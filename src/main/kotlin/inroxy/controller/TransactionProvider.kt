package inroxy.controller

import ca.uhn.fhir.context.FhirContext
import ca.uhn.fhir.rest.annotation.Transaction
import ca.uhn.fhir.rest.annotation.TransactionParam
import ca.uhn.fhir.rest.server.IResourceProvider
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.Bundle
import org.springframework.stereotype.Component

@Component
class TransactionProvider(
    private val fhirContext: FhirContext,
    private val resourceProviders: List<IResourceProvider>
) {

    @Transaction
    fun transaction(@TransactionParam bundle: Bundle): Bundle {
        val responseBundle = Bundle()
        responseBundle.type = Bundle.BundleType.TRANSACTIONRESPONSE

        for (entry in bundle.entry) {
            val request = entry.request ?: continue
            if (request.method != Bundle.HTTPVerb.GET) {
                val responseEntry = responseBundle.addEntry()
                responseEntry.response = Bundle.BundleEntryResponseComponent().apply {
                    status = "405 Method Not Allowed"
                }
                continue
            }

            val responseEntry = responseBundle.addEntry()
            responseEntry.response = Bundle.BundleEntryResponseComponent().apply {
                status = "200 OK"
            }
        }

        return responseBundle
    }
}
