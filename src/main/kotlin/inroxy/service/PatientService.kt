package inroxy.service

import inroxy.client.VardguidenClient
import inroxy.mapper.PatientMapper
import org.hl7.fhir.r4.model.Patient
import org.springframework.stereotype.Service

@Service
class PatientService(
    private val client: VardguidenClient,
    private val mapper: PatientMapper
) {

    suspend fun getPatient(cookies: String): Patient? {
        val profile = client.getUserProfile(cookies) ?: return null
        return mapper.toFhir(profile)
    }
}
