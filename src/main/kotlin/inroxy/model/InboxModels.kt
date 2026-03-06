package inroxy.model

data class InboxMessage(
    val id: String? = null,
    val threadTitle: String? = null,
    val threadLabel: String? = null,
    val facilityName: String? = null,
    val messageDate: String? = null,
    val readStatus: String? = null,
    val favorite: Boolean? = null,
    val messageUrl: String? = null,
    val hasAttachment: Boolean? = null,
    val messagesInThread: Int? = null
)
