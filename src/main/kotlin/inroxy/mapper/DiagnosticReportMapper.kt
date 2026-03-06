package inroxy.mapper

import inroxy.model.JournalEntry
import org.hl7.fhir.r4.model.*
import org.springframework.stereotype.Component

@Component
class DiagnosticReportMapper {

    fun toFhir(source: JournalEntry): DiagnosticReport {
        val report = DiagnosticReport()

        source.id?.let { report.id = it }
        report.status = DiagnosticReport.DiagnosticReportStatus.FINAL

        source.title?.let { title ->
            report.code = CodeableConcept().apply { text = title }
        }

        source.date?.let { dateStr ->
            parseDate(dateStr)?.let { date ->
                report.effective = DateTimeType(date)
            }
        }

        source.observations?.forEach { obs ->
            obs.id?.let { obsId ->
                report.addResult(Reference("Observation/$obsId").apply {
                    display = obs.name
                })
            }
        }

        source.notes?.forEach { note ->
            report.conclusion = (report.conclusion ?: "") + (note.text ?: "") + "\n"
        }

        source.careUnitId?.let { unitId ->
            report.addPerformer(Reference("Organization/$unitId").apply {
                display = source.careUnitName
            })
        }

        return report
    }

    fun toFhirList(sources: List<JournalEntry>): List<DiagnosticReport> =
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
