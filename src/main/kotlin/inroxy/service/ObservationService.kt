package inroxy.service

import inroxy.client.VardguidenClient
import inroxy.mapper.ObservationMapper
import org.hl7.fhir.r4.model.Observation
import org.springframework.stereotype.Service

@Service
class ObservationService(
    private val client: VardguidenClient,
    private val mapper: ObservationMapper
) {

    suspend fun getObservations(cookies: String): List<Observation> {
        val entries = client.getJournalEntries(cookies)
        val allObs = entries.flatMap { it.observations ?: emptyList() }
        return mapper.toFhirList(allObs)
    }
}
