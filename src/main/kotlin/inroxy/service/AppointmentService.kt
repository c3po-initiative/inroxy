package inroxy.service

import inroxy.client.VardguidenClient
import inroxy.mapper.AppointmentMapper
import org.hl7.fhir.r4.model.Appointment
import org.springframework.stereotype.Service

@Service
class AppointmentService(
    private val client: VardguidenClient,
    private val mapper: AppointmentMapper
) {

    suspend fun getAppointments(cookies: String): List<Appointment> {
        val appointments = client.getAppointments(cookies)
        return mapper.toFhirList(appointments)
    }

    suspend fun getAppointment(id: String, cookies: String): Appointment? {
        val appointment = client.getAppointment(id, cookies) ?: return null
        return mapper.toFhir(appointment)
    }
}
