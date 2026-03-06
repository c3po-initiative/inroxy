package inroxy.model

data class JournalEntry(
    val id: String? = null,
    val date: String? = null,
    val title: String? = null,
    val careUnitName: String? = null,
    val careUnitId: String? = null,
    val careContactType: String? = null,
    val diagnoses: List<Diagnosis>? = null,
    val observations: List<LabResult>? = null,
    val notes: List<ClinicalNote>? = null
)

data class Diagnosis(
    val code: String? = null,
    val codeSystem: String? = null,
    val displayName: String? = null,
    val date: String? = null
)

data class LabResult(
    val id: String? = null,
    val name: String? = null,
    val value: String? = null,
    val unit: String? = null,
    val referenceRange: String? = null,
    val date: String? = null,
    val status: String? = null,
    val comment: String? = null
)

data class ClinicalNote(
    val id: String? = null,
    val type: String? = null,
    val text: String? = null,
    val author: String? = null,
    val date: String? = null
)

data class JournalListResponse(
    val entries: List<JournalEntry>? = null
)
