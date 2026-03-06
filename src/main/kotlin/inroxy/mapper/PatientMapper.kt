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

        if (profile.firstName != null || profile.lastName != null) {
            patient.addName().apply {
                family = profile.lastName
                profile.firstName?.let { addGiven(it) }
            }
        }

        return patient
    }
}
