package com.schoolbus.driver

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.schoolbus.common.models.Trip
import com.schoolbus.common.models.TripStatus
import com.schoolbus.driver.databinding.ActivityTripBinding

class TripActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTripBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var currentTripId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnStartTrip.setOnClickListener {
            startTrip()
        }

        binding.btnEndTrip.setOnClickListener {
            endTrip()
        }
    }

    private fun startTrip() {
        val userId = auth.currentUser?.uid ?: return
        
        val trip = Trip(
            id = db.collection("trips").document().id,
            driverId = userId,
            startTime = System.currentTimeMillis(),
            status = TripStatus.IN_PROGRESS
        )

        db.collection("trips").document(trip.id).set(trip)
            .addOnSuccessListener {
                currentTripId = trip.id
                startService(Intent(this, LocationTrackingService::class.java))
                Toast.makeText(this, "Trip started", Toast.LENGTH_SHORT).show()
                binding.btnStartTrip.isEnabled = false
                binding.btnEndTrip.isEnabled = true
            }
    }

    private fun endTrip() {
        currentTripId?.let { tripId ->
            db.collection("trips").document(tripId)
                .update("status", TripStatus.COMPLETED.name, "endTime", System.currentTimeMillis())
                .addOnSuccessListener {
                    stopService(Intent(this, LocationTrackingService::class.java))
                    Toast.makeText(this, "Trip ended", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }
}
