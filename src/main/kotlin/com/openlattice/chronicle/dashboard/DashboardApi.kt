package com.openlattice.chronicle.dashboard

import com.openlattice.chronicle.study.StudyApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

public interface DashboardApi {
    public companion object {
        public const val DASHBOARD_PATH: String = "/dashboard"
        public const val STATS_PATH: String = "/stats"
        public const val EVENTS_PATH: String = "/events"
    }

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + DASHBOARD_PATH + STATS_PATH)
    public fun getStats(@Path(StudyApi.STUDY_ID) studyId: UUID): StudyRealtimeStats

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + DASHBOARD_PATH + EVENTS_PATH)
    public fun getRecentEvents(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Query("limit") limit: Int = 100,
        @Query("since") since: String? = null
    ): List<StudyEvent>
}
