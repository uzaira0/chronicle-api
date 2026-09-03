package com.openlattice.chronicle.organizations

import com.openlattice.chronicle.settings.AppComponent
import com.openlattice.chronicle.settings.AppUsageFrequency
import com.openlattice.chronicle.study.StudySetting
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public data class ChronicleDataCollectionSettings(
        @field:NotNull(message = "App usage frequency is required")
        val appUsageFrequency: AppUsageFrequency = AppUsageFrequency.DAILY
) : StudySetting

public data class OrganizationSettings(
        @field:Valid
        @field:NotNull(message = "Chronicle data collection settings are required")
        val chronicleDataCollection: ChronicleDataCollectionSettings = ChronicleDataCollectionSettings(),

        val appSettings: Map<AppComponent, Map<String, Any>> = mutableMapOf()
)
