package inroxy.config

import inroxy.mcp.ToolFactory
import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class McpServerConfig {

    @Bean
    fun toolCallbackProvider(toolFactory: ToolFactory): ToolCallbackProvider =
        toolFactory.createToolCallbackProvider()
}
