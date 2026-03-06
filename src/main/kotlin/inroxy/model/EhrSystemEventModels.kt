package inroxy.model

data class EhrSystemEventsResponse(
    val ehrSystemEvents: List<EhrSystemEvent>? = null
)

data class EhrSystemEvent(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val date: String? = null,
    val careUnitName: String? = null,
    val careUnitId: String? = null
)
