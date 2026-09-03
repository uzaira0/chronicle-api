/*
 * Copyright (C) 2024. Chronicle.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openlattice.chronicle.audit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.util.UUID

/**
 * Represents a HIPAA-compliant audit log entry that captures all details about
 * system access, data modifications, and security events.
 *
 * This entry is designed to be written to both database (for querying and retention)
 * and log files (for SIEM integration).
 *
 * @property id Unique identifier for this audit event
 * @property timestamp When the event occurred (UTC)
 * @property userId The authenticated user's ID (nullable for unauthenticated requests)
 * @property userRole The role of the user performing the action
 * @property ipAddress The IP address of the request originator
 * @property userAgent The User-Agent header from the request
 * @property action The type of action being performed
 * @property resourceType The type of resource being accessed (Study, Participant, etc.)
 * @property resourceId The specific resource ID being accessed
 * @property studyId The study context for this action (if applicable)
 * @property organizationId The organization context for this action (if applicable)
 * @property success Whether the operation succeeded
 * @property errorMessage Error details if the operation failed
 * @property accessedPHI Whether Protected Health Information was accessed
 * @property phiFields Specific PHI field names that were accessed
 * @property requestPath The HTTP request path
 * @property requestMethod The HTTP method (GET, POST, etc.)
 * @property responseCode The HTTP response status code
 * @property durationMs How long the operation took in milliseconds
 * @property additionalData Any additional context-specific data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public data class AuditLogEntry(
    @param:JsonProperty("id")
    val id: UUID = UUID.randomUUID(),

    @param:JsonProperty("timestamp")
    val timestamp: Instant = Instant.now(),

    @param:JsonProperty("userId")
    val userId: UUID? = null,

    @param:JsonProperty("userRole")
    val userRole: String? = null,

    @param:JsonProperty("ipAddress")
    val ipAddress: String,

    @param:JsonProperty("userAgent")
    val userAgent: String? = null,

    @param:JsonProperty("action")
    val action: AuditAction,

    @param:JsonProperty("resourceType")
    val resourceType: String,

    @param:JsonProperty("resourceId")
    val resourceId: UUID? = null,

    @param:JsonProperty("studyId")
    val studyId: UUID? = null,

    @param:JsonProperty("organizationId")
    val organizationId: UUID? = null,

    @param:JsonProperty("success")
    val success: Boolean,

    @param:JsonProperty("errorMessage")
    val errorMessage: String? = null,

    @param:JsonProperty("accessedPHI")
    val accessedPHI: Boolean = false,

    @param:JsonProperty("phiFields")
    val phiFields: List<String>? = null,

    @param:JsonProperty("requestPath")
    val requestPath: String? = null,

    @param:JsonProperty("requestMethod")
    val requestMethod: String? = null,

    @param:JsonProperty("responseCode")
    val responseCode: Int? = null,

    @param:JsonProperty("durationMs")
    val durationMs: Long? = null,

    @param:JsonProperty("additionalData")
    val additionalData: Map<String, Any>? = null
) {
    public companion object {
        // PHI field identifiers that must be tracked when accessed
        public val PHI_FIELDS: Set<String> = setOf(
            "firstName",
            "lastName",
            "dateOfBirth",
            "email",
            "phoneNumber",
            "address",
            "socialSecurityNumber",
            "medicalRecordNumber",
            "healthPlanBeneficiaryNumber",
            "accountNumber",
            "certificateLicenseNumber",
            "vehicleIdentifiers",
            "deviceIdentifiers",
            "webUniversalResourceLocators",
            "ipAddress",
            "biometricIdentifiers",
            "fullFacePhotographs",
            "otherUniqueIdentifyingNumber"
        )

        // Resource types that contain PHI
        public val PHI_RESOURCE_TYPES: Set<String> = setOf(
            "Participant",
            "Candidate",
            "TimeUseDiary",
            "SensorData",
            "UsageData",
            "AppUsageSurvey",
            "Questionnaire"
        )
    }

    /**
     * Returns true if this entry represents a failed operation
     */
    public fun isFailed(): Boolean = !success

    /**
     * Returns true if this entry involves PHI access
     */
    public fun involvesPHI(): Boolean = accessedPHI || (phiFields?.isNotEmpty() == true)

    /**
     * Returns true if this is a security-relevant event
     */
    public fun isSecurityEvent(): Boolean = action in setOf(
        AuditAction.LOGIN,
        AuditAction.LOGOUT,
        AuditAction.LOGIN_FAILED,
        AuditAction.UNAUTHORIZED_ACCESS,
        AuditAction.ACCESS_DENIED,
        AuditAction.PERMISSION_CHANGE,
        AuditAction.PERMISSION_GRANT,
        AuditAction.PERMISSION_REVOKE,
        AuditAction.SUSPICIOUS_ACTIVITY
    )

    /**
     * Returns true if this is a data modification event
     */
    public fun isDataModification(): Boolean = action in setOf(
        AuditAction.CREATE,
        AuditAction.UPDATE,
        AuditAction.DELETE,
        AuditAction.ARCHIVE,
        AuditAction.RESTORE,
        AuditAction.DATA_DELETION
    )
}

/**
 * Builder class for creating AuditLogEntry instances with a fluent API.
 */
public class AuditLogEntryBuilder {
    private var id: UUID = UUID.randomUUID()
    private var timestamp: Instant = Instant.now()
    private var userId: UUID? = null
    private var userRole: String? = null
    private var ipAddress: String? = null
    private var userAgent: String? = null
    private var action: AuditAction? = null
    private var resourceType: String? = null
    private var resourceId: UUID? = null
    private var studyId: UUID? = null
    private var organizationId: UUID? = null
    private var success: Boolean? = null
    private var errorMessage: String? = null
    private var accessedPHI: Boolean = false
    private var phiFields: MutableList<String> = mutableListOf()
    private var requestPath: String? = null
    private var requestMethod: String? = null
    private var responseCode: Int? = null
    private var durationMs: Long? = null
    private var additionalData: MutableMap<String, Any> = mutableMapOf()

    public fun id(id: UUID): AuditLogEntryBuilder = apply { this.id = id }
    public fun timestamp(timestamp: Instant): AuditLogEntryBuilder = apply { this.timestamp = timestamp }
    public fun userId(userId: UUID?): AuditLogEntryBuilder = apply { this.userId = userId }
    public fun userRole(userRole: String?): AuditLogEntryBuilder = apply { this.userRole = userRole }
    public fun ipAddress(ipAddress: String?): AuditLogEntryBuilder = apply { this.ipAddress = ipAddress }
    public fun userAgent(userAgent: String?): AuditLogEntryBuilder = apply { this.userAgent = userAgent }
    public fun action(action: AuditAction): AuditLogEntryBuilder = apply { this.action = action }
    public fun resourceType(resourceType: String): AuditLogEntryBuilder = apply { this.resourceType = resourceType }
    public fun resourceId(resourceId: UUID?): AuditLogEntryBuilder = apply { this.resourceId = resourceId }
    public fun studyId(studyId: UUID?): AuditLogEntryBuilder = apply { this.studyId = studyId }
    public fun organizationId(organizationId: UUID?): AuditLogEntryBuilder = apply { this.organizationId = organizationId }
    public fun success(success: Boolean): AuditLogEntryBuilder = apply { this.success = success }
    public fun failed(errorMessage: String): AuditLogEntryBuilder = apply { this.success = false; this.errorMessage = errorMessage }
    public fun errorMessage(errorMessage: String?): AuditLogEntryBuilder = apply { this.errorMessage = errorMessage }
    public fun accessedPHI(accessedPHI: Boolean): AuditLogEntryBuilder = apply { this.accessedPHI = accessedPHI }
    public fun phiField(field: String): AuditLogEntryBuilder = apply { this.phiFields.add(field); this.accessedPHI = true }
    public fun phiFields(fields: List<String>): AuditLogEntryBuilder = apply {
        this.phiFields.addAll(fields)
        if (fields.isNotEmpty()) this.accessedPHI = true
    }
    public fun requestPath(path: String?): AuditLogEntryBuilder = apply { this.requestPath = path }
    public fun requestMethod(method: String?): AuditLogEntryBuilder = apply { this.requestMethod = method }
    public fun responseCode(code: Int?): AuditLogEntryBuilder = apply { this.responseCode = code }
    public fun durationMs(duration: Long?): AuditLogEntryBuilder = apply { this.durationMs = duration }
    public fun additionalData(key: String, value: Any): AuditLogEntryBuilder = apply { this.additionalData[key] = value }
    public fun additionalData(data: Map<String, Any>): AuditLogEntryBuilder = apply { this.additionalData.putAll(data) }

    public fun build(): AuditLogEntry {
        requireNotNull(ipAddress) { "ipAddress must be set explicitly for HIPAA audit logging" }
        requireNotNull(action) { "action must be set explicitly for HIPAA audit logging" }
        requireNotNull(resourceType) { "resourceType must be set explicitly for HIPAA audit logging" }
        requireNotNull(success) { "success must be set explicitly for HIPAA audit logging" }
        return AuditLogEntry(
        id = id,
        timestamp = timestamp,
        userId = userId,
        userRole = userRole,
        ipAddress = ipAddress!!,
        userAgent = userAgent,
        action = action!!,
        resourceType = resourceType!!,
        resourceId = resourceId,
        studyId = studyId,
        organizationId = organizationId,
        success = success!!,
        errorMessage = errorMessage,
        accessedPHI = accessedPHI,
        phiFields = if (phiFields.isNotEmpty()) phiFields.toList() else null,
        requestPath = requestPath,
        requestMethod = requestMethod,
        responseCode = responseCode,
        durationMs = durationMs,
        additionalData = if (additionalData.isNotEmpty()) additionalData.toMap() else null
    )
    }
}

/**
 * Extension function to create an AuditLogEntryBuilder
 */
public fun auditLogEntry(block: AuditLogEntryBuilder.() -> Unit): AuditLogEntry =
    AuditLogEntryBuilder().apply(block).build()
