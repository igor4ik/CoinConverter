package com.igor4ik83gmail.coinconverter;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by igor4ik2 on 06.01.2015.
 */
public class Location {

    private Context context;

    public Location(Context context){
        this.context = context;
    }

    //get latitude and longitude
    public double[] getLatitudeAndLongitude()
    {
        LocationManager locationManager;
        String provider;
        double[] lat_lng = new double[2];
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        android.location.Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            lat_lng[0] = location.getLatitude();
            lat_lng[1] = location.getLongitude();
            return lat_lng;
        }
        else {
            Toast.makeText(context.getApplicationContext(), "Location not available", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    //get country code
    public String getLocationCode(){
        String locationCode;
        //get lat and lng
        double[] loc = getLatitudeAndLongitude();
        //get country code from lat and lng
        locationCode = getCountryFromLocation(loc[0], loc[1]);
        if(locationCode != null){
            //return currency code
            return locationCode;
        }
        return null;
    }

    //get country name from latitude and longitude
    public String getCountryFromLocation(double _lat, double _lng)
    {
        Geocoder gcd = new Geocoder(context.getApplicationContext(), Locale.ENGLISH);
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(_lat, _lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0)
            return addresses.get(0).getCountryCode();
        return null;
    }
}
