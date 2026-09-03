package com.openlattice.chronicle.study

import com.openlattice.chronicle.base.OK
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@getmethodic.com&gt;
 */
public interface StudyComplianceApi {
    public companion object {
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/compliance"

        /**
         * The researcher dashboard calls the stable, unversioned `/chronicle/api/web`
         * boundary, which BOTH reverse proxies rewrite to `/chronicle/v3/...`
         * (production Traefik's `chronicle-web-strip` replacepathregex, and the
         * self-host Caddy `uri replace`). Compliance was the one dashboard-facing
         * controller mapped without the `/v3` prefix, so every rewritten request
         * landed on an unmapped path and the Compliance tab 404'd for every study.
         * The unversioned [CONTROLLER] mapping is retained for the Retrofit client
         * and any existing caller.
         */
        public const val V3_CONTROLLER: String = "/v3" + CONTROLLER

        public const val BASE: String = SERVICE + CONTROLLER
        public const val V3_BASE: String = SERVICE + V3_CONTROLLER

        public const val STUDY_ID: String = "studyId"
        public const val STUDY: String = "/study"
        public const val NOTIFICATION: String = "/notifications"


        public const val STUDY_ID_PATH: String = "/{$STUDY_ID}"

    }
    @GET(BASE + STUDY + STUDY_ID_PATH)
    public fun getStudyComplianceViolations(@Path(STUDY_ID) studyId: UUID): Map<UUID, Map<String, List<ComplianceViolation>>>

    @POST(BASE + NOTIFICATION)
    public fun triggerStudyComplianceNotifications(@Body studyIds: Set<UUID>) : OK

    @GET(BASE + NOTIFICATION)
    public fun triggerComplianceNotificationsForAllStudies() : OK
}
