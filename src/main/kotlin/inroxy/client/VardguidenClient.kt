package inroxy.client

import inroxy.model.*
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class VardguidenClient(
    @param:Qualifier("vardguidenWebClient") private val vardguidenWebClient: WebClient,
    @param:Qualifier("journalenWebClient") private val journalenWebClient: WebClient
) {

    suspend fun getUserProfile(cookies: String): UserProfile? =
        vardguidenWebClient.get()
            .uri("/api/core/userprofile")
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<UserProfile>()
            .awaitSingleOrNull()

    suspend fun getAppointmentEvents(cookies: String): AppointmentEventsResponse? =
        vardguidenWebClient.get()
            .uri("/api/core/overview/events/appointment-events")
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<AppointmentEventsResponse>()
            .awaitSingleOrNull()

    suspend fun getInboxMessages(cookies: String): List<InboxMessage> =
        vardguidenWebClient.get()
            .uri("/api/core/inbox/message")
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<List<InboxMessage>>()
            .awaitSingleOrNull() ?: emptyList()

    suspend fun getEhrSystemEvents(cookies: String): List<EhrSystemEvent> =
        vardguidenWebClient.get()
            .uri("/api/core/overview/events/ehr-system-events")
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<EhrSystemEventsResponse>()
            .awaitSingleOrNull()
            ?.ehrSystemEvents ?: emptyList()

    // journalen.1177.se endpoints return HTML - these are placeholders
    // until we implement HTML scraping or discover JSON APIs

    suspend fun getJournalEntries(cookies: String): List<JournalEntry> =
        emptyList()

    suspend fun getJournalEntry(id: String, cookies: String): JournalEntry? =
        null

    suspend fun getMedications(cookies: String): List<Medication> =
        emptyList()

    suspend fun getMedication(id: String, cookies: String): Medication? =
        null

    suspend fun getImmunizations(cookies: String): List<VardguidenImmunization> =
        emptyList()

    suspend fun getImmunization(id: String, cookies: String): VardguidenImmunization? =
        null

    suspend fun getCareUnits(cookies: String): List<CareUnit> =
        emptyList()

    suspend fun getCareUnit(id: String, cookies: String): CareUnit? =
        null
}
