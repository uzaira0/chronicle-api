package com.openlattice.chronicle.models

import com.openlattice.chronicle.ChronicleTestUtils.readBody
import com.openlattice.chronicle.authorization.AclKey
import com.openlattice.chronicle.authorization.AuthorizationsApi
import com.openlattice.chronicle.authorization.AuthorizedObjectsSearchResult
import com.openlattice.chronicle.authorization.Permission
import com.openlattice.chronicle.authorization.SecurableObjectType
import com.openlattice.chronicle.settings.AppUsageFrequency
import com.openlattice.chronicle.sources.AndroidDevice
import com.openlattice.chronicle.study.AndroidDiagnosticCodeSummary
import com.openlattice.chronicle.study.AndroidDiagnosticsHistoryRow
import com.openlattice.chronicle.study.AndroidDiagnosticsPage
import com.openlattice.chronicle.study.EnrollmentResponse
import com.openlattice.chronicle.study.StudyApi
import com.openlattice.chronicle.study.StudyLifecycleApi
import com.openlattice.chronicle.study.StudyUpdate
import com.openlattice.chronicle.survey.AppUsageFrequencyResponse
import com.openlattice.chronicle.survey.SurveyApi
import com.openlattice.chronicle.util.RetrofitBuilders
import com.openlattice.chronicle.util.RetrofitBuilders.mapper
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

class StudyApiClientDefaultsTest {
    private val requests = mutableListOf<Request>()

    private fun <T> api(body: String, type: Class<T>): T {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request()
            requests += request
            Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(200).message("OK")
                .header("Content-Type", "application/json")
                .body(body.toResponseBody("application/json".toMediaType())).build()
        }.build()
        return RetrofitBuilders.decorateWithRhizomeFactories(
            RetrofitBuilders.createBaseChronicleRetrofitBuilder("http://localhost/", client),
        ).build().create(type)
    }

    private fun api(body: String): StudyApi = api(body, StudyApi::class.java)

    private fun HttpUrl.params(): Map<String, String?> = queryParameterNames.associateWith { queryParameter(it) }

    /**
     * The diagnostics history of a study, unfiltered: the client asks for the first page of 50 with no filter, and
     * reads back a device-day row with its retained code and a redacted quality alert (no device, no codes).
     */
    @Test
    fun unfilteredDiagnosticsHistoryAsksForTheFirstPageAndReadsDeviceDaysAndAlerts() {
        val studyId = UUID.randomUUID()
        val device = UUID.randomUUID()
        val page = api("""{"items":[
            {"participantId":"p-001","randomDeviceId":"$device","day":"2026-10-05","codes":[
              {"eventId":"e-1","moduleFamily":"usage_events","issueCode":"UPLOAD_HTTP_ERROR","occurrenceCount":3,
               "firstOccurredAt":"2026-10-05T09:00:00-03:00","lastOccurredAt":"2026-10-05T21:30:00-03:00",
               "httpStatus":503,"errorType":null}]},
            {"participantId":"p-002","randomDeviceId":null,"day":"2026-10-04"}],
            "nextCursor":"2026-10-04~p-002"}""").getAndroidDiagnostics(studyId)

        assertEquals("/chronicle/v3/study/$studyId/participants/android/diagnostics", requests.single().url.encodedPath)
        assertEquals(mapOf("limit" to "50"), requests.single().url.params())
        val decoded = page.items.first().codes.single()
        val first = OffsetDateTime.parse("2026-10-05T09:00:00-03:00")
        val last = OffsetDateTime.parse("2026-10-05T21:30:00-03:00")
        // The wire carries instants; compare them in the offset the server wrote.
        val code = decoded.copy(firstOccurredAt = decoded.firstOccurredAt.withOffsetSameInstant(first.offset),
            lastOccurredAt = decoded.lastOccurredAt.withOffsetSameInstant(last.offset))
        assertEquals(AndroidDiagnosticCodeSummary("e-1", "usage_events", "UPLOAD_HTTP_ERROR", 3, first, last, 503, null), code)
        assertEquals(AndroidDiagnosticsPage(listOf(
            AndroidDiagnosticsHistoryRow("p-001", device, LocalDate.of(2026, 10, 5), listOf(decoded)),
            AndroidDiagnosticsHistoryRow("p-002", null, LocalDate.of(2026, 10, 4)),
        ), "2026-10-04~p-002"), page)
    }

    /** A client that does not page a researcher list asks for its first page, with the size each list documents. */
    @Test
    fun unpagedResearcherListsAskForTheirFirstPage() {
        val studyId = UUID.randomUUID()
        val organizationId = UUID.randomUUID()
        api("[]").run {
            getAllStudies()
            getOrgStudies(organizationId)
            getStudyParticipants(studyId)
            getStudySettingsAudit(studyId)
            getStudyCollectionAcknowledgments(studyId)
        }
        api("{}").getParticipantStats(studyId)

        assertEquals(listOf(
            "/chronicle/v3/study" to mapOf("limit" to "100", "offset" to "0"),
            "/chronicle/v3/study/organization/$organizationId" to mapOf("limit" to "100", "offset" to "0"),
            "/chronicle/v3/study/$studyId/participants" to mapOf("limit" to "100", "offset" to "0"),
            "/chronicle/v3/study/$studyId/settings/audit" to mapOf("limit" to "50", "offset" to "0"),
            "/chronicle/v3/study/$studyId/settings/acknowledgments" to mapOf("limit" to "50", "offset" to "0"),
            "/chronicle/v3/study/$studyId/participants/stats" to mapOf("limit" to "100", "offset" to "0"),
        ), requests.map { it.url.encodedPath to it.url.params() })
    }

    /**
     * A phone that enrolls without an invitation (no code, no manifest digest, no attempt, no proposed key) sends
     * only its device id header, so the server sees an uninvited enrollment rather than empty credentials.
     */
    @Test
    fun anEnrollmentWithoutAnInvitationSendsNoInvitationHeaders() {
        val studyId = UUID.randomUUID()
        val device = AndroidDevice("phone", "model", "codename", "brand", "14", "34", "product", "device-instance")
        val issued = EnrollmentResponse(UUID.randomUUID())

        assertEquals(issued, api(mapper.writeValueAsString(issued)).enrollV4(studyId, "p-001", device.randomDeviceId, device))

        val request = requests.single()
        assertEquals("/chronicle/v4/study/$studyId/participant/p-001/enroll", request.url.encodedPath)
        assertEquals(device.randomDeviceId, request.header("X-Chronicle-Random-Device-Id"))
        listOf("X-Chronicle-Enrollment-Code", "X-Chronicle-Manifest-Digest", "X-Chronicle-Enrollment-Attempt-Id",
            "X-Chronicle-Proposed-Api-Key").forEach { assertNull(it, request.header(it)) }
        assertEquals(device, mapper.readValue(readBody(request.body), AndroidDevice::class.java))
    }

    /**
     * A researcher's study edit defaults to retrieve=false. This fixture declares JSON but returns an empty body,
     * so decoding fails after recording the request. These assertions cover only the request's method, path,
     * query defaults, and serialized update body.
     */
    @Test
    fun aStudyEditDoesNotAskForTheUpdatedStudyByDefault() {
        val studyId = UUID.randomUUID()

        runCatching { api("").updateStudy(studyId, StudyUpdate(title = "Renamed study")) }

        val request = requests.single()
        assertEquals("PATCH" to "/chronicle/v3/study/$studyId", request.method to request.url.encodedPath)
        assertEquals(mapOf("retrieve" to "false"), request.url.params())
        assertEquals("Renamed study", mapper.readValue(readBody(request.body), StudyUpdate::class.java).title)
    }

    /** Cloning a study with no options keeps the settings and lets the server title it "Copy of ...". */
    @Test
    fun cloningAStudyByDefaultCopiesItsSettingsUnderTheServerTitle() {
        val studyId = UUID.randomUUID()
        val clone = UUID.randomUUID()

        assertEquals(clone, api("\"$clone\"", StudyLifecycleApi::class.java).cloneStudy(studyId))

        val request = requests.single()
        assertEquals("POST" to "/chronicle/v3/study/$studyId/clone", request.method to request.url.encodedPath)
        assertEquals(mapper.readTree("""{"newTitle":"","includeSettings":true}"""), mapper.readTree(readBody(request.body)))
    }

    /** The first page of the studies a researcher can read carries no paging token; the reply carries the next one. */
    @Test
    fun theFirstPageOfReadableStudiesCarriesNoPagingToken() {
        val page = AuthorizedObjectsSearchResult("next-page", setOf(AclKey(UUID.randomUUID()), AclKey(UUID.randomUUID())))

        assertEquals(page, api(mapper.writeValueAsString(page), AuthorizationsApi::class.java)
            .getAccessibleObjects(SecurableObjectType.Study, Permission.READ))

        assertEquals(mapOf("objectType" to "Study", "permission" to "READ"), requests.single().url.params())
    }

    /** The participant survey page reads the study's survey frequency to show the daily or the hourly variant. */
    @Test
    fun theSurveyPageReadsTheStudySurveyFrequency() {
        val studyId = UUID.randomUUID()

        assertEquals(AppUsageFrequencyResponse(AppUsageFrequency.DAILY),
            api("""{"appUsageFrequency":"DAILY"}""", SurveyApi::class.java).getAppUsageFrequency(studyId))

        assertEquals("/chronicle/v3/survey/$studyId/app-usage-frequency", requests.single().url.encodedPath)
    }
}
