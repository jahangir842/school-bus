package com.schoolbus.parent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.schoolbus.parent.databinding.ActivityTrackBusBinding

class TrackBusActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityTrackBusBinding
    private lateinit var googleMap: GoogleMap
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackBusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        trackChildBus()
    }

    private fun trackChildBus() {
        val userId = auth.currentUser?.uid ?: return
        
        db.collection("students")
            .whereEqualTo("parentId", userId)
            .get()
            .addOnSuccessListener { students ->
                val busId = students.firstOrNull()?.getString("busId") ?: return@addOnSuccessListener
                
                db.collection("buses").document(busId)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) return@addSnapshotListener
                        
                        snapshot?.let { doc ->
                            val lat = doc.getDouble("currentLat") ?: 0.0
                            val lng = doc.getDouble("currentLng") ?: 0.0
                            val busNumber = doc.getString("busNumber") ?: ""
                            
                            googleMap.clear()
                            val position = LatLng(lat, lng)
                            googleMap.addMarker(MarkerOptions().position(position).title("Bus $busNumber"))
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
                        }
                    }
            }
    }
}
