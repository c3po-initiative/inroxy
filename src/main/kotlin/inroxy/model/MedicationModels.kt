package inroxy.model

data class Medication(
    val id: String? = null,
    val name: String? = null,
    val atcCode: String? = null,
    val dosage: String? = null,
    val form: String? = null,
    val strength: String? = null,
    val prescribedDate: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val status: String? = null,
    val prescriber: String? = null,
    val prescriberCareUnit: String? = null,
    val prescriberCareUnitId: String? = null,
    val instructions: String? = null,
    val nplPackId: String? = null
)

data class MedicationListResponse(
    val medications: List<Medication>? = null
)
