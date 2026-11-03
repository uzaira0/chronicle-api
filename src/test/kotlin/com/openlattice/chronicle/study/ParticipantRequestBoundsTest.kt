package com.openlattice.chronicle.study

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.KotlinModule
import jakarta.validation.Validation
import java.util.Optional
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

/** The participant edit requests as the dashboard sends them, and the bounds the server enforces on them. */
class ParticipantRequestBoundsTest {
    private val mapper = ObjectMapper().registerModule(Jdk8Module()).registerModule(KotlinModule.Builder().build())
    private val validator = Validation.byDefaultProvider().configure()
        .messageInterpolator(ParameterMessageInterpolator()).buildValidatorFactory().validator

    private fun violations(value: Any): Set<String> = validator.validate(value).map { it.message }.toSet()

    @Test
    fun anAnnotationsUpdateOmitsWhatItDoesNotChange() {
        assertEquals(ParticipantAnnotationsUpdate(), mapper.readValue("{}", ParticipantAnnotationsUpdate::class.java))
        assertEquals("{}", mapper.writeValueAsString(ParticipantAnnotationsUpdate()))
        val replace = ParticipantAnnotationsUpdate(Optional.of("note"), Optional.of(listOf("tag")))
        val json = """{"participantNotes":"note","participantTags":["tag"]}"""
        assertEquals(replace, mapper.readValue(json, ParticipantAnnotationsUpdate::class.java))
        assertEquals(mapper.readTree(json), mapper.readTree(mapper.writeValueAsString(replace)))
    }

    @Test
    fun anAnnotationsUpdateOutsideItsBoundsIsRefused() {
        assertEquals(emptySet<String>(), violations(ParticipantAnnotationsUpdate(Optional.of("note"), Optional.of(listOf("tag")))))
        assertEquals(setOf("Participant notes exceed maximum length"),
            violations(ParticipantAnnotationsUpdate(participantNotes = Optional.of("n".repeat(50_001)))))
        assertEquals(setOf("Participant has too many tags"),
            violations(ParticipantAnnotationsUpdate(participantTags = Optional.of(List(1001) { "tag" }))))
        assertEquals(setOf("Participant tag exceeds maximum length"),
            violations(ParticipantAnnotationsUpdate(participantTags = Optional.of(listOf("t".repeat(256))))))
    }

    /** Jackson's String element deserializer refuses an object element before the model is built. */
    @Test
    fun aTagListWithAnObjectTagIsRefusedByTheReader() {
        val refused = assertThrows(MismatchedInputException::class.java) {
            mapper.readValue("""{"participantTags":["tag",{"label":"x"}]}""", ParticipantAnnotationsUpdate::class.java)
        }
        assertEquals("participantTags", refused.path.first().fieldName)
    }

    /**
     * Jackson reads a JSON `null` element of `List<String>` as a null entry and does not refuse it, so the
     * model's own guard is the only check. Without it `["a",null]` is stored as `{a,NULL}` and every later
     * participant list read fails.
     */
    @Test
    fun aTagListWithANullTagIsRefusedByTheModel() {
        val refused = assertThrows(ValueInstantiationException::class.java) {
            mapper.readValue("""{"participantTags":["tag",null]}""", ParticipantAnnotationsUpdate::class.java)
        }
        assertEquals("Participant tags must contain only strings", refused.cause?.message)
        assertTrue(refused.cause is IllegalArgumentException)
    }

    @Test
    fun aProcessingBlockNeedsABoundedReason() {
        val request = ParticipantProcessingBlockRequest("Participant asked to pause analysis")
        assertEquals(request, mapper.readValue(mapper.writeValueAsString(request), ParticipantProcessingBlockRequest::class.java))
        assertEquals(emptySet<String>(), violations(request))
        assertTrue(violations(ParticipantProcessingBlockRequest(" ")).isNotEmpty())
        assertEquals(setOf("Reason exceeds maximum length"), violations(ParticipantProcessingBlockRequest("r".repeat(4001))))
    }
}
