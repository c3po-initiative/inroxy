package inroxy.mcp

import ca.uhn.fhir.context.FhirContext
import ca.uhn.fhir.rest.server.IResourceProvider
import org.springframework.stereotype.Component

@Component
class McpBridge(
    private val fhirContext: FhirContext,
    private val resourceProviders: List<IResourceProvider>
) {

    fun getAvailableResourceTypes(): List<String> =
        resourceProviders.map { it.resourceType.simpleName }

    fun serializeResource(resource: org.hl7.fhir.r4.model.Resource): String =
        fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(resource)
}
