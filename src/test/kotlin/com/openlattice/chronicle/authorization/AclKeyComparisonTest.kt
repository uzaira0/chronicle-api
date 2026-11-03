package com.openlattice.chronicle.authorization

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

/**
 * Regression coverage for the prefix-comparison bug: a longer key indexed past the end of
 * the shorter operand and a shorter key compared equal to a key it merely prefixes, which
 * collapsed distinct authorization keys in sorted collections.
 */
class AclKeyComparisonTest {

    private val a = UUID.fromString("00000000-0000-0000-0000-00000000000a")
    private val b = UUID.fromString("00000000-0000-0000-0000-00000000000b")

    @Test
    fun longerKeySortsAfterItsOwnPrefix() {
        assertTrue(AclKey(a, b) > AclKey(a))
    }

    @Test
    fun shorterKeySortsBeforeAKeyItPrefixes() {
        assertTrue(AclKey(a) < AclKey(a, b))
    }

    @Test
    fun identicalKeysCompareEqual() {
        assertEquals(0, AclKey(a, b).compareTo(AclKey(a, b)))
    }

    @Test
    fun differingKeysOfEqualLengthOrderByFirstDifference() {
        assertTrue(AclKey(a, a) < AclKey(a, b))
        assertTrue(AclKey(a, b) > AclKey(a, a))
    }

    /** Keys of every shape, including ids that differ only in their high bits, order consistently with equals. */
    @Test
    fun compareToAgreesWithEqualsAndIsAntisymmetricForEveryKeyPair() {
        val ids = listOf(a, b, UUID(Long.MIN_VALUE, 0xaL), UUID(0x10L, 0xaL))
        val keys = listOf(AclKey()) + ids.map { AclKey(it) } +
            ids.flatMap { first -> ids.map { AclKey(first, it) } } + ids.map { AclKey(it, a, b) }
        for (left in keys) for (right in keys) {
            val label = "$left vs $right"
            assertEquals(label, left == right, left.compareTo(right) == 0)
            assertEquals(label, Integer.signum(left.compareTo(right)), -Integer.signum(right.compareTo(left)))
        }
        val sorted = sortedSetOf(Comparator<AclKey> { l, r -> l.compareTo(r) }, *keys.reversed().toTypedArray())
        assertEquals(keys.toSet().size, sorted.size)
        sorted.zipWithNext().forEach { (lower, higher) -> assertTrue("$lower < $higher", lower < higher) }
    }

    @Test
    fun sortedSetKeepsAKeyAndItsPrefixDistinct() {
        val keys = sortedSetOf(Comparator<AclKey> { l, r -> l.compareTo(r) }, AclKey(a, b), AclKey(a))
        assertEquals(2, keys.size)
        assertEquals(listOf(AclKey(a), AclKey(a, b)), keys.toList())
    }
}
