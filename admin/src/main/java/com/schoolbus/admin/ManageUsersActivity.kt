package com.schoolbus.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.schoolbus.admin.databinding.ActivityManageUsersBinding
import com.schoolbus.common.models.User

class ManageUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageUsersBinding
    private val db = FirebaseFirestore.getInstance()
    private val usersList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadUsers()
    }

    private fun setupRecyclerView() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
    }

    private fun loadUsers() {
        db.collection("users").get()
            .addOnSuccessListener { documents ->
                usersList.clear()
                for (doc in documents) {
                    val user = doc.toObject(User::class.java)
                    usersList.add(user)
                }
                Toast.makeText(this, "Loaded ${usersList.size} users", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load users", Toast.LENGTH_SHORT).show()
            }
    }
}
