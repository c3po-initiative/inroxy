package inroxy.config

import ca.uhn.fhir.context.FhirContext
import ca.uhn.fhir.rest.server.IResourceProvider
import ca.uhn.fhir.rest.server.RestfulServer
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FhirRestfulServerConfig(
    private val fhirContext: FhirContext,
    private val resourceProviders: List<IResourceProvider>
) {

    @Bean
    fun fhirServletRegistration(): ServletRegistrationBean<FhirServlet> {
        val servlet = FhirServlet(fhirContext, resourceProviders)
        return ServletRegistrationBean(servlet, "/fhir/*").apply {
            setName("fhirServlet")
            setLoadOnStartup(1)
        }
    }
}

class FhirServlet(
    fhirContext: FhirContext,
    private val resourceProviders: List<IResourceProvider>
) : RestfulServer(fhirContext) {
    override fun initialize() {
        serverName = "inroxy"
        implementationDescription = "Swedish Healthcare FHIR Proxy"
        registerProviders(resourceProviders)
    }
}
