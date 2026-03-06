package ca.uhn.fhir.rest.server

import ca.uhn.fhir.context.FhirContext

class McpFhirBridge(private val fhirContext: FhirContext) {

    fun encodeResource(resource: org.hl7.fhir.r4.model.Resource): String =
        fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(resource)

    fun parseResource(json: String): org.hl7.fhir.r4.model.Resource =
        fhirContext.newJsonParser().parseResource(json) as org.hl7.fhir.r4.model.Resource
}
