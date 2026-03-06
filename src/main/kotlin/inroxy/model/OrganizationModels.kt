package inroxy.model

data class CareUnit(
    val id: String? = null,
    val name: String? = null,
    val hsaId: String? = null,
    val phoneNumber: String? = null,
    val street: String? = null,
    val postalCode: String? = null,
    val city: String? = null,
    val municipality: String? = null,
    val region: String? = null,
    val type: String? = null,
    val email: String? = null,
    val website: String? = null
)

data class CareUnitListResponse(
    val careUnits: List<CareUnit>? = null
)
