package com.schoolbus.common.models

data class Route(
    val id: String = "",
    val name: String = "",
    val pickupPoints: List<PickupPoint> = emptyList(),
    val startTime: String = "",
    val endTime: String = ""
)

data class PickupPoint(
    val id: String = "",
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val scheduledTime: String = "",
    val order: Int = 0
)
