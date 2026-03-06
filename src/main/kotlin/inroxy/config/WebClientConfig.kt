package inroxy.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(private val properties: VardguidenClientProperties) {

    @Bean
    fun vardguidenWebClient(): WebClient =
        WebClient.builder()
            .baseUrl(properties.baseUrl)
            .defaultHeader("Accept", "application/json")
            .build()

    @Bean
    fun journalenWebClient(): WebClient =
        WebClient.builder()
            .baseUrl(properties.journalenBaseUrl)
            .defaultHeader("Accept", "application/json")
            .build()
}
