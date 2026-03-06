package inroxy.controller

import ca.uhn.fhir.rest.annotation.Read
import ca.uhn.fhir.rest.server.IResourceProvider
import inroxy.service.PatientService
import jakarta.servlet.http.HttpServletRequest
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.IdType
import org.hl7.fhir.r4.model.Patient
import org.springframework.stereotype.Component

@Component
class PatientProvider(
    private val patientService: PatientService,
    private val request: HttpServletRequest
) : IResourceProvider {

    override fun getResourceType(): Class<out IBaseResource> = Patient::class.java

    @Read
    fun read(@ca.uhn.fhir.rest.annotation.IdParam id: IdType): Patient? = runBlocking {
        patientService.getPatient(request.extractCookies())
    }
}
