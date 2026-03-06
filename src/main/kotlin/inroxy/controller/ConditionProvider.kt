package inroxy.controller

import ca.uhn.fhir.rest.annotation.Search
import ca.uhn.fhir.rest.api.server.IBundleProvider
import ca.uhn.fhir.rest.server.IResourceProvider
import ca.uhn.fhir.rest.server.SimpleBundleProvider
import inroxy.service.ConditionService
import jakarta.servlet.http.HttpServletRequest
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.Condition
import org.springframework.stereotype.Component

@Component
class ConditionProvider(
    private val conditionService: ConditionService,
    private val request: HttpServletRequest
) : IResourceProvider {

    override fun getResourceType(): Class<out IBaseResource> = Condition::class.java

    @Search
    fun search(): IBundleProvider = runBlocking {
        SimpleBundleProvider(conditionService.getConditions(request.extractCookies()))
    }
}
