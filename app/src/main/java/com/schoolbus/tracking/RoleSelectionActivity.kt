package com.schoolbus.tracking

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.schoolbus.common.models.UserRole
import com.schoolbus.tracking.databinding.ActivityRoleSelectionBinding

class RoleSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoleSelectionBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserRole()
    }

    private fun loadUserRole() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val role = document.getString("role")
                navigateToModule(role)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateToModule(role: String?) {
        val intent = when (role) {
            UserRole.ADMIN.name -> Intent(this, Class.forName("com.schoolbus.admin.AdminDashboardActivity"))
            UserRole.DRIVER.name -> Intent(this, Class.forName("com.schoolbus.driver.DriverDashboardActivity"))
            UserRole.PARENT.name -> Intent(this, Class.forName("com.schoolbus.parent.ParentDashboardActivity"))
            else -> {
                Toast.makeText(this, "Invalid user role", Toast.LENGTH_SHORT).show()
                return
            }
        }
        startActivity(intent)
        finish()
    }
}
