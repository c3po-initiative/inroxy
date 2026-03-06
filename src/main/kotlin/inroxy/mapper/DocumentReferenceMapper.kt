package inroxy.mapper

import inroxy.model.InboxMessage
import org.hl7.fhir.r4.model.*
import org.springframework.stereotype.Component

@Component
class DocumentReferenceMapper {

    fun toFhir(source: InboxMessage): DocumentReference {
        val doc = DocumentReference()

        source.id?.let { doc.id = it }
        doc.status = Enumerations.DocumentReferenceStatus.CURRENT

        source.threadTitle?.let { doc.description = it }

        source.messageDate?.let { dateStr ->
            parseDate(dateStr)?.let { doc.date = it }
        }

        source.facilityName?.let { name ->
            doc.addAuthor(Reference().apply { display = name })
        }

        source.threadLabel?.let { label ->
            doc.addCategory(CodeableConcept().apply { text = label })
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
