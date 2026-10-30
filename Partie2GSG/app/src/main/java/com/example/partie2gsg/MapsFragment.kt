package com.example.partie2gsg

import android.os.Bundle
import androidx.core.content.IntentCompat
import androidx.fragment.app.FragmentActivity
import com.example.partie2gsg.DataBase.SuccursaleDomino
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
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val s = IntentCompat.getSerializableExtra(intent, "succursale", SuccursaleDomino::class.java)

        val pos = if (s != null) LatLng(s.latitude, s.longitude)
        else LatLng(46.8300868, -71.2277016)
        val titre = s?.address ?: "Cégep Limoilou"

        mMap!!.addMarker(MarkerOptions().position(pos).title(titre))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15f))

        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap!!.uiSettings.isZoomControlsEnabled = true
        mMap!!.uiSettings.isZoomGesturesEnabled = true
        mMap!!.uiSettings.isCompassEnabled = true
    }
}