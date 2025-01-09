package com.example.hw1_space.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hw1_space.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.hw1_space.utilities.SharedPreferencesManager

class MapFragment : Fragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val topScores = SharedPreferencesManager.getInstance().getTopScores()

        topScores.forEach { scoreData ->
            val position = LatLng(scoreData.lat, scoreData.lng)
            val marker = map.addMarker(MarkerOptions()
                .position(position)
                .title("Score: ${scoreData.score}"))
        }

        topScores.firstOrNull()?.let { firstScore ->
            val position = LatLng(firstScore.lat, firstScore.lng)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 12f))
        }
    }

    fun focusOnLocation(lat: Double, lon: Double) {
        val location = LatLng(lat, lon)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }
}