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
        val response = client.getAppointmentEvents(cookies)
        return mapper.toFhirList(response?.appointments ?: emptyList())
    }
}
