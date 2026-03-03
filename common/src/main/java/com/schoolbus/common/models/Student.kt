package com.schoolbus.common.models

data class Student(
    val id: String = "",
    val name: String = "",
    val parentId: String = "",
    val busId: String = "",
    val pickupPointId: String = "",
    val dropoffPointId: String = "",
    val qrCode: String = "",
    val grade: String = ""
)
