package inroxy.controller

import ca.uhn.fhir.rest.annotation.IdParam
import ca.uhn.fhir.rest.annotation.Read
import ca.uhn.fhir.rest.annotation.Search
import ca.uhn.fhir.rest.api.server.IBundleProvider
import ca.uhn.fhir.rest.server.IResourceProvider
import ca.uhn.fhir.rest.server.SimpleBundleProvider
import inroxy.service.EncounterService
import jakarta.servlet.http.HttpServletRequest
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.Encounter
import org.hl7.fhir.r4.model.IdType
import org.springframework.stereotype.Component

@Component
class EncounterProvider(
    private val encounterService: EncounterService,
    private val request: HttpServletRequest
) : IResourceProvider {

    override fun getResourceType(): Class<out IBaseResource> = Encounter::class.java

    @Read
    fun read(@IdParam id: IdType): Encounter? = runBlocking {
        encounterService.getEncounter(id.idPart, request.extractCookies())
    }

    @Search
    fun search(): IBundleProvider = runBlocking {
        SimpleBundleProvider(encounterService.getEncounters(request.extractCookies()))
    }
}
