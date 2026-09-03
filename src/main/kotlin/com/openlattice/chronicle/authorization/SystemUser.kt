package com.openlattice.chronicle.authorization

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@getmethodic.com&gt;
 */
public enum class SystemUser(principalId: String) {
    CHRONICLE("Chronicle");

    public val principal : Principal = Principal( PrincipalType.USER, principalId)
}
