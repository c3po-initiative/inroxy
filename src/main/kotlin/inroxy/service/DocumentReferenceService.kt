package inroxy.service

import inroxy.client.VardguidenClient
import inroxy.mapper.DocumentReferenceMapper
import org.hl7.fhir.r4.model.DocumentReference
import org.springframework.stereotype.Service

@Service
class DocumentReferenceService(
    private val client: VardguidenClient,
    private val mapper: DocumentReferenceMapper
) {

    suspend fun getDocumentReferences(cookies: String): List<DocumentReference> {
        val messages = client.getInboxMessages(cookies)
        return mapper.toFhirList(messages)
    }

    suspend fun getDocumentReference(id: String, cookies: String): DocumentReference? {
        val message = client.getInboxMessage(id, cookies) ?: return null
        return mapper.toFhir(message)
    }
}
