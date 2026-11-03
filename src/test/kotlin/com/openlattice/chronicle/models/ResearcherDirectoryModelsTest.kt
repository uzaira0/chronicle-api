package com.openlattice.chronicle.models

import com.openlattice.chronicle.ids.IdConstants
import com.openlattice.chronicle.organizations.Organization
import com.openlattice.chronicle.users.ChronicleUserProfile
import com.openlattice.chronicle.users.UserSearchFields
import com.openlattice.chronicle.util.RetrofitBuilders.mapper
import jakarta.validation.Validation
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

/** The researcher directory as the dashboard uses it: searching for a colleague, their profile, and organizations. */
class ResearcherDirectoryModelsTest {
    private val validator = Validation.byDefaultProvider().configure()
        .messageInterpolator(ParameterMessageInterpolator()).buildValidatorFactory().validator

    private fun violations(value: Any): Set<String> = validator.validate(value).map { it.message }.toSet()

    /** A search names exactly one of email or name, and neither may be blank. */
    @Test
    fun aColleagueSearchNamesExactlyOneNonBlankField() {
        assertEquals(UserSearchFields("ana@example.org", null),
            mapper.readValue("""{"email":"ana@example.org"}""", UserSearchFields::class.java))
        assertEquals("ana@example.org", UserSearchFields("ana@example.org", null).email)
        assertEquals("Ana", UserSearchFields(null, "Ana").name)
        mapOf(
            "only one of \"email\", \"name\" are allowed" to { UserSearchFields("ana@example.org", "Ana") },
            "one of \"email\", \"name\" is required" to { UserSearchFields(null, null) },
            "email cannot be blank" to { UserSearchFields(" ", null) },
            "name cannot be blank" to { UserSearchFields(null, " ") },
        ).forEach { (message, search) ->
            assertEquals(message, assertThrows(IllegalArgumentException::class.java) { search() }.message)
        }
    }

    /** The search bounds the server enforces before it looks anything up. */
    @Test
    fun aColleagueSearchOutsideItsBoundsIsRefused() {
        assertEquals(emptySet<String>(), violations(UserSearchFields("ana@example.org", null)))
        assertEquals(setOf("Invalid email format"), violations(UserSearchFields("not-an-email", null)))
        assertEquals(setOf("Name exceeds maximum length"), violations(UserSearchFields(null, "n".repeat(256))))
    }

    /** A configured user listed with only an id has no name, email, connections or identity links. */
    @Test
    fun aProfileWithOnlyAnIdHasNothingElse() {
        val profile = mapper.readValue("""{"id":"researcher-1"}""", ChronicleUserProfile::class.java)

        assertEquals(ChronicleUserProfile("researcher-1", null, null, null, null, null, null, emptySet(), emptySet()), profile)
        assertEquals(profile, mapper.readValue(mapper.writeValueAsString(profile), ChronicleUserProfile::class.java))
    }

    /** A new organization has no id until the server assigns one, an empty description, and a required bounded title. */
    @Test
    fun aNewOrganizationNeedsABoundedTitle() {
        val organization = mapper.readValue("""{"title":"Research group"}""", Organization::class.java)

        assertEquals(Organization(IdConstants.UNINITIALIZED.id, "Research group", ""), organization)
        assertEquals(emptySet<String>(), violations(organization))
        assertEquals(setOf("Organization title is required"), violations(Organization(title = " ")))
        assertEquals(setOf("Organization description exceeds maximum length"),
            violations(Organization(title = "Research group", description = "d".repeat(4001))))
    }
}
