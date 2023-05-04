package com.example.asmadvancedandroid.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asmadvancedandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsFragment extends Fragment {
    private String address;
    private double[] currentLatLN;
    private int isClicked;
    public static MapsFragment newInstance(String address){
        MapsFragment fragment = new MapsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("address",address);
fragment.setArguments(bundle);
        return fragment;
    }
    public static MapsFragment newInstance1(double[] currentLatLn, int isClicked){
        MapsFragment fragment = new MapsFragment();
        Bundle bundle = new Bundle();
        bundle.putDoubleArray("currentLocation",currentLatLn);
        bundle.putInt("isClicked",isClicked);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            address = getArguments().getString("address");
            currentLatLN = getArguments().getDoubleArray("currentLocation");
            isClicked = getArguments().getInt("isClicked");
        }
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            try {
                //chuyen tu dia chi sang lat va longitude
                Geocoder geocoder = new Geocoder(getContext());
                List<Address> list = geocoder.getFromLocationName(address,3);
                Address adr = list.get(0);
                LatLng latLng = new LatLng(adr.getLatitude(), adr.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(latLng).title(address));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }catch (Exception e){
                Log.d(">>>>>>>>>>TAG","onMapReady: "+e.getMessage());
            }

            if(currentLatLN!=null){
                Log.d(">>>TAGG","onMapReady: Lat"+currentLatLN[0]+"Long"+currentLatLN[1]);
                LatLng latLng = new LatLng(currentLatLN[0], currentLatLN[1]);
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Your Current Location ! "));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}