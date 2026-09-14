package com.openlattice.chronicle

import com.openlattice.chronicle.authorization.RoleApi
import com.openlattice.chronicle.survey.SurveyApi
import com.openlattice.chronicle.users.PrincipalApi
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.HTTP

/**
 * Retrofit rejects a `@Body` parameter on an HTTP method declared without a request body
 * ("Non-body HTTP method cannot contain @Body"), so such a declaration cannot be invoked at all.
 * These interfaces each previously carried a bodied DELETE declared with `@DELETE`
 * or with `@HTTP(hasBody = false)`.
 */
class RetrofitBodyAnnotationTest {

    private val apis = listOf(PrincipalApi::class.java, RoleApi::class.java, SurveyApi::class.java)

    @Test
    fun noBodyParameterOnABodylessHttpMethod() {
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
}
