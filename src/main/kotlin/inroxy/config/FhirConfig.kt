package inroxy.config

import ca.uhn.fhir.context.FhirContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FhirConfig {

    @Bean
    fun fhirContext(): FhirContext = FhirContext.forR4()
}
