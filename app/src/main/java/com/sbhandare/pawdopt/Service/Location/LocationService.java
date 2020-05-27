package com.sbhandare.pawdopt.Service.Location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.core.app.ActivityCompat;

import com.sbhandare.pawdopt.Model.GeoPoint;

public class LocationService {
    private Context context;
    private LocationManager locationManager;

    public LocationService(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public GeoPoint getCurrentLatLong() {
        if (ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return null;
        }
        Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location locationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        double lat;
        double lon;

        if(locationGps != null){
            lat = locationGps.getLatitude();
            lon = locationGps.getLongitude();
        }else if(locationNetwork != null){
            lat = locationNetwork.getLatitude();
            lon = locationNetwork.getLongitude();
        }else if(locationPassive != null){
            lat = locationPassive.getLatitude();
            lon = locationPassive.getLongitude();
        }else
            return null;
        return new GeoPoint(lat,lon);
    }

    private GeoPoint getLatLongFromAddress(com.sbhandare.pawdopt.Model.Address address){
        String addressStr = address.getStreet1()+", "+address.getStreet2()+", "+address.getCity()+", "+address.getState()+", "+address.getZipCode();
        Geocoder coder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList;
        GeoPoint p1 = null;

        try {
            addressList = coder.getFromLocationName(addressStr,5);
            if (addressList==null) {
                return null;
            }
            Address location = addressList.get(0);

            p1 = new GeoPoint(location.getLatitude(), location.getLongitude());

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getDistanceInMiles(com.sbhandare.pawdopt.Model.Address address){
        GeoPoint currenLatLong = getCurrentLatLong();
        GeoPoint addressLatLong = getLatLongFromAddress(address);

        if(currenLatLong!=null & addressLatLong!=null) {
            Location loc1 = new Location("");
            loc1.setLatitude(currenLatLong.getLattitude());
            loc1.setLongitude(currenLatLong.getLongitude());

            Location loc2 = new Location("");
            loc2.setLatitude(addressLatLong.getLattitude());
            loc2.setLongitude(addressLatLong.getLongitude());

            return Math.round(loc1.distanceTo(loc2) * 0.00062137);
        }
        return 0;
    }
}
