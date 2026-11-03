package com.openlattice.chronicle.survey

import java.util.*

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 */
public data class DeviceUsage(
    /** Total device usage in seconds. */
    val totalTime: Double,
    /** Device usage in seconds, keyed by package name. */
    val usageByPackage: Map<String, Double>,
    val categoryByPackage: Map<String, String>,
    val users: List<String> = listOf()
)
