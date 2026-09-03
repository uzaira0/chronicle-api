package com.openlattice.chronicle.survey

import java.util.*

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 */
public data class DeviceUsage(
    val totalTime: Double,
    val usageByPackage: Map<String, Double>,
    val categoryByPackage: Map<String, String>,
    val users: List<String> = listOf()
)
