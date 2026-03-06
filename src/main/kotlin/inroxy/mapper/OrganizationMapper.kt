package inroxy.mapper

import inroxy.model.CareUnit
import org.hl7.fhir.r4.model.*
import org.springframework.stereotype.Component

@Component
class OrganizationMapper {

    companion object {
        const val HSA_ID_SYSTEM = "urn:oid:1.2.752.29.4.19"
    }

    fun toFhir(source: CareUnit): Organization {
        val org = Organization()

        source.id?.let { org.id = it }
        source.name?.let { org.name = it }

        source.hsaId?.let { hsaId ->
            org.addIdentifier().apply {
                system = HSA_ID_SYSTEM
                value = hsaId
            }
        }

        source.phoneNumber?.let { phone ->
            org.addTelecom().apply {
                system = ContactPoint.ContactPointSystem.PHONE
                value = phone
            }
        }

        source.email?.let { email ->
            org.addTelecom().apply {
                system = ContactPoint.ContactPointSystem.EMAIL
                value = email
            }
        }

        source.website?.let { url ->
            org.addTelecom().apply {
                system = ContactPoint.ContactPointSystem.URL
                value = url
            }
        }

        if (source.street != null || source.city != null || source.postalCode != null) {
            org.addAddress().apply {
                source.street?.let { addLine(it) }
                city = source.city
                postalCode = source.postalCode
                country = "SE"
            }
        }

        source.type?.let { t ->
            org.addType(CodeableConcept().apply { text = t })
        }

        return org
    }

    fun toFhirList(sources: List<CareUnit>): List<Organization> =
        sources.map { toFhir(it) }
}
