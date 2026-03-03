package com.schoolbus.common.utils

object Constants {
    // Firebase Collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_BUSES = "buses"
    const val COLLECTION_STUDENTS = "students"
    const val COLLECTION_ROUTES = "routes"
    const val COLLECTION_TRIPS = "trips"
    
    // Notification Channels
    const val CHANNEL_ID_LOCATION = "location_tracking"
    const val CHANNEL_ID_ALERTS = "bus_alerts"
    
    // Location Update Interval
    const val LOCATION_UPDATE_INTERVAL = 10000L // 10 seconds
    const val LOCATION_FASTEST_INTERVAL = 5000L // 5 seconds
}
