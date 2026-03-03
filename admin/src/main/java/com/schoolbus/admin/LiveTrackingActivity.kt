package com.schoolbus.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.schoolbus.admin.databinding.ActivityLiveTrackingBinding
import com.schoolbus.common.models.Bus

class LiveTrackingActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityLiveTrackingBinding
    private lateinit var googleMap: GoogleMap
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        loadBusLocations()
    }

    private fun loadBusLocations() {
        db.collection("buses").whereEqualTo("isActive", true)
            .addSnapshotListener { snapshots, error ->
                if (error != null) return@addSnapshotListener
                
                googleMap.clear()
                snapshots?.documents?.forEach { doc ->
                    val bus = doc.toObject(Bus::class.java)
                    bus?.let {
                        val position = LatLng(it.currentLat, it.currentLng)
                        googleMap.addMarker(MarkerOptions().position(position).title(it.busNumber))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 12f))
                    }
                }
            }
    }
}
