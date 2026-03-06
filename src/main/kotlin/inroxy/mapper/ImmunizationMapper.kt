package inroxy.mapper

import inroxy.model.VardguidenImmunization
import org.hl7.fhir.r4.model.*
import org.springframework.stereotype.Component

@Component
class ImmunizationMapper {

    fun toFhir(source: VardguidenImmunization): Immunization {
        val immunization = Immunization()

        source.id?.let { immunization.id = it }

        immunization.status = when (source.status?.lowercase()) {
            "completed", "genomford" -> Immunization.ImmunizationStatus.COMPLETED
            "not-done", "ej_genomford" -> Immunization.ImmunizationStatus.NOTDONE
            else -> Immunization.ImmunizationStatus.COMPLETED
        }

        val vaccineConcept = CodeableConcept().apply {
            text = source.vaccineName
            source.vaccineCode?.let { code ->
                addCoding().apply {
                    this.code = code
                    display = source.vaccineName
                }
            }
        }
        immunization.vaccineCode = vaccineConcept

        source.date?.let { dateStr ->
            parseDate(dateStr)?.let { date ->
                immunization.occurrence = DateTimeType(date)
            }
        }

        source.lotNumber?.let { immunization.lotNumber = it }

        source.doseNumber?.let { dose ->
            immunization.addProtocolApplied().apply {
                doseNumber = PositiveIntType(dose)
            }
        }

        source.performer?.let { performerName ->
            immunization.addPerformer().apply {
                actor = Reference().apply { display = performerName }
            }
        }

        source.careUnitId?.let { unitId ->
            immunization.addPerformer().apply {
                actor = Reference("Organization/$unitId").apply {
                    display = source.careUnitName
                }
            }
        }

        return immunization
    }

    fun toFhirList(sources: List<VardguidenImmunization>): List<Immunization> =
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
