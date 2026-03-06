package inroxy.service

import inroxy.client.VardguidenClient
import inroxy.mapper.MedicationStatementMapper
import org.hl7.fhir.r4.model.MedicationStatement
import org.springframework.stereotype.Service

@Service
class MedicationStatementService(
    private val client: VardguidenClient,
    private val mapper: MedicationStatementMapper
) {

    suspend fun getMedicationStatements(cookies: String): List<MedicationStatement> {
        val medications = client.getMedications(cookies)
        return mapper.toFhirList(medications)
    }

    suspend fun getMedicationStatement(id: String, cookies: String): MedicationStatement? {
        val medication = client.getMedication(id, cookies) ?: return null
        return mapper.toFhir(medication)
    }
}
