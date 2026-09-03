package com.openlattice.chronicle.enums

import com.openlattice.chronicle.export.ExportFormat
import com.openlattice.chronicle.export.ExportJobStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class ExportEnumsTest {

    // ===== ExportFormat (3 values) =====
    @Test fun testExportFormatCount() { assertEquals(3, ExportFormat.values().size) }
    @Test fun testExportFormatCsv() { assertEquals("CSV", ExportFormat.CSV.name) }
    @Test fun testExportFormatJson() { assertEquals("JSON", ExportFormat.JSON.name) }
    @Test fun testExportFormatExcel() { assertEquals("EXCEL", ExportFormat.EXCEL.name) }
    @Test fun testExportFormatOrdinalCsv() { assertEquals(0, ExportFormat.CSV.ordinal) }
    @Test fun testExportFormatOrdinalJson() { assertEquals(1, ExportFormat.JSON.ordinal) }
    @Test fun testExportFormatOrdinalExcel() { assertEquals(2, ExportFormat.EXCEL.ordinal) }
    @Test fun testExportFormatValueOfCsv() { assertEquals(ExportFormat.CSV, ExportFormat.valueOf("CSV")) }
    @Test fun testExportFormatValueOfJson() { assertEquals(ExportFormat.JSON, ExportFormat.valueOf("JSON")) }
    @Test fun testExportFormatValueOfExcel() { assertEquals(ExportFormat.EXCEL, ExportFormat.valueOf("EXCEL")) }
    @Test fun testExportFormatValueOfInvalid() {
        try {
            ExportFormat.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== ExportJobStatus (4 values) =====
    @Test fun testExportJobStatusCount() { assertEquals(4, ExportJobStatus.values().size) }
    @Test fun testExportJobStatusPending() { assertEquals("PENDING", ExportJobStatus.PENDING.name) }
    @Test fun testExportJobStatusRunning() { assertEquals("RUNNING", ExportJobStatus.RUNNING.name) }
    @Test fun testExportJobStatusCompleted() { assertEquals("COMPLETED", ExportJobStatus.COMPLETED.name) }
    @Test fun testExportJobStatusFailed() { assertEquals("FAILED", ExportJobStatus.FAILED.name) }
    @Test fun testExportJobStatusOrdinalPending() { assertEquals(0, ExportJobStatus.PENDING.ordinal) }
    @Test fun testExportJobStatusOrdinalRunning() { assertEquals(1, ExportJobStatus.RUNNING.ordinal) }
    @Test fun testExportJobStatusOrdinalCompleted() { assertEquals(2, ExportJobStatus.COMPLETED.ordinal) }
    @Test fun testExportJobStatusOrdinalFailed() { assertEquals(3, ExportJobStatus.FAILED.ordinal) }
    @Test fun testExportJobStatusValueOfPending() { assertEquals(ExportJobStatus.PENDING, ExportJobStatus.valueOf("PENDING")) }
    @Test fun testExportJobStatusValueOfInvalid() {
        try {
            ExportJobStatus.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
}
