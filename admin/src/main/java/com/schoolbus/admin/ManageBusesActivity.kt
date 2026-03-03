package com.schoolbus.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.schoolbus.admin.databinding.ActivityManageBusesBinding
import com.schoolbus.common.models.Bus

class ManageBusesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageBusesBinding
    private val db = FirebaseFirestore.getInstance()
    private val busesList = mutableListOf<Bus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageBusesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadBuses()
    }

    private fun setupRecyclerView() {
        binding.rvBuses.layoutManager = LinearLayoutManager(this)
    }

    private fun loadBuses() {
        db.collection("buses").get()
            .addOnSuccessListener { documents ->
                busesList.clear()
                for (doc in documents) {
                    val bus = doc.toObject(Bus::class.java)
                    busesList.add(bus)
                }
                Toast.makeText(this, "Loaded ${busesList.size} buses", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load buses", Toast.LENGTH_SHORT).show()
            }
    }
}
