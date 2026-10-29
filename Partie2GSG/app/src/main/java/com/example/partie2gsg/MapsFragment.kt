package com.example.partie2gsg

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : FragmentActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_maps)
        val mapFragment = getSupportFragmentManager()
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val cegep = LatLng(46.8300868, -71.2277016)
        mMap!!.addMarker(MarkerOptions().position(cegep).title("Marker in Cégep Limoilou"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(cegep))

        mMap!!.setMapType(GoogleMap.MAP_TYPE_NORMAL)
        mMap!!.getUiSettings().setZoomControlsEnabled(true)
        mMap!!.getUiSettings().setZoomGesturesEnabled(true)
        mMap!!.getUiSettings().setCompassEnabled(true)
    }
}