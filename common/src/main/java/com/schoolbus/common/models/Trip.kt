package com.schoolbus.common.models

data class Trip(
    val id: String = "",
    val busId: String = "",
    val driverId: String = "",
    val routeId: String = "",
    val startTime: Long = 0,
    val endTime: Long = 0,
    val status: TripStatus = TripStatus.SCHEDULED,
    val attendanceRecords: List<AttendanceRecord> = emptyList()
)

enum class TripStatus {
    SCHEDULED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}

data class AttendanceRecord(
    val studentId: String = "",
    val pickupTime: Long = 0,
    val dropoffTime: Long = 0,
    val status: AttendanceStatus = AttendanceStatus.ABSENT
)

enum class AttendanceStatus {
    PRESENT,
    ABSENT,
    PICKED_UP,
    DROPPED_OFF
}
