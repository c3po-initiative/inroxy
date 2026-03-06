package inroxy.model

data class VardguidenAppointment(
    val id: String? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val status: String? = null,
    val careUnitName: String? = null,
    val careUnitId: String? = null,
    val careGiverName: String? = null,
    val visitType: String? = null,
    val description: String? = null,
    val location: String? = null,
    val isVideoVisit: Boolean? = null,
    val isCancellable: Boolean? = null
)

data class AppointmentListResponse(
    val appointments: List<VardguidenAppointment>? = null
)
