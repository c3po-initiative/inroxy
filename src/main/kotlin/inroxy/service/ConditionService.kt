package inroxy.service

import inroxy.client.VardguidenClient
import inroxy.mapper.ConditionMapper
import org.hl7.fhir.r4.model.Condition
import org.springframework.stereotype.Service

@Service
class ConditionService(
    private val client: VardguidenClient,
    private val mapper: ConditionMapper
) {

    suspend fun getConditions(cookies: String): List<Condition> {
        val entries = client.getJournalEntries(cookies)
        val allDiagnoses = entries.flatMap { it.diagnoses ?: emptyList() }
        return mapper.toFhirList(allDiagnoses)
    }
}
