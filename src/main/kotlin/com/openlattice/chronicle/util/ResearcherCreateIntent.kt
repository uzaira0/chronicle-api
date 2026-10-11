package com.openlattice.chronicle.util

import com.openlattice.chronicle.apikey.ApiKeyApi
import com.openlattice.chronicle.export.ExportApi
import com.openlattice.chronicle.study.StudyApi
import com.openlattice.chronicle.survey.SurveyApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.security.MessageDigest
import java.util.UUID

/**
 * Install one instance per logical researcher create, and reuse it on every retry.
 * Persist [key] with the request if retrying after restart. The first route/body binding is
 * immutable: editing the payload requires an explicitly new intent rather than another retry.
 */
public class ResearcherCreateIntent(public val key: String = UUID.randomUUID().toString()) : Interceptor {
    private var binding: Pair<String, List<Byte>>? = null

    init {
        require(key.length in 1..128 && key.matches(Regex("[A-Za-z0-9_-]+"))) { "Invalid create intent key" }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val route = request.url.encodedPath.removePrefix(StudyApi.SERVICE).trimEnd('/')
        if (request.method != "POST" || createRoutes.none { it.matches(route) }) return chain.proceed(request)
        val body = request.body ?: throw IOException("Create intent requires a request body")
        if (body.isOneShot() || body.isDuplex()) throw IOException("Create intent requires a replayable body")
        val bytes = Buffer().also(body::writeTo).readByteArray()
        val current = request.url.toString() to MessageDigest.getInstance("SHA-256").digest(bytes).toList()
        synchronized(this) {
            if (binding != null && binding != current) throw IOException("Create intent cannot retry a changed route or body")
            binding = current
        }
        val explicit = request.header("Idempotency-Key")
        if (explicit != null && explicit != key) throw IOException("Create intent key does not match request")
        return chain.proceed(request.newBuilder().header("Idempotency-Key", key).build())
    }
}

private val createRoutes = listOf(
    StudyApi.CONTROLLER,
    StudyApi.CONTROLLER + StudyApi.STUDY_ID_PATH + ApiKeyApi.API_KEYS_PATH,
    StudyApi.CONTROLLER + StudyApi.STUDY_ID_PATH + ExportApi.EXPORT_PATH + ExportApi.ASYNC_PATH,
    SurveyApi.CONTROLLER + SurveyApi.STUDY_ID_PATH + SurveyApi.QUESTIONNAIRE_PATH,
).map { Regex(it.replace("{${StudyApi.STUDY_ID}}", "[0-9a-fA-F-]+")) }

/** A dedicated adapter for one researcher create, reused with the same DTO after response loss. */
public fun createResearcherCreateRetrofitBuilder(
    baseUrl: String,
    httpClient: OkHttpClient,
    intent: ResearcherCreateIntent,
): Retrofit.Builder = RetrofitBuilders.createBaseChronicleRetrofitBuilder(
    baseUrl, httpClient.newBuilder().addInterceptor(intent).build(),
)
