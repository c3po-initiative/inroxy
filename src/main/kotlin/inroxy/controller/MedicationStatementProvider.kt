package inroxy.controller

import ca.uhn.fhir.rest.annotation.IdParam
import ca.uhn.fhir.rest.annotation.Read
import ca.uhn.fhir.rest.annotation.Search
import ca.uhn.fhir.rest.api.server.IBundleProvider
import ca.uhn.fhir.rest.server.IResourceProvider
import ca.uhn.fhir.rest.server.SimpleBundleProvider
import inroxy.service.MedicationStatementService
import jakarta.servlet.http.HttpServletRequest
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.IdType
import org.hl7.fhir.r4.model.MedicationStatement
import org.springframework.stereotype.Component

@Component
class MedicationStatementProvider(
    private val medicationStatementService: MedicationStatementService,
    private val request: HttpServletRequest
) : IResourceProvider {

    override fun getResourceType(): Class<out IBaseResource> = MedicationStatement::class.java

    @Read
    fun read(@IdParam id: IdType): MedicationStatement? = runBlocking {
        medicationStatementService.getMedicationStatement(id.idPart, request.extractCookies())
    }

    @Search
    fun search(): IBundleProvider = runBlocking {
        SimpleBundleProvider(medicationStatementService.getMedicationStatements(request.extractCookies()))
    }
}
