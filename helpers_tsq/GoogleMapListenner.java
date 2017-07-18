package com.app.helpers;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by muhamamdtouseeq on 4/11/2017.
 */

public abstract class GoogleMapListenner implements OnMapReadyCallback {
    @Override
    public void onMapReady(GoogleMap googleMap) {
       LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public abstract  void showLocation(GoogleMap googleMap);
}
