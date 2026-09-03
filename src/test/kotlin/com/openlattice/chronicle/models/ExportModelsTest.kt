package com.openlattice.chronicle.models

import com.openlattice.chronicle.export.ExportFormat
import com.openlattice.chronicle.export.ExportJobInfo
import com.openlattice.chronicle.export.ExportJobStatus
import com.openlattice.chronicle.export.ExportRequest
import com.openlattice.chronicle.study.ParticipantDataType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.OffsetDateTime
import java.util.UUID

class ExportModelsTest {

    // ===== ExportRequest =====
    @Test fun testExportRequestDefaultConstructor() {
        val r = ExportRequest()
        assertNotNull(r)
    }
    @Test fun testExportRequestDefaultDataTypes() { assertTrue(ExportRequest().dataTypes.isEmpty()) }
    @Test fun testExportRequestDefaultParticipantIds() { assertTrue(ExportRequest().participantIds.isEmpty()) }
    @Test fun testExportRequestDefaultStartDate() { assertNull(ExportRequest().startDate) }
    @Test fun testExportRequestDefaultEndDate() { assertNull(ExportRequest().endDate) }
    @Test fun testExportRequestDefaultFormat() { assertEquals(ExportFormat.CSV, ExportRequest().format) }

    @Test fun testExportRequestCustomDataTypes() {
        val r = ExportRequest(dataTypes = setOf(ParticipantDataType.UsageEvents, ParticipantDataType.Preprocessed))
        assertEquals(2, r.dataTypes.size)
        assertTrue(r.dataTypes.contains(ParticipantDataType.UsageEvents))
    }
    @Test fun testExportRequestCustomParticipantIds() {
        val r = ExportRequest(participantIds = setOf("p1", "p2"))
        assertEquals(2, r.participantIds.size)
    }
    @Test fun testExportRequestCustomFormat() {
        val r = ExportRequest(format = ExportFormat.JSON)
        assertEquals(ExportFormat.JSON, r.format)
    }
    @Test fun testExportRequestCustomDates() {
        val start = OffsetDateTime.now().minusDays(30)
        val end = OffsetDateTime.now()
        val r = ExportRequest(startDate = start, endDate = end)
        assertEquals(start, r.startDate)
        assertEquals(end, r.endDate)
    }
    @Test fun testExportRequestEquality() {
        val r1 = ExportRequest(format = ExportFormat.EXCEL)
        val r2 = ExportRequest(format = ExportFormat.EXCEL)
        assertEquals(r1, r2)
    }
    @Test fun testExportRequestInequality() {
        val r1 = ExportRequest(format = ExportFormat.CSV)
        val r2 = ExportRequest(format = ExportFormat.JSON)
        assertNotEquals(r1, r2)
    }
    @Test fun testExportRequestCopy() {
        val r = ExportRequest(format = ExportFormat.CSV)
        val c = r.copy(format = ExportFormat.EXCEL)
        assertEquals(ExportFormat.EXCEL, c.format)
    }
    @Test fun testExportRequestToStringNotNull() { assertNotNull(ExportRequest().toString()) }

    // ===== ExportJobInfo =====
    @Test fun testExportJobInfoConstruction() {
        val id = UUID.randomUUID()
        val studyId = UUID.randomUUID()
        val now = OffsetDateTime.now()
        val info = ExportJobInfo(
            exportId = id,
            studyId = studyId,
            status = ExportJobStatus.PENDING,
            format = ExportFormat.CSV,
            createdAt = now
        )
        assertEquals(id, info.exportId)
        assertEquals(studyId, info.studyId)
        assertEquals(ExportJobStatus.PENDING, info.status)
        assertEquals(ExportFormat.CSV, info.format)
        assertEquals(now, info.createdAt)
    }
    @Test fun testExportJobInfoDefaultCompletedAt() {
        val info = ExportJobInfo(UUID.randomUUID(), UUID.randomUUID(), ExportJobStatus.PENDING, ExportFormat.CSV, OffsetDateTime.now())
        assertNull(info.completedAt)
    }
    @Test fun testExportJobInfoDefaultDownloadToken() {
        val info = ExportJobInfo(UUID.randomUUID(), UUID.randomUUID(), ExportJobStatus.PENDING, ExportFormat.CSV, OffsetDateTime.now())
        assertNull(info.downloadToken)
    }
    @Test fun testExportJobInfoDefaultRowCount() {
        val info = ExportJobInfo(UUID.randomUUID(), UUID.randomUUID(), ExportJobStatus.PENDING, ExportFormat.CSV, OffsetDateTime.now())
        assertEquals(0L, info.rowCount)
    }
    @Test fun testExportJobInfoDefaultErrorMessage() {
        val info = ExportJobInfo(UUID.randomUUID(), UUID.randomUUID(), ExportJobStatus.PENDING, ExportFormat.CSV, OffsetDateTime.now())
        assertNull(info.errorMessage)
    }
    @Test fun testExportJobInfoDefaultFilePath() {
        val info = ExportJobInfo(UUID.randomUUID(), UUID.randomUUID(), ExportJobStatus.PENDING, ExportFormat.CSV, OffsetDateTime.now())
        assertNull(info.filePath)
    }
    @Test fun testExportJobInfoCustomRowCount() {
        val info = ExportJobInfo(
            UUID.randomUUID(), UUID.randomUUID(), ExportJobStatus.COMPLETED,
            ExportFormat.CSV, OffsetDateTime.now(), rowCount = 1000
        )
        assertEquals(1000L, info.rowCount)
    }
    @Test fun testExportJobInfoCustomErrorMessage() {
        val info = ExportJobInfo(
            UUID.randomUUID(), UUID.randomUUID(), ExportJobStatus.FAILED,
            ExportFormat.CSV, OffsetDateTime.now(), errorMessage = "out of memory"
        )
        assertEquals("out of memory", info.errorMessage)
    }
    @Test fun testExportJobInfoToStringNotNull() {
        val info = ExportJobInfo(UUID.randomUUID(), UUID.randomUUID(), ExportJobStatus.PENDING, ExportFormat.CSV, OffsetDateTime.now())
        assertNotNull(info.toString())
    }
}
