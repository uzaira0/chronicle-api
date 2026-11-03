package com.openlattice.chronicle

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.common.reflect.ClassPath
import com.openlattice.chronicle.authorization.AclKey
import com.openlattice.chronicle.serialization.ChronicleCallAdapterFactory
import com.openlattice.chronicle.serialization.ChronicleJacksonConverterFactory
import com.openlattice.chronicle.study.StudyApi
import com.openlattice.chronicle.survey.SurveyApi
import com.openlattice.chronicle.users.DirectedAclKeys
import com.openlattice.chronicle.users.PrincipalApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.HTTP
import java.util.UUID

/**
 * Retrofit rejects a `@Body` parameter on an HTTP method declared without a request body
 * ("Non-body HTTP method cannot contain @Body"), so such a declaration cannot be invoked at all.
 * Every bodied DELETE must be declared with `@HTTP(method = "DELETE", hasBody = true)`.
 */
class RetrofitBodyAnnotationTest {

    /** Compiled interfaces from this module, including nested interfaces. */
    private val apis: List<Class<*>> = ClassPath.from(StudyApi::class.java.classLoader).allClasses.asSequence()
        .filter { it.packageName.startsWith("com.openlattice.chronicle") }
        .map { it.load() }
        .filter { it.isInterface && it.protectionDomain.codeSource.location == StudyApi::class.java.protectionDomain.codeSource.location }
        .toList()

    @Test
    fun noBodyParameterOnABodylessHttpMethod() {
        assertTrue(PrincipalApi::class.java in apis && SurveyApi::class.java in apis)
        apis.forEach { api ->
            api.methods.forEach { method ->
                val hasBodyParam = method.parameterAnnotations.any { annotations ->
                    annotations.any { it is Body }
                }
                if (hasBodyParam) {
                    val bodyless = method.isAnnotationPresent(DELETE::class.java) ||
                        method.isAnnotationPresent(GET::class.java) ||
                        method.isAnnotationPresent(HEAD::class.java) ||
                        method.getAnnotation(HTTP::class.java)?.hasBody == false
                    assertTrue(
                        "${api.simpleName}.${method.name} declares @Body on a body-less HTTP method",
                        !bodyless
                    )
                }
            }
        }
    }

    /** Each bodied DELETE, invoked through Retrofit, sends a DELETE whose body is exactly the argument's JSON. */
    @Test
    fun everyBodiedDeleteTransmitsItsJsonBodyUnchanged() {
        val mapper = jacksonObjectMapper()
        val sent = mutableListOf<Request>()
        var reply = ""
        val retrofit = Retrofit.Builder().baseUrl("https://chronicle.example/")
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                sent += chain.request()
                Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).message("OK")
                    .body(reply.toResponseBody("application/json".toMediaType())).build()
            }.build())
            .addCallAdapterFactory(ChronicleCallAdapterFactory())
            .addConverterFactory(ChronicleJacksonConverterFactory(mapper))
            .build()
        val keys = DirectedAclKeys(AclKey(UUID.randomUUID(), UUID.randomUUID()), AclKey(UUID.randomUUID()))
        val packages = linkedSetOf("org.example.åß", "com.example.app")
        val ok = """{"msg":"SUCCESS"}"""
        val calls = mapOf<String, Triple<Any, String, () -> Unit>>(
            "PrincipalApi.removePrincipalFromPrincipal" to Triple(keys, ok) {
                retrofit.create(PrincipalApi::class.java).removePrincipalFromPrincipal(keys)
            },
            "SurveyApi.allowAppForStudyAppUsageSurvey" to Triple(packages, ok) {
                retrofit.create(SurveyApi::class.java).allowAppForStudyAppUsageSurvey(UUID.randomUUID(), packages)
            },
            "StudyApi.deleteStudyParticipants" to Triple(packages, "[]") {
                retrofit.create(StudyApi::class.java).deleteStudyParticipants(UUID.randomUUID(), packages)
            },
        )
        val bodiedDeletes = apis.flatMap { api ->
            api.methods.filter { method ->
                method.getAnnotation(HTTP::class.java)?.method == "DELETE" &&
                    method.parameterAnnotations.any { annotations -> annotations.any { it is Body } }
            }.map { "${api.simpleName}.${it.name}" }
        }.toSet()
        assertEquals(calls.keys, bodiedDeletes)
        calls.forEach { (name, call) ->
            sent.clear()
            reply = call.second
            call.third()
            val request = sent.single()
            assertEquals(name, "DELETE", request.method)
            val body = Buffer().also { checkNotNull(request.body).writeTo(it) }.readUtf8()
            assertEquals(name, mapper.writeValueAsString(call.first), body)
        }
    }
}
