package inroxy.mapper

import inroxy.model.UserProfile
import org.hl7.fhir.r4.model.*
import org.springframework.stereotype.Component

@Component
class PatientMapper {

    companion object {
        const val PERSONNUMMER_SYSTEM = "urn:oid:1.2.752.129.2.1.3.1"
    }

    fun toFhir(profile: UserProfile): Patient {
        val patient = Patient()

        profile.personnummer?.let { pnr ->
            patient.id = pnr
            patient.addIdentifier().apply {
                system = PERSONNUMMER_SYSTEM
                value = pnr
            }
        }

        if (profile.firstName != null || profile.lastName != null) {
            patient.addName().apply {
                family = profile.lastName
                profile.firstName?.let { addGiven(it) }
            }
        }

        profile.email?.let { email ->
            patient.addTelecom().apply {
                system = ContactPoint.ContactPointSystem.EMAIL
                value = email
            }
        }

        profile.phoneNumber?.let { phone ->
            patient.addTelecom().apply {
                system = ContactPoint.ContactPointSystem.PHONE
                value = phone
            }
        }

        profile.address?.let { addr ->
            patient.addAddress().apply {
                addr.street?.let { addLine(it) }
                city = addr.city
                postalCode = addr.postalCode
                country = addr.country
            }
        }

        profile.gender?.let { g ->
            patient.gender = when (g.lowercase()) {
                "male", "man" -> Enumerations.AdministrativeGender.MALE
                "female", "kvinna" -> Enumerations.AdministrativeGender.FEMALE
                "other", "annan" -> Enumerations.AdministrativeGender.OTHER
                else -> Enumerations.AdministrativeGender.UNKNOWN
            }
        }

        profile.birthDate?.let { patient.birthDateElement = DateType(it) }

        return patient
    }
}
