package com.openlattice.chronicle.candidates

import retrofit2.http.Body
import retrofit2.http.POST
import java.util.UUID

@Deprecated("Candidate endpoints are deprecated. Candidate data is no longer stored.")
public interface CandidateApi {

    public companion object {
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/v3/candidate"
        public const val BASE: String = SERVICE + CONTROLLER
    }

    @POST(BASE)
    public fun registerCandidate(@Body candidate: Candidate): UUID
}
