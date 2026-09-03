package com.openlattice.chronicle.webhooks

import com.openlattice.chronicle.base.OK
import com.openlattice.chronicle.study.StudyApi
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

public interface WebhookApi {
    public companion object {
        public const val WEBHOOKS_PATH: String = "/webhooks"
        public const val WEBHOOK_ID: String = "webhookId"
        public const val WEBHOOK_ID_PATH: String = "/{$WEBHOOK_ID}"
        public const val DELIVERIES_PATH: String = "/deliveries"
        public const val TEST_PATH: String = "/test"
    }

    @POST(StudyApi.BASE + StudyApi.STUDY_ID_PATH + WEBHOOKS_PATH)
    public fun createWebhook(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Body request: WebhookCreateRequest
    ): WebhookRegistration

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + WEBHOOKS_PATH)
    public fun listWebhooks(@Path(StudyApi.STUDY_ID) studyId: UUID): List<WebhookRegistration>

    @DELETE(StudyApi.BASE + StudyApi.STUDY_ID_PATH + WEBHOOKS_PATH + WEBHOOK_ID_PATH)
    public fun deleteWebhook(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(WEBHOOK_ID) webhookId: UUID
    ): OK

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + WEBHOOKS_PATH + WEBHOOK_ID_PATH + DELIVERIES_PATH)
    public fun getDeliveries(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(WEBHOOK_ID) webhookId: UUID
    ): List<WebhookDeliveryInfo>

    @POST(StudyApi.BASE + StudyApi.STUDY_ID_PATH + WEBHOOKS_PATH + WEBHOOK_ID_PATH + TEST_PATH)
    public fun testWebhook(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(WEBHOOK_ID) webhookId: UUID
    ): OK
}
