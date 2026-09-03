package com.openlattice.chronicle.study

import com.openlattice.chronicle.collection.AndroidDataCollectionSetting
import java.net.URI
import java.time.OffsetDateTime
import java.util.UUID

/** Authoritative study disclosure returned before a one-time invitation is consumed. */
public data class EnrollmentManifest(
    val schemaVersion: Int = CURRENT_SCHEMA_VERSION,
    val serverOrigin: String,
    val studyId: UUID,
    val participantId: String,
    val studyTitle: String,
    val studyDescription: String,
    val participantPolicy: StudyParticipantPolicy,
    val collectionSettings: AndroidDataCollectionSetting,
    val settingsVersion: Int,
    val issuedAt: OffsetDateTime,
    val expiresAt: OffsetDateTime,
) {
    init {
        require(schemaVersion == CURRENT_SCHEMA_VERSION) { "Unsupported enrollment manifest schema: $schemaVersion" }
        require(participantId.isNotBlank()) { "participantId must not be blank" }
        require(studyTitle.isNotBlank()) { "studyTitle must not be blank" }
        require(settingsVersion > 0) { "settingsVersion must be positive" }
        require(settingsVersion == collectionSettings.settingsVersion) {
            "settingsVersion must match collectionSettings.settingsVersion"
        }
        require(expiresAt.isAfter(issuedAt)) { "Enrollment manifest must expire after it is issued" }
        requireRootHttpsOrigin(serverOrigin)
    }

    public companion object {
        public const val CURRENT_SCHEMA_VERSION: Int = 1

        private fun requireRootHttpsOrigin(value: String) {
            val uri = runCatching { URI(value) }.getOrNull()
            require(
                uri != null &&
                    uri.isAbsolute &&
                    uri.scheme.equals("https", ignoreCase = true) &&
                    !uri.host.isNullOrBlank() &&
                    uri.userInfo == null &&
                    (uri.path.isNullOrEmpty() || uri.path == "/") &&
                    uri.query == null &&
                    uri.fragment == null
            ) {
                "serverOrigin must be an HTTPS root origin without credentials, query, or fragment"
            }
        }
    }
}

/** Enrollment preview plus the digest the app must echo when it completes enrollment. */
public data class EnrollmentPreviewResponse(
    val manifest: EnrollmentManifest,
    val manifestDigest: String,
) {
    init {
        require(MANIFEST_DIGEST.matches(manifestDigest)) {
            "manifestDigest must be a lowercase SHA-256 hex digest"
        }
    }

    private companion object {
        val MANIFEST_DIGEST: Regex = Regex("^[0-9a-f]{64}$")
    }
}
