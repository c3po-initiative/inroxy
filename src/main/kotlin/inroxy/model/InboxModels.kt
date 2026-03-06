package inroxy.model

data class InboxMessage(
    val id: String? = null,
    val subject: String? = null,
    val senderName: String? = null,
    val senderId: String? = null,
    val receivedDate: String? = null,
    val isRead: Boolean? = null,
    val content: String? = null,
    val contentType: String? = null,
    val attachments: List<MessageAttachment>? = null,
    val careUnitName: String? = null,
    val careUnitId: String? = null
)

data class MessageAttachment(
    val id: String? = null,
    val fileName: String? = null,
    val contentType: String? = null,
    val size: Long? = null
)

data class InboxListResponse(
    val messages: List<InboxMessage>? = null
)
