package com.schoolbus.driver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.schoolbus.driver.databinding.ActivityDriverDashboardBinding

class DriverDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDriverDashboardBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadDriverInfo()
        setupClickListeners()
    }

    private fun loadDriverInfo() {
        val userId = auth.currentUser?.uid ?: return
        
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                binding.tvDriverName.text = "Welcome, ${document.getString("name")}"
            }
    }

    private fun setupClickListeners() {
        binding.btnStartTrip.setOnClickListener {
            startActivity(Intent(this, TripActivity::class.java))
        }

        binding.btnMarkAttendance.setOnClickListener {
            startActivity(Intent(this, AttendanceActivity::class.java))
        }
    }
}
