package com.example.partie2gsg;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng cegep = new LatLng(46.8300868, -71.2277016);
        mMap.addMarker(new MarkerOptions().position(cegep).title("Marker in Cégep Limoilou"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cegep));

        mMap.setMapType ( GoogleMap . MAP_TYPE_NORMAL );
        mMap.getUiSettings (). setZoomControlsEnabled ( true );
        mMap.getUiSettings (). setZoomGesturesEnabled ( true );
        mMap.getUiSettings (). setCompassEnabled (true);
    }

}