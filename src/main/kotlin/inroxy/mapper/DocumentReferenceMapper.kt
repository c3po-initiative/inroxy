package inroxy.mapper

import inroxy.model.InboxMessage
import org.hl7.fhir.r4.model.*
import org.springframework.stereotype.Component

@Component
class DocumentReferenceMapper {

    fun toFhir(source: InboxMessage): DocumentReference {
        val doc = DocumentReference()

        source.id?.let { doc.id = it }
        doc.status = if (source.isRead == true) {
            Enumerations.DocumentReferenceStatus.CURRENT
        } else {
            Enumerations.DocumentReferenceStatus.CURRENT
        }

        source.subject?.let { doc.description = it }

        source.receivedDate?.let { dateStr ->
            parseDate(dateStr)?.let { doc.date = it }
        }

        source.senderName?.let { name ->
            doc.addAuthor(Reference().apply {
                display = name
                source.senderId?.let { id = "Organization/$it" }
            })
        }

        source.content?.let { text ->
            val attachment = Attachment().apply {
                contentType = source.contentType ?: "text/plain"
                data = text.toByteArray()
            }
            doc.addContent().attachment = attachment
        }

        source.careUnitId?.let { unitId ->
            doc.context = DocumentReference.DocumentReferenceContextComponent().apply {
                addRelated(Reference("Organization/$unitId").apply {
                    display = source.careUnitName
                })
            }
        }

        return doc
    }

    fun toFhirList(sources: List<InboxMessage>): List<DocumentReference> =
        sources.map { toFhir(it) }

    private fun parseDate(dateStr: String): java.util.Date? =
        try {
            java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateStr)
        } catch (e: Exception) {
            try {
                java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateStr)
            } catch (e2: Exception) {
                null
            }
        }
}
