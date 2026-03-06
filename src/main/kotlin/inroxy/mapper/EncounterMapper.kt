package inroxy.mapper

import inroxy.model.JournalEntry
import org.hl7.fhir.r4.model.*
import org.springframework.stereotype.Component

@Component
class EncounterMapper {

    fun toFhir(source: JournalEntry): Encounter {
        val encounter = Encounter()

        source.id?.let { encounter.id = it }
        encounter.status = Encounter.EncounterStatus.FINISHED

        source.careContactType?.let { contactType ->
            encounter.class_ = when (contactType.lowercase()) {
                "mottagningsbesok", "visit" -> Coding("http://terminology.hl7.org/CodeSystem/v3-ActCode", "AMB", "ambulatory")
                "slutenvard", "inpatient" -> Coding("http://terminology.hl7.org/CodeSystem/v3-ActCode", "IMP", "inpatient encounter")
                "telefonkontakt", "phone" -> Coding("http://terminology.hl7.org/CodeSystem/v3-ActCode", "VR", "virtual")
                else -> Coding("http://terminology.hl7.org/CodeSystem/v3-ActCode", "AMB", "ambulatory")
            }
        } ?: run {
            encounter.class_ = Coding("http://terminology.hl7.org/CodeSystem/v3-ActCode", "AMB", "ambulatory")
        }

        source.date?.let { dateStr ->
            parseDate(dateStr)?.let { date ->
                encounter.period = Period().apply { start = DateTimeType(date).value }
            }
        }

        source.careUnitId?.let { unitId ->
            encounter.serviceProvider = Reference("Organization/$unitId").apply {
                display = source.careUnitName
            }
        }

        source.diagnoses?.forEach { diag ->
            val diagComponent = encounter.addDiagnosis()
            diagComponent.condition = Reference().apply {
                display = diag.displayName
            }
        }

        return encounter
    }

    fun toFhirList(sources: List<JournalEntry>): List<Encounter> =
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
