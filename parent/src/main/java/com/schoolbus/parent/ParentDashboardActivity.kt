package com.schoolbus.parent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.schoolbus.parent.databinding.ActivityParentDashboardBinding

class ParentDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParentDashboardBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadChildInfo()
        setupClickListeners()
    }

    private fun loadChildInfo() {
        val userId = auth.currentUser?.uid ?: return
        
        db.collection("students")
            .whereEqualTo("parentId", userId)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(this, "No child assigned", Toast.LENGTH_SHORT).show()
                } else {
                    val student = documents.first()
                    binding.tvChildName.text = student.getString("name")
                }
            }
    }

    private fun setupClickListeners() {
        binding.btnTrackBus.setOnClickListener {
            startActivity(Intent(this, TrackBusActivity::class.java))
        }

        binding.btnChildDetails.setOnClickListener {
            startActivity(Intent(this, ChildDetailsActivity::class.java))
        }
    }
}
