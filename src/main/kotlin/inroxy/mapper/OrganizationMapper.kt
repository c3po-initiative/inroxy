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

        source.address?.let { addr ->
            org.addAddress().apply {
                addr.street?.let { addLine(it) }
                city = addr.city
                postalCode = addr.postalCode
                country = addr.country
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
