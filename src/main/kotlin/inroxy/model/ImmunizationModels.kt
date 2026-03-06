package inroxy.model

data class VardguidenImmunization(
    val id: String? = null,
    val vaccineName: String? = null,
    val vaccineCode: String? = null,
    val date: String? = null,
    val doseNumber: Int? = null,
    val lotNumber: String? = null,
    val performer: String? = null,
    val careUnitName: String? = null,
    val careUnitId: String? = null,
    val status: String? = null
)

data class ImmunizationListResponse(
    val immunizations: List<VardguidenImmunization>? = null
)
