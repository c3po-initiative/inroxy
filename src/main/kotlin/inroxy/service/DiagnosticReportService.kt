package inroxy.service

import inroxy.client.VardguidenClient
import inroxy.mapper.DiagnosticReportMapper
import org.hl7.fhir.r4.model.DiagnosticReport
import org.springframework.stereotype.Service

@Service
class DiagnosticReportService(
    private val client: VardguidenClient,
    private val mapper: DiagnosticReportMapper
) {

    suspend fun getDiagnosticReports(cookies: String): List<DiagnosticReport> {
        val entries = client.getJournalEntries(cookies)
        return mapper.toFhirList(entries)
    }

    suspend fun getDiagnosticReport(id: String, cookies: String): DiagnosticReport? {
        val entry = client.getJournalEntry(id, cookies) ?: return null
        return mapper.toFhir(entry)
    }
}
