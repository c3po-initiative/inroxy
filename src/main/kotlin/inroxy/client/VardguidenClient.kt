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
            .uri("/api/user/profile")
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<UserProfile>()
            .awaitSingleOrNull()

    suspend fun getAppointments(cookies: String): List<VardguidenAppointment> =
        vardguidenWebClient.get()
            .uri("/api/appointments")
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<AppointmentListResponse>()
            .awaitSingleOrNull()
            ?.appointments ?: emptyList()

    suspend fun getAppointment(id: String, cookies: String): VardguidenAppointment? =
        vardguidenWebClient.get()
            .uri("/api/appointments/{id}", id)
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<VardguidenAppointment>()
            .awaitSingleOrNull()

    suspend fun getInboxMessages(cookies: String): List<InboxMessage> =
        vardguidenWebClient.get()
            .uri("/api/inbox/messages")
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<InboxListResponse>()
            .awaitSingleOrNull()
            ?.messages ?: emptyList()

    suspend fun getInboxMessage(id: String, cookies: String): InboxMessage? =
        vardguidenWebClient.get()
            .uri("/api/inbox/messages/{id}", id)
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<InboxMessage>()
            .awaitSingleOrNull()

    suspend fun getJournalEntries(cookies: String): List<JournalEntry> =
        journalenWebClient.get()
            .uri("/api/journal/entries")
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<JournalListResponse>()
            .awaitSingleOrNull()
            ?.entries ?: emptyList()

    suspend fun getJournalEntry(id: String, cookies: String): JournalEntry? =
        journalenWebClient.get()
            .uri("/api/journal/entries/{id}", id)
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<JournalEntry>()
            .awaitSingleOrNull()

    suspend fun getMedications(cookies: String): List<Medication> =
        vardguidenWebClient.get()
            .uri("/api/medications")
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<MedicationListResponse>()
            .awaitSingleOrNull()
            ?.medications ?: emptyList()

    suspend fun getMedication(id: String, cookies: String): Medication? =
        vardguidenWebClient.get()
            .uri("/api/medications/{id}", id)
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<Medication>()
            .awaitSingleOrNull()

    suspend fun getImmunizations(cookies: String): List<VardguidenImmunization> =
        vardguidenWebClient.get()
            .uri("/api/immunizations")
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<ImmunizationListResponse>()
            .awaitSingleOrNull()
            ?.immunizations ?: emptyList()

    suspend fun getImmunization(id: String, cookies: String): VardguidenImmunization? =
        vardguidenWebClient.get()
            .uri("/api/immunizations/{id}", id)
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<VardguidenImmunization>()
            .awaitSingleOrNull()

    suspend fun getCareUnits(cookies: String): List<CareUnit> =
        vardguidenWebClient.get()
            .uri("/api/careunits")
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<CareUnitListResponse>()
            .awaitSingleOrNull()
            ?.careUnits ?: emptyList()

    suspend fun getCareUnit(id: String, cookies: String): CareUnit? =
        vardguidenWebClient.get()
            .uri("/api/careunits/{id}", id)
            .header("Cookie", cookies)
            .retrieve()
            .bodyToMono<CareUnit>()
            .awaitSingleOrNull()
}
