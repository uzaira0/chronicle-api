package com.openlattice.chronicle

import com.openlattice.chronicle.study.ParticipantProcessingApi
import com.openlattice.chronicle.study.StudyApi
import com.openlattice.chronicle.study.StudyLifecycleApi
import com.openlattice.chronicle.survey.SurveyApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.yaml.snakeyaml.Yaml
import retrofit2.http.DELETE
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import java.io.File
import java.lang.reflect.Method

class EditRevisionContractTest {
    @Test
    fun everyRevisionReadHasAConditionalRetrofitEditAndPublishedContract() {
        val specification = File("chronicle.yaml").inputStream().use { Yaml().load<Map<String, Any>>(it) }
        val paths = specification["paths"] as Map<*, *>
        val components = specification["components"] as Map<*, *>
        val parameterDefinitions = components["parameters"] as Map<*, *>
        val edits = listOf(StudyApi::class.java, SurveyApi::class.java).flatMap { it.methods.toList() }
            .mapNotNull { method -> method.getAnnotation(PATCH::class.java)?.let { it.value to method } }.toMap()
        val revisions = EditRevisionApi::class.java.methods.mapNotNull { it.getAnnotation(HEAD::class.java)?.value }
        assertTrue(revisions.isNotEmpty())
        for (path in revisions) {
            val method = edits.getValue(path)
            assertTrue("$path Retrofit edit must carry its revision", method.parameterAnnotations.flatten()
                .filterIsInstance<Header>().any { it.value == EditRevisionApi.IF_MATCH })
            val operations = paths[path] as Map<*, *>
            val patch = operations["patch"] as Map<*, *>
            assertTrue("$path OpenAPI edit must require its revision", requiresIfMatch(patch, parameterDefinitions))
            val failures = patch["responses"] as Map<*, *>
            assertTrue(failures.keys.containsAll(setOf("412", "428")))
            val head = operations["head"] as Map<*, *>
            val response = (head["responses"] as Map<*, *>)["204"] as Map<*, *>
            assertNotNull((response["headers"] as Map<*, *>)[EditRevisionApi.ETAG])
        }
        assertEquals(revisions.size, revisions.toSet().size)
    }

    /**
     * The revisioned status, processing-block and lifecycle writes: their published contract requires If-Match, and
     * the typed client sends it explicitly (no fixture interceptor supplies it).
     */
    @Test
    fun revisionedResearcherWritesCarryIfMatchInTheirRetrofitCall() {
        val specification = File("chronicle.yaml").inputStream().use { Yaml().load<Map<String, Any>>(it) }
        val parameterDefinitions = (specification["components"] as Map<*, *>)["parameters"] as Map<*, *>
        val paths = specification["paths"] as Map<*, *>
        val participant = "${StudyApi.BASE}/{studyId}/participant/{participantId}"
        val routes = setOf(
            "PATCH $participant/status",
            "POST $participant/processing-block",
            "DELETE $participant/processing-block",
            "POST ${StudyApi.BASE}/{studyId}/archive",
            "POST ${StudyApi.BASE}/{studyId}/unarchive",
        )
        val calls = listOf(StudyApi::class.java, ParticipantProcessingApi::class.java, StudyLifecycleApi::class.java)
            .flatMap { it.methods.toList() }
            .mapNotNull { method -> route(method)?.takeIf { it in routes }?.let { it to method } }
            .toMap()
        assertEquals(routes, calls.keys)
        for ((route, method) in calls) {
            val (verb, path) = route.split(' ')
            val operation = (paths[path] as Map<*, *>)[verb.lowercase()] as Map<*, *>
            assertTrue("$route OpenAPI must require If-Match", requiresIfMatch(operation, parameterDefinitions))
            assertTrue("$route Retrofit call must send If-Match", method.parameterAnnotations.flatten()
                .filterIsInstance<Header>().any { it.value == EditRevisionApi.IF_MATCH })
        }
    }

    private fun requiresIfMatch(operation: Map<*, *>, definitions: Map<*, *>): Boolean =
        (operation["parameters"] as List<*>).map { parameter ->
            (parameter as Map<*, *>)["\$ref"]?.let { definitions[it.toString().substringAfterLast('/')] as Map<*, *> } ?: parameter
        }.any { it["name"] == EditRevisionApi.IF_MATCH && it["in"] == "header" && it["required"] == true }

    private fun route(method: Method): String? = method.getAnnotation(PATCH::class.java)?.let { "PATCH ${it.value}" }
        ?: method.getAnnotation(POST::class.java)?.let { "POST ${it.value}" }
        ?: method.getAnnotation(DELETE::class.java)?.let { "DELETE ${it.value}" }
        ?: method.getAnnotation(PUT::class.java)?.let { "PUT ${it.value}" }
}
