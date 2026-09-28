package com.openlattice.chronicle.study

/** Keyset-paginated full history of retained upload diagnostics and quality alerts. */
public data class AndroidDiagnosticsPage(
    val items: List<AndroidDiagnosticsHistoryRow>,
    val nextCursor: String?,
)
