package com.example.veeresh.parsegeo;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.List;
import java.util.Locale;


public class LocationManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final int updateInMiliseconds = 300;
    Geocoder geocoder;
    private boolean recievedUpdateOnce = false;



    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest.Builder builder;
    private Location presentLocation;
   // private List<Address> presentAddress;
    public MapsActivity main;




    public Location getPresentLocation() {
        return presentLocation;
    }

    public LocationManager(MapsActivity main) {
        this.main = main;
        fetchLocation();
    }

    public void setMainActivity(MapsActivity main) {
        this.main = main;
    }


    public void fetchLocation() {


        geocoder = new Geocoder(main.getApplicationContext(), Locale.getDefault());
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(main.getApplicationContext())
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.reconnect();

        }

    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(updateInMiliseconds); // Update location
        presentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        if (!isGpsActivated())
        {
            enableAutoGps();
        }
        recievedUpdateOnce = false;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mGoogleApiClient.connect();
    }

    public void removeLocationUpdateListner() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    @Override
    public void onLocationChanged(Location location)
    {

        if (!recievedUpdateOnce) {
            presentLocation = location;

            if (presentLocation != null) {
                recievedUpdateOnce = true;
                removeLocationUpdateListner();
                main.setUserLocation(location);

            }
        }
    }





    public void enableAutoGps() {

        builder.setAlwaysShow(true); //
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(main, 1000);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }



    public boolean isGpsActivated()
    {
        final android.location.LocationManager manager = (android.location.LocationManager) main.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
    }
}