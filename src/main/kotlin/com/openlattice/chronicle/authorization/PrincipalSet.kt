package com.openlattice.chronicle.authorization

public data class PrincipalSet(val principalSet: MutableSet<Principal>) : MutableSet<Principal> by principalSet
