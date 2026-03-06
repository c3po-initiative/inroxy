package inroxy.controller

import ca.uhn.fhir.rest.annotation.IdParam
import ca.uhn.fhir.rest.annotation.Read
import ca.uhn.fhir.rest.annotation.Search
import ca.uhn.fhir.rest.api.server.IBundleProvider
import ca.uhn.fhir.rest.server.IResourceProvider
import ca.uhn.fhir.rest.server.SimpleBundleProvider
import inroxy.service.DiagnosticReportService
import jakarta.servlet.http.HttpServletRequest
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.DiagnosticReport
import org.hl7.fhir.r4.model.IdType
import org.springframework.stereotype.Component

@Component
class DiagnosticReportProvider(
    private val diagnosticReportService: DiagnosticReportService,
    private val request: HttpServletRequest
) : IResourceProvider {

    override fun getResourceType(): Class<out IBaseResource> = DiagnosticReport::class.java

    @Read
    fun read(@IdParam id: IdType): DiagnosticReport? = runBlocking {
        diagnosticReportService.getDiagnosticReport(id.idPart, request.extractCookies())
    }

    @Search
    fun search(): IBundleProvider = runBlocking {
        SimpleBundleProvider(diagnosticReportService.getDiagnosticReports(request.extractCookies()))
    }
}
