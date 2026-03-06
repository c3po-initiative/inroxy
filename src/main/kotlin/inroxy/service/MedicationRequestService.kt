package inroxy.service

import inroxy.client.VardguidenClient
import inroxy.mapper.MedicationRequestMapper
import org.hl7.fhir.r4.model.MedicationRequest
import org.springframework.stereotype.Service

@Service
class MedicationRequestService(
    private val client: VardguidenClient,
    private val mapper: MedicationRequestMapper
) {

    suspend fun getMedicationRequests(cookies: String): List<MedicationRequest> {
        val medications = client.getMedications(cookies)
        return mapper.toFhirList(medications)
    }

    suspend fun getMedicationRequest(id: String, cookies: String): MedicationRequest? {
        val medication = client.getMedication(id, cookies) ?: return null
        return mapper.toFhir(medication)
    }
}
