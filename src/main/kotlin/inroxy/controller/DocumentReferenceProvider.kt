package inroxy.controller

import ca.uhn.fhir.rest.annotation.Search
import ca.uhn.fhir.rest.api.server.IBundleProvider
import ca.uhn.fhir.rest.server.IResourceProvider
import ca.uhn.fhir.rest.server.SimpleBundleProvider
import inroxy.service.DocumentReferenceService
import jakarta.servlet.http.HttpServletRequest
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.DocumentReference
import org.springframework.stereotype.Component

@Component
class DocumentReferenceProvider(
    private val documentReferenceService: DocumentReferenceService,
    private val request: HttpServletRequest
) : IResourceProvider {

    override fun getResourceType(): Class<out IBaseResource> = DocumentReference::class.java

    @Search
    fun search(): IBundleProvider = runBlocking {
        SimpleBundleProvider(documentReferenceService.getDocumentReferences(request.extractCookies()))
    }
}
