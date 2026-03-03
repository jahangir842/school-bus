package com.schoolbus.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.schoolbus.admin.databinding.ActivityAdminDashboardBinding

class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.cardManageUsers.setOnClickListener {
            startActivity(Intent(this, ManageUsersActivity::class.java))
        }

        binding.cardManageBuses.setOnClickListener {
            startActivity(Intent(this, ManageBusesActivity::class.java))
        }

        binding.cardLiveTracking.setOnClickListener {
            startActivity(Intent(this, LiveTrackingActivity::class.java))
        }
    }
}
