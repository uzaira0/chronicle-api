package com.openlattice.chronicle.models

import com.openlattice.chronicle.study.StudyApi
import com.openlattice.chronicle.study.Study
import com.openlattice.chronicle.util.ResearcherCreateIntent
import com.openlattice.chronicle.util.RetrofitBuilders
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import java.util.UUID
import java.io.IOException

class ResearcherCreateIntentTest {
    @Test fun sdkRetransmitsTheSameCreateIdentityAndRejectsAnEditedRetry() {
        val intent = ResearcherCreateIntent("sdk-logical-create")
        val requests = mutableListOf<String?>()
        val id = UUID.randomUUID()
        val client = OkHttpClient.Builder().addInterceptor(intent).addInterceptor { chain ->
            requests.add(chain.request().header("Idempotency-Key"))
            if (requests.size == 1) throw IOException("response lost")
            Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).message("OK")
                .header("Content-Type", "application/json").body("\"$id\"".toResponseBody("application/json".toMediaType())).build()
        }.build()
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
}
