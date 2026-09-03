package com.openlattice.chronicle.notifications

import com.openlattice.chronicle.base.OK
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

/**
 * @author Todd Bergman <todd@openlattice.com>
 */

public interface NotificationApi {
    public companion object {
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/v3/notification"
        public const val BASE: String = SERVICE + CONTROLLER

        public const val ORGANIZATION_ID: String = "organizationId"
        public const val STUDY_ID: String = "studyId"
        public const val PRINCIPAL_ID: String = "principalId"
        public const val PARTICIPANT_ID: String = "participantId"
        public const val DATE: String = "date"
        public const val SETTINGS: String = "settings"
        public const val NOTIFICATION_TYPE: String = "notificationType"
        public const val PHONE_NUMBER: String = "phoneNumber"

        public const val PHONE_NUMBERS_PATH: String = "/phone-numbers"
        public const val NOTIFICATIONS_PATH: String = "/notifications"
        public const val VERIFICATION_PATH: String = "/verify"
        public const val STUDY_ID_PATH: String = "/{$STUDY_ID}"
        public const val PARTICIPANT_ID_PATH: String = "/{${PARTICIPANT_ID}}"
        public const val PRINCIPAL_ID_PATH: String = "/principal/{$PRINCIPAL_ID}"
        public const val NOTIFICATION_TYPE_PATH: String = "$NOTIFICATIONS_PATH/{$NOTIFICATION_TYPE}"
        public const val PHONE_NUMBER_PATH: String = "$PHONE_NUMBERS_PATH/{$PHONE_NUMBER}"


        public const val MESSAGE_ID: String = "MessageSid"
        public const val MESSAGE_STATUS: String = "MessageStatus"
        public const val STATUS_PATH: String = "/status"
    }

    /**
     * Gets a researchers currently configured phone number.
     *
     * @param principalId The principal for which to the phone number.
     * @return The principals currently configured phone numbers.
     */
    @GET(BASE + STUDY_ID_PATH + PRINCIPAL_ID_PATH + PHONE_NUMBERS_PATH)
    public fun getResearcherPhoneNumber(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PRINCIPAL_ID) principalId: String,
    ): String


    @PUT(BASE + STUDY_ID_PATH + PRINCIPAL_ID_PATH + PHONE_NUMBER_PATH)
    public fun setResearcherPhoneNumber(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PRINCIPAL_ID) principalId: String,
        @Path(PHONE_NUMBER) phoneNumber: String,
    )

    @POST(BASE + STUDY_ID_PATH + PRINCIPAL_ID_PATH + PHONE_NUMBER_PATH + VERIFICATION_PATH)
    public fun verifyResearcherPhoneNumber(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PRINCIPAL_ID) principalId: String,
        @Path(PHONE_NUMBER) phoneNumber: String,
        @Body confirmationCode: String,
    )

    @GET(BASE + STUDY_ID_PATH + PRINCIPAL_ID_PATH + PHONE_NUMBER_PATH + VERIFICATION_PATH)
    public fun isResearcherPhoneNumberVerified(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PRINCIPAL_ID) principalId: String,
        @Path(PHONE_NUMBER) phoneNumber: String,
    ): Boolean

    @GET(BASE + STUDY_ID_PATH + PRINCIPAL_ID_PATH + NOTIFICATIONS_PATH)
    public fun getResearcherNotificationSettings(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PRINCIPAL_ID) principalId: String,
    ): Map<NotificationType, Set<DeliveryType>>

    @PUT(BASE + STUDY_ID_PATH + PRINCIPAL_ID_PATH + NOTIFICATIONS_PATH)
    public fun setResearcherNotificationSettings(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PRINCIPAL_ID) principalId: String,
        @Body settings: Map<NotificationType, Set<DeliveryType>>,
    ): OK

    @GET(BASE + STUDY_ID_PATH + PRINCIPAL_ID_PATH + NOTIFICATION_TYPE_PATH)
    public fun getResearcherNotificationSetting(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PRINCIPAL_ID) principalId: String,
        @Path(NOTIFICATION_TYPE) notificationType: NotificationType,
    ): Set<DeliveryType>

    @PUT(BASE + STUDY_ID_PATH + PRINCIPAL_ID_PATH + NOTIFICATION_TYPE_PATH)
    public fun setResearcherNotificationSettings(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PRINCIPAL_ID) principalId: String,
        @Path(NOTIFICATION_TYPE) notificationType: NotificationType,
        @Body deliveryTypes: Set<DeliveryType>,
    ): OK

    /**
     * Send Message to participant.
     *
     * @param studyId - The id of the study.
     * @param participantNotificationList - a list of notification details
     */
    @POST(BASE + STUDY_ID_PATH)
    public fun sendNotifications(
        @Path(STUDY_ID) studyId: UUID,
        @Body participantNotificationList: List<ParticipantNotification>,
    ): OK

    /**
     * Update staus for messages sent to partipants.
     * @param messageId - String Identifier (SID) - a unique key that is used to identify specific resources.
     * @param messageStatus - Finalized Message Delivery Status
     */
    @POST(BASE + STATUS_PATH)
    public fun updateNotificationStatus(
        @Query(MESSAGE_ID) messageId: String,
        @Query(MESSAGE_STATUS) messageStatus: String,
    ): OK

}
