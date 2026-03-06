package inroxy.mapper

import inroxy.model.VardguidenAppointment
import org.hl7.fhir.r4.model.Appointment
import org.hl7.fhir.r4.model.CodeableConcept
import org.hl7.fhir.r4.model.Reference
import org.springframework.stereotype.Component
import java.util.Date

@Component
class AppointmentMapper {

    fun toFhir(source: VardguidenAppointment): Appointment {
        val appointment = Appointment()

        source.id?.let { appointment.id = it }

        source.status?.let { s ->
            appointment.status = when (s.lowercase()) {
                "booked", "bokad" -> Appointment.AppointmentStatus.BOOKED
                "cancelled", "avbokad" -> Appointment.AppointmentStatus.CANCELLED
                "fulfilled", "genomford" -> Appointment.AppointmentStatus.FULFILLED
                "pending", "vantande" -> Appointment.AppointmentStatus.PENDING
                "noshow" -> Appointment.AppointmentStatus.NOSHOW
                else -> Appointment.AppointmentStatus.PROPOSED
            }
        }

        source.startTime?.let { appointment.start = parseDate(it) }
        source.endTime?.let { appointment.end = parseDate(it) }

        source.visitType?.let { vt ->
            appointment.addServiceType(CodeableConcept().apply { text = vt })
        }

        source.description?.let { appointment.description = it }

        source.careUnitId?.let { unitId ->
            val participant = appointment.addParticipant()
            participant.actor = Reference("Organization/$unitId").apply {
                display = source.careUnitName
            }
            participant.status = Appointment.ParticipationStatus.ACCEPTED
        }

        return appointment
    }

    fun toFhirList(sources: List<VardguidenAppointment>): List<Appointment> =
        sources.map { toFhir(it) }

    private fun parseDate(dateStr: String): Date? =
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
