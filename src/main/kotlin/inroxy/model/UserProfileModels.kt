package inroxy.model

data class UserProfile(
    val personnummer: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val address: Address? = null,
    val gender: String? = null,
    val birthDate: String? = null
)

data class Address(
    val street: String? = null,
    val postalCode: String? = null,
    val city: String? = null,
    val country: String? = "SE"
)
