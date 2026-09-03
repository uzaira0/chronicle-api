package com.openlattice.chronicle

import com.openlattice.chronicle.candidates.Candidate
import com.openlattice.chronicle.ids.IdConstants
import org.junit.Assert
import org.junit.Test
import java.util.UUID

class CandidateTests {

    @Test
    fun testNewCandidate() {
        val c1 = Candidate()
        Assert.assertEquals(IdConstants.UNINITIALIZED.id, c1.id)
        c1.id = UUID.randomUUID()
        val c2 = Candidate(c1.id)
        Assert.assertEquals(c1, c2)
    }

    @Test
    fun testCandidateEquality() {
        val c1 = Candidate()
        val c2 = Candidate()
        Assert.assertEquals(c1, c2)
        val id = UUID.randomUUID()
        val c3 = Candidate(id)
        val c4 = Candidate(id)
        Assert.assertEquals(c3, c4)
        Assert.assertNotEquals(c1, c3)
    }
}
