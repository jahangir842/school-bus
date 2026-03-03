package com.schoolbus.driver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LocationTrackingService : Service() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupLocationCallback()
        startLocationUpdates()
    }

    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    updateLocationInFirebase(location.latitude, location.longitude)
                }
            }
        }
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 10000
        ).build()

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun updateLocationInFirebase(lat: Double, lng: Double) {
        val userId = auth.currentUser?.uid ?: return
        
        db.collection("buses")
            .whereEqualTo("driverId", userId)
            .get()
            .addOnSuccessListener { documents ->
                documents.firstOrNull()?.let { doc ->
                    db.collection("buses").document(doc.id)
                        .update(mapOf(
                            "currentLat" to lat,
                            "currentLng" to lng,
                            "lastUpdated" to System.currentTimeMillis()
                        ))
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
