package inroxy.mapper

import inroxy.model.Medication
import org.hl7.fhir.r4.model.*
import org.springframework.stereotype.Component

@Component
class MedicationStatementMapper {

    companion object {
        const val ATC_SYSTEM = "http://www.whocc.no/atc"
    }

    fun toFhir(source: Medication): MedicationStatement {
        val stmt = MedicationStatement()

        source.id?.let { stmt.id = it }

        stmt.status = when (source.status?.lowercase()) {
            "active", "aktiv" -> MedicationStatement.MedicationStatementStatus.ACTIVE
            "completed", "avslutad" -> MedicationStatement.MedicationStatementStatus.COMPLETED
            "stopped", "stoppad" -> MedicationStatement.MedicationStatementStatus.STOPPED
            else -> MedicationStatement.MedicationStatementStatus.ACTIVE
        }

        val medConcept = CodeableConcept().apply {
            text = source.name
            source.atcCode?.let { atc ->
                addCoding().apply {
                    system = ATC_SYSTEM
                    code = atc
                    display = source.name
                }
            }
        }
        stmt.medication = medConcept

        if (source.startDate != null || source.endDate != null) {
            stmt.effective = Period().apply {
                source.startDate?.let { start = parseDate(it)?.let { d -> DateTimeType(d).value } }
                source.endDate?.let { end = parseDate(it)?.let { d -> DateTimeType(d).value } }
            }
        }

        source.dosage?.let { dosageText ->
            stmt.addDosage().apply { text = dosageText }
        }

        source.instructions?.let { instr ->
            if (stmt.dosage.isEmpty()) {
                stmt.addDosage().apply { text = instr }
            } else {
                stmt.dosageFirstRep.patientInstruction = instr
            }
        }

        return stmt
    }

    fun toFhirList(sources: List<Medication>): List<MedicationStatement> =
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
