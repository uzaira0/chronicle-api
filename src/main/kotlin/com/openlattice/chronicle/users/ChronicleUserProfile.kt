package com.openlattice.chronicle.users

public data class ChronicleUserProfile(
    val id: String,
    val email: String? = null,
    val name: String? = null,
    val nickname: String? = null,
    val givenName: String? = null,
    val familyName: String? = null,
    val username: String? = null,
    val connections: Set<String> = emptySet(),
    val identityUserIds: Set<String> = emptySet(),
)
