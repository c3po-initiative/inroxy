package inroxy.service

import inroxy.client.VardguidenClient
import inroxy.mapper.ImmunizationMapper
import org.hl7.fhir.r4.model.Immunization
import org.springframework.stereotype.Service

@Service
class ImmunizationService(
    private val client: VardguidenClient,
    private val mapper: ImmunizationMapper
) {

    suspend fun getImmunizations(cookies: String): List<Immunization> {
        val immunizations = client.getImmunizations(cookies)
        return mapper.toFhirList(immunizations)
    }

    suspend fun getImmunization(id: String, cookies: String): Immunization? {
        val immunization = client.getImmunization(id, cookies) ?: return null
        return mapper.toFhir(immunization)
    }
}
