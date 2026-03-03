package com.schoolbus.parent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.schoolbus.parent.databinding.ActivityChildDetailsBinding

class ChildDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChildDetailsBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadChildDetails()
    }

    private fun loadChildDetails() {
        val userId = auth.currentUser?.uid ?: return
        
        db.collection("students")
            .whereEqualTo("parentId", userId)
            .get()
            .addOnSuccessListener { documents ->
                documents.firstOrNull()?.let { doc ->
                    binding.tvName.text = "Name: ${doc.getString("name")}"
                    binding.tvGrade.text = "Grade: ${doc.getString("grade")}"
                    
                    val busId = doc.getString("busId") ?: return@addOnSuccessListener
                    loadBusDetails(busId)
                }
            }
    }

    private fun loadBusDetails(busId: String) {
        db.collection("buses").document(busId).get()
            .addOnSuccessListener { doc ->
                binding.tvBusNumber.text = "Bus: ${doc.getString("busNumber")}"
            }
    }
}
