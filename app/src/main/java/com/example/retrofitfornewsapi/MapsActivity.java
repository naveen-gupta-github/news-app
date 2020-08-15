package com.example.retrofitfornewsapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener
{

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 11;
    private GoogleMap mMap;
    private static final String TAG = "MapsActivity";

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if(mapFragment != null)
       {
           mapFragment.getMapAsync(this);
       }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        getLastLocation();


    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);

        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng lastLocation = new LatLng(location.getLatitude(),
                                    location.getLongitude());

                            mMap.addMarker(new MarkerOptions().position(lastLocation));
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(lastLocation, 4);
                            mMap.animateCamera(cameraUpdate);

                        }
                    }
                });


    }



    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ((requestCode == REQUEST_CODE_LOCATION_PERMISSION) && (grantResults.length > 0)) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getLastLocation();
            } else {

                Toast toast = new Toast(this);
                @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.custom_toast, null);
                toast.setView(view);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 200);
                toast.show();
            }
        }




    }

    @Override
    public void onMapLongClick(LatLng latLng)
    {

        final Address address = getCountryName(latLng.latitude,latLng.longitude);
        Log.i(TAG, "onMapLongClick: "+address.getCountryName());

        showAlertDialogue(address);
    }


    public Address getCountryName(double latitude, double longitude)
    {
        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        List<Address> addresses;
        //Address ad=null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0);
            }else
                return null;
        } catch (IOException e) {
            return null;
        }

    }

    private void showNews(String countryCode,  String CountryName)
    {
        Intent newsIntent  = new Intent(MapsActivity.this, MainActivity.class);

        newsIntent.putExtra("COUNTRY", countryCode);
        newsIntent.putExtra("CountryName", CountryName);
        Log.d(TAG, "showNews: "+CountryName);
        startActivity(newsIntent);
    }

    @SuppressLint("SetTextI18n")
    public void showAlertDialogue(final Address address)
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.custom_alert_dialogue, null);

        Button yesButton = view.findViewById(R.id.yesButton);
        Button noButton = view.findViewById(R.id.noButton);
        TextView alertTextView = view.findViewById(R.id.alertTextView);
        alertTextView.setText("Do You Want Top Headlines For "
                +address.getCountryName().toUpperCase()+" ?");

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showNews(address.getCountryCode(), address.getCountryName());
                alertDialog.dismiss();

            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

            }
        });


        alertDialog.show();

    }

}