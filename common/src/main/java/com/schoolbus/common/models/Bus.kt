package com.schoolbus.common.models

data class Bus(
    val id: String = "",
    val busNumber: String = "",
    val capacity: Int = 0,
    val driverId: String = "",
    val routeId: String = "",
    val isActive: Boolean = true,
    val currentLat: Double = 0.0,
    val currentLng: Double = 0.0,
    val lastUpdated: Long = System.currentTimeMillis()
)
