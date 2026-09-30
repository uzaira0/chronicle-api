package com.openlattice.chronicle.models

import com.openlattice.chronicle.study.ParticipantDataType
import com.openlattice.chronicle.study.StudyApi
import com.openlattice.chronicle.util.RetrofitBuilders
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.time.OffsetDateTime
import java.util.UUID

class ParticipantDownloadContractTest {
    @Test
    fun `Retrofit requests JSON rows for diagnostics and quality alerts`() {
        val studyId = UUID.randomUUID()
        val start = OffsetDateTime.parse("2026-09-05T00:00:00-04:00")
        val end = OffsetDateTime.parse("2026-09-06T01:00:00-03:00")
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request()
            assertEquals("/chronicle/v3/study/$studyId/participants/data", request.url.encodedPath)
            assertEquals(listOf("p1", "p2"), request.url.queryParameterValues("participantId"))
            assertEquals(start.toString(), request.url.queryParameter("startDate"))
            assertEquals(end.toString(), request.url.queryParameter("endDate"))
            assertEquals("json", request.url.queryParameter("responseType"))
            Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(200).message("OK")
                .header("Content-Type", "application/json")
                .body("[{\"event_id\":\"opaque-id\"}]".toResponseBody("application/json".toMediaType())).build()
        }.build()
        val api = RetrofitBuilders.decorateWithRhizomeFactories(
            RetrofitBuilders.createBaseChronicleRetrofitBuilder("http://localhost/", client),
        ).build().create(StudyApi::class.java)
        listOf(ParticipantDataType.UploadDiagnostics, ParticipantDataType.DataQualityAlerts).forEach { type ->
            assertEquals("opaque-id", api.getParticipantsData(studyId, type, linkedSetOf("p1", "p2"), start, end)
                .single()["event_id"])
        }
    }

    @Test
    fun `OpenAPI download parameters and representations match the server`() {
        val spec = File("chronicle.yaml").inputStream().use { Yaml().load<Map<String, Any>>(it) }
        val paths = spec["paths"]!! as Map<*, *>
        val path = paths["/chronicle/v3/study/{studyId}/participants/data"]!! as Map<*, *>
        val operation = path["get"]!! as Map<*, *>
        val parameters = (operation["parameters"]!! as List<*>).map { it as Map<*, *> }
            .filter { it["in"] == "query" }.associateBy { it["name"] }
        assertEquals(setOf("dataType", "participantId", "startDate", "endDate", "responseType", "fileName", "sensorTypes"),
            parameters.keys)
        listOf("dataType", "participantId", "startDate", "endDate").forEach { name ->
            assertEquals("$name must be required", true, parameters.getValue(name)["required"])
        }
        listOf("responseType", "fileName", "sensorTypes").forEach { name ->
            assertTrue("$name must be optional", parameters.getValue(name)["required"] != true)
        }
        val participant = parameters["participantId"]!!
        assertEquals("form", participant["style"])
        assertEquals(true, participant["explode"])
        val participantSchema = participant["schema"]!! as Map<*, *>
        assertEquals(1, participantSchema["minItems"])
        assertEquals(100, participantSchema["maxItems"])
        listOf("startDate", "endDate").forEach { name ->
            assertEquals("date-time", (parameters.getValue(name)["schema"]!! as Map<*, *>)["format"])
        }
        val responseType = parameters["responseType"]!!["schema"]!! as Map<*, *>
        assertEquals("csv", responseType["default"])
        assertEquals(listOf("csv", "json"), responseType["enum"])
        val responses = operation["responses"]!! as Map<*, *>
        val success = responses["200"]!! as Map<*, *>
        val content = success["content"]!! as Map<*, *>
        assertEquals(setOf("application/json", "text/csv"), content.keys)
        val json = content["application/json"]!! as Map<*, *>
        assertEquals("array", (json["schema"]!! as Map<*, *>)["type"])
        val csv = content["text/csv"]!! as Map<*, *>
        assertEquals("string", (csv["schema"]!! as Map<*, *>)["type"])
    }
}
