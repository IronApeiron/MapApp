package com.mapapp.mpi.api;

import android.app.Activity;
import android.content.Context;
import android.location.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.GeoPoint;
import com.mapapp.mpi.core.exec.MainActivity;

import java.io.IOException;
import java.util.List;

/**
 * A set of utilities to be used with plugins.
 *
 * @author Ganesh Ravendranathan
 *         Last Modified: 9/5/2014 at 11:21 PM
 */
public class MapUtils {

    /**
     * An active activity to access methods that can't be accessed in non-activity classes.
     */
    static Activity activity = MainActivity.getInstance();

    /**
     * Converts an address into a {@link com.google.android.gms.maps.model.LatLng} location on a
     * GoogleMap.
     *
     * @param address The address of a location as a {@link java.lang.String}.
     * @return The {@link com.google.android.gms.maps.model.LatLng} of the location found.
     */
    public static LatLng getLatLngLocation(String address){
        return toLatLng(getLocationFromAddress(address));
    }

    /**
     * Retrieves the location from an address as a {@link com.google.android.maps.GeoPoint}.
     * @param strAddress The address of the location as a {@link java.lang.String}
     * @return The {@link com.google.android.maps.GeoPoint} of where the address points to.
     */
    public static GeoPoint getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new GeoPoint((int) (location.getLatitude() * 1E6),
                    (int) (location.getLongitude() * 1E6));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts a {@link com.google.android.maps.GeoPoint} to a {@link com.google.android.gms.maps.model.LatLng}.
     * @param gPoint The {@link com.google.android.maps.GeoPoint} to be converted.
     * @return A {@link com.google.android.gms.maps.model.LatLng} derived from the {@link com.google.android.maps.GeoPoint}.
     */
    public static LatLng toLatLng(GeoPoint gPoint){
        double lat = gPoint.getLatitudeE6() / 1E6;
        double lng = gPoint.getLongitudeE6() / 1E6;
        return new LatLng(lat, lng);
    }

    /**
     * Credits to MCApps on StackOverFlow
     *
     * Returns the current location of the device.
     * @return Returns a {@link android.location.Location} of the device.
     */
    public static Location getMyLocation() {
        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Location wasn't found, check the next most accurate place for the current location
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // Finds a provider that matches the criteria
            String provider = lm.getBestProvider(criteria, true);
            // Use the provider to get the last known location
            myLocation = lm.getLastKnownLocation(provider);
        }

        return myLocation;
    }

    /**
     * Returns an active activity, for internal use only.
     *
     * @return An active activity.
     */
    private static Activity getActivity(){
        return activity;
    }
}
