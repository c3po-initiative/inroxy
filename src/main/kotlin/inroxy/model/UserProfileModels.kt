package inroxy.model

data class UserProfile(
    val firstName: String? = null,
    val lastName: String? = null,
    val agent: Agent? = null
)

data class Agent(
    val firstName: String? = null,
    val lastName: String? = null
)
