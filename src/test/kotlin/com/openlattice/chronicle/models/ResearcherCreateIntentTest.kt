package com.openlattice.chronicle.models

import com.openlattice.chronicle.study.StudyApi
import com.openlattice.chronicle.study.Study
import com.openlattice.chronicle.util.createResearcherCreateRetrofitBuilder
import com.openlattice.chronicle.util.ResearcherCreateIntent
import com.openlattice.chronicle.util.RetrofitBuilders
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.util.UUID
import java.io.IOException

class ResearcherCreateIntentTest {
    private fun researcherClient(intent: ResearcherCreateIntent, transport: Interceptor): OkHttpClient =
        (createResearcherCreateRetrofitBuilder("http://localhost/", OkHttpClient(), intent).build()
            .callFactory() as OkHttpClient).newBuilder().addInterceptor(transport).build()

    @Test fun sdkRetransmitsTheSameCreateIdentityAndRejectsAnEditedRetry() {
        val intent = ResearcherCreateIntent("sdk-logical-create")
        val requests = mutableListOf<String?>()
        val id = UUID.randomUUID()
        val client = researcherClient(intent) { chain ->
            requests.add(chain.request().header("Idempotency-Key"))
            if (requests.size == 1) throw IOException("response lost")
            Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).message("OK")
                .header("Content-Type", "application/json").body("\"$id\"".toResponseBody("application/json".toMediaType())).build()
        }
        val api = RetrofitBuilders.decorateWithRhizomeFactories(
            RetrofitBuilders.createBaseChronicleRetrofitBuilder("http://localhost/", client)
        ).build().create(StudyApi::class.java)
        val study = Study(title = "SDK study", contact = "research@example.org")
        assertThrows(Exception::class.java) { api.createStudy(study) }
        assertEquals(id, api.createStudy(study))
        assertEquals(listOf(intent.key, intent.key), requests)
        assertThrows(Exception::class.java) { api.createStudy(Study(title = "changed", contact = "research@example.org")) }
        assertEquals(2, requests.size)
    }

    @Test fun addsIntentOnlyToTheFourResearcherCreateRoutes() {
        val studyId = UUID.randomUUID()
        val routes = listOf(
            "/chronicle/v3/study",
            "/chronicle/v3/study/$studyId/api-keys",
            "/chronicle/v3/study/$studyId/export/async",
            "/chronicle/v3/survey/$studyId/questionnaire",
        )
        for (route in routes + "/chronicle/v3/study/$studyId/participant") {
            val intent = ResearcherCreateIntent("route-intent")
            var observedKey: String? = null
            val client = researcherClient(intent) { chain ->
                observedKey = chain.request().header("Idempotency-Key")
                Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).message("OK")
                    .body("{}".toResponseBody("application/json".toMediaType())).build()
            }
            client.newCall(Request.Builder().url("http://localhost$route").post(
                "{}".toRequestBody("application/json".toMediaType())
            ).build()).execute().close()
            assertEquals(if (route in routes) intent.key else null, observedKey)
        }
    }

}
