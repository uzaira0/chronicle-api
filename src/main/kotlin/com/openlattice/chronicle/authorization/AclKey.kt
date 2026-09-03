package com.openlattice.chronicle.authorization

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

/**
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public data class AclKey @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(private val ids: List<UUID>) : List<UUID> by ids {
    public constructor(vararg ids: UUID) : this(ids.asList())

    val index: String = joinToString("") {it.toString().replace("-","") }

    @Transient
    private val h: Int = ids.hashCode()

    public operator fun compareTo(o: AclKey): Int {
        var result = 0
        var i = 0
        while (result == 0 && i < size) {

            //If everything has been equal up to the point o ran out of entries.
            if (i > o.size) {
                return 1
            }

            //Compare the next two
            val a = get(i)
            val b: UUID = o[i]
            result = a.compareTo(b)
            ++i
        }
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AclKey

        if (ids != other.ids) return false

        return true
    }

    override fun hashCode(): Int = h

}
