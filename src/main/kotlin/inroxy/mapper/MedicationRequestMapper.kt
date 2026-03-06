package inroxy.mapper

import inroxy.model.Medication
import org.hl7.fhir.r4.model.*
import org.springframework.stereotype.Component

@Component
class MedicationRequestMapper {

    companion object {
        const val ATC_SYSTEM = "http://www.whocc.no/atc"
    }

    fun toFhir(source: Medication): MedicationRequest {
        val request = MedicationRequest()

        source.id?.let { request.id = "rx-$it" }

        request.status = when (source.status?.lowercase()) {
            "active", "aktiv" -> MedicationRequest.MedicationRequestStatus.ACTIVE
            "completed", "avslutad" -> MedicationRequest.MedicationRequestStatus.COMPLETED
            "stopped", "stoppad" -> MedicationRequest.MedicationRequestStatus.STOPPED
            "cancelled", "avbruten" -> MedicationRequest.MedicationRequestStatus.CANCELLED
            else -> MedicationRequest.MedicationRequestStatus.ACTIVE
        }

        request.intent = MedicationRequest.MedicationRequestIntent.ORDER

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
        request.medication = medConcept

        source.prescribedDate?.let { dateStr ->
            parseDate(dateStr)?.let { request.authoredOn = it }
        }

        source.prescriber?.let { prescriberName ->
            request.requester = Reference().apply {
                display = prescriberName
            }
        }

        source.dosage?.let { dosageText ->
            request.addDosageInstruction().apply { text = dosageText }
        }

        source.instructions?.let { instr ->
            if (request.dosageInstruction.isEmpty()) {
                request.addDosageInstruction().apply { text = instr }
            } else {
                request.dosageInstructionFirstRep.patientInstruction = instr
            }
        }

        return request
    }

    fun toFhirList(sources: List<Medication>): List<MedicationRequest> =
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
