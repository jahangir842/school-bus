package com.schoolbus.driver

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import com.schoolbus.driver.databinding.ActivityAttendanceBinding

class AttendanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendanceBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScanQr.setOnClickListener {
            startQRScanner()
        }
    }

    private fun startQRScanner() {
        IntentIntegrator(this).apply {
            setPrompt("Scan student QR code")
            setBeepEnabled(true)
            initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                markAttendance(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun markAttendance(studentId: String) {
        db.collection("students").document(studentId).get()
            .addOnSuccessListener { document ->
                val studentName = document.getString("name")
                Toast.makeText(this, "Attendance marked for $studentName", Toast.LENGTH_SHORT).show()
            }
    }
}
