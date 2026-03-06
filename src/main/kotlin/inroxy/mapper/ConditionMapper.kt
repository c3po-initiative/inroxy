package inroxy.mapper

import inroxy.model.Diagnosis
import org.hl7.fhir.r4.model.*
import org.springframework.stereotype.Component

@Component
class ConditionMapper {

    fun toFhir(source: Diagnosis): Condition {
        val condition = Condition()

        source.code?.let { code ->
            condition.id = code
            condition.code = CodeableConcept().apply {
                addCoding().apply {
                    this.code = source.code
                    system = source.codeSystem ?: "http://icd.who.int/browse10"
                    display = source.displayName
                }
                text = source.displayName
            }
        }

        condition.clinicalStatus = CodeableConcept().apply {
            addCoding().apply {
                system = "http://terminology.hl7.org/CodeSystem/condition-clinical"
                code = "active"
            }
        }

        condition.verificationStatus = CodeableConcept().apply {
            addCoding().apply {
                system = "http://terminology.hl7.org/CodeSystem/condition-ver-status"
                code = "confirmed"
            }
        }

        source.date?.let { dateStr ->
            parseDate(dateStr)?.let { date ->
                condition.onset = DateTimeType(date)
            }
        }

        return condition
    }

    fun toFhirList(sources: List<Diagnosis>): List<Condition> =
        sources.map { toFhir(it) }

    private fun parseDate(dateStr: String): java.util.Date? =
        try {
            java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateStr)
        } catch (e: Exception) {
            try {
                java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateStr)
            } catch (e2: Exception) {
                null
            }
        }
}
