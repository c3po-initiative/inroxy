package inroxy.mapper

import inroxy.model.LabResult
import org.hl7.fhir.r4.model.*
import org.springframework.stereotype.Component

@Component
class ObservationMapper {

    fun toFhir(source: LabResult): Observation {
        val obs = Observation()

        source.id?.let { obs.id = it }

        source.status?.let { s ->
            obs.status = when (s.lowercase()) {
                "final", "slutgiltig" -> Observation.ObservationStatus.FINAL
                "preliminary", "preliminar" -> Observation.ObservationStatus.PRELIMINARY
                "cancelled", "avbruten" -> Observation.ObservationStatus.CANCELLED
                else -> Observation.ObservationStatus.FINAL
            }
        } ?: run { obs.status = Observation.ObservationStatus.FINAL }

        source.name?.let { name ->
            obs.code = CodeableConcept().apply { text = name }
        }

        if (source.value != null) {
            val numericValue = source.value.toDoubleOrNull()
            if (numericValue != null && source.unit != null) {
                obs.value = Quantity().apply {
                    value = java.math.BigDecimal(numericValue)
                    unit = source.unit
                }
            } else {
                obs.value = StringType(source.value)
            }
        }

        source.referenceRange?.let { range ->
            obs.addReferenceRange().apply { text = range }
        }

        source.date?.let { dateStr ->
            parseDate(dateStr)?.let { date ->
                obs.effective = DateTimeType(date)
            }
        }

        source.comment?.let { obs.addNote().text = it }

        return obs
    }

    fun toFhirList(sources: List<LabResult>): List<Observation> =
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
