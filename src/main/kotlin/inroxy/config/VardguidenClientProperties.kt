package inroxy.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "vardguiden")
data class VardguidenClientProperties(
    val baseUrl: String = "https://e-tjanster.1177.se",
    val journalenBaseUrl: String = "https://journalen.1177.se"
)
