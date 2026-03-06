package inroxy.service

import inroxy.client.VardguidenClient
import inroxy.mapper.OrganizationMapper
import org.hl7.fhir.r4.model.Organization
import org.springframework.stereotype.Service

@Service
class OrganizationService(
    private val client: VardguidenClient,
    private val mapper: OrganizationMapper
) {

    suspend fun getOrganizations(cookies: String): List<Organization> {
        val careUnits = client.getCareUnits(cookies)
        return mapper.toFhirList(careUnits)
    }

    suspend fun getOrganization(id: String, cookies: String): Organization? {
        val careUnit = client.getCareUnit(id, cookies) ?: return null
        return mapper.toFhir(careUnit)
    }
}
