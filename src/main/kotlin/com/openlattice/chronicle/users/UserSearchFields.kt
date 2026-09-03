package com.openlattice.chronicle.users

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

public data class UserSearchFields(
    @field:Email(message = "Invalid email format")
    @field:Size(max = 255, message = "Email exceeds maximum length")
    val email: String?,

    @field:Size(max = 255, message = "Name exceeds maximum length")
    val name: String?
) {
    init {
        // TODO - support multiple fields and construct a valid query string for the configured user directory.
        // https://jira.openlattice.com/browse/LATTICE-2805
        require(email == null || name == null) { "only one of \"email\", \"name\" are allowed" }
        require(email != null || name != null) { "one of \"email\", \"name\" is required" }
        if (email != null) {
            require(email.isNotBlank()) { "email cannot be blank" }
        }
        if (name != null) {
            require(name.isNotBlank()) { "name cannot be blank" }
        }
    }
}
