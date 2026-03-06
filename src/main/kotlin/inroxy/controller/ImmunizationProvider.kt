package inroxy.controller

import ca.uhn.fhir.rest.annotation.IdParam
import ca.uhn.fhir.rest.annotation.Read
import ca.uhn.fhir.rest.annotation.Search
import ca.uhn.fhir.rest.api.server.IBundleProvider
import ca.uhn.fhir.rest.server.IResourceProvider
import ca.uhn.fhir.rest.server.SimpleBundleProvider
import inroxy.service.ImmunizationService
import jakarta.servlet.http.HttpServletRequest
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.IdType
import org.hl7.fhir.r4.model.Immunization
import org.springframework.stereotype.Component

@Component
class ImmunizationProvider(
    private val immunizationService: ImmunizationService,
    private val request: HttpServletRequest
) : IResourceProvider {

    override fun getResourceType(): Class<out IBaseResource> = Immunization::class.java

    @Read
    fun read(@IdParam id: IdType): Immunization? = runBlocking {
        immunizationService.getImmunization(id.idPart, request.extractCookies())
    }

    @Search
    fun search(): IBundleProvider = runBlocking {
        SimpleBundleProvider(immunizationService.getImmunizations(request.extractCookies()))
    }
}
