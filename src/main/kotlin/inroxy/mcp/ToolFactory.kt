package inroxy.mcp

import org.springframework.ai.tool.ToolCallback
import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.stereotype.Component

@Component
class ToolFactory(private val mcpBridge: McpBridge) {

    fun createToolCallbackProvider(): ToolCallbackProvider {
        return ToolCallbackProvider { emptyList<ToolCallback>().toTypedArray() }
    }
}
