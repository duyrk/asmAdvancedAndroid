package com.example.asmadvancedandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asmadvancedandroid.fragments.MapsFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText edtAddress;
    private FusedLocationProviderClient fusedLocationClient;
    private FloatingActionButton fabCurrentLocation;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        edtAddress = findViewById(R.id.edtFindMap);
        spinner = findViewById(R.id.spn_5coso);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.coso_fpt_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        fabCurrentLocation = findViewById(R.id.fabCurrentLocation);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fabCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getCurrent();
            }
        });
    }

    public void onShowMap(View view) {
        String address = edtAddress.getText().toString();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.showMaps, MapsFragment.newInstance(address))
                .commit();
    }

    public void getCurrent() {
        Boolean isPermissionAllowed = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (isPermissionAllowed) {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                             double[] latLn = new double[2];
                             latLn[0] =  location.getLatitude();
                             latLn[1] = location.getLongitude();
                             getSupportFragmentManager()
                                     .beginTransaction()
                                     .replace(R.id.showMaps,MapsFragment.newInstance1(latLn,1))
                                     .commit();
                            }
                        }
                    });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Boolean isPermissionAllowed = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                    if (isPermissionAllowed) {

                        fusedLocationClient.getLastLocation()
                                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        // Got last known location. In some rare situations this can be null.
                                        if (location != null) {
                                            // Logic to handle location object
                                            double[] latLn = new double[2];
                                            latLn[0] =  location.getLatitude();
                                            latLn[1] = location.getLongitude();
                                            getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.showMaps,MapsFragment.newInstance1(latLn,1))
                                                    .commit();
                                        }
                                    }
                                });
                    }


                    break;
                }
            }
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:{

                break;
            }
            case 1 :{

                double[] latLn = new double[2];
                latLn[0] =  10.853113630800816;
                latLn[1] = 106.62952853516792;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.showMaps,MapsFragment.newInstance1(latLn,1))
                        .commit();
                break;
            }
            case 2 :{
                double[] latLn = new double[2];
                latLn[0] =  16.075998146443037;
                latLn[1] = 108.16995326107367;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.showMaps,MapsFragment.newInstance1(latLn,1))
                        .commit();
                break;
            }
            case 3 :{
                double[] latLn = new double[2];
                latLn[0] =  21.038308031957428;
                latLn[1] = 105.7468085544932;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.showMaps,MapsFragment.newInstance1(latLn,1))
                        .commit();
                break;
            }
            case 4 :{
                double[] latLn = new double[2];
                latLn[0] =  12.710003506099222;
                latLn[1] = 108.07507285466151;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.showMaps,MapsFragment.newInstance1(latLn,1))
                        .commit();
                break;
            }
            case 5 :{
                double[] latLn = new double[2];
                latLn[0] =  10.028869323786376;
                latLn[1] = 105.7575964520448;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.showMaps,MapsFragment.newInstance1(latLn,1))
                        .commit();
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}