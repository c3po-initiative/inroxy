package inroxy.service

import inroxy.client.VardguidenClient
import inroxy.mapper.EncounterMapper
import org.hl7.fhir.r4.model.Encounter
import org.springframework.stereotype.Service

@Service
class EncounterService(
    private val client: VardguidenClient,
    private val mapper: EncounterMapper
) {

    suspend fun getEncounters(cookies: String): List<Encounter> {
        val entries = client.getJournalEntries(cookies)
        return mapper.toFhirList(entries)
    }

    suspend fun getEncounter(id: String, cookies: String): Encounter? {
        val entry = client.getJournalEntry(id, cookies) ?: return null
        return mapper.toFhir(entry)
    }
}
