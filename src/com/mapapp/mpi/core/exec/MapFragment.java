package com.mapapp.mpi.core.exec;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.view.*;

import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.mapapp.R;

import java.util.Map;

/**
 * This class holds the {@link com.google.android.gms.maps.GoogleMap}. This is the core of what
 * this app can provide.
 *
 * @author Ganesh Ravendranathan
 */
public class MapFragment extends Fragment {

    /**
     * A {@link com.google.android.gms.maps.GoogleMap} to be used for various reasons.
     */
    private GoogleMap gMap;

    /**
     * An instance of {@link com.google.android.gms.maps.MapFragment}.
     */
    public static MapFragment instance;

    /**
     * A toggle to see if the map is available.
     */
    private static boolean isReady;

    /**
     * Checks to see if the local {@link com.google.android.gms.maps.GoogleMap} is ready to be
     * interacted with.
     *
     * @return True, if the {@link com.google.android.gms.maps.GoogleMap} is readty to be interacted with, false if otherwise.
     */
    protected static boolean isReady(){
        return isReady;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.mapbay, container, false);
        centerMapOnMyLocation();
        gMap = ((com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        System.out.println("New instance of MapFragment made!");
        if(instance == null) {
            instance = this;
        }
        isReady = true;

        MainActivity.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(final Plugin p : PluginManager.getAll()){
                    if(p.isActive()){
                        System.out.println("ASDASDASD");
                        p.onInit();
                        p.hasInitialized = true;
                    }
                }
            }
        });

        return rootView;
    }


    @Override
    public void onDestroyView ()
    {
        try{
            com.google.android.gms.maps.MapFragment fragment = ((com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map));
            FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
            System.out.println("MapFragment instance killed.");
            isReady = false;
            PaintOverlayPanel.delegateToUiThread = true;
        }catch(Exception e){
        }
        super.onDestroyView();
    }

    /**
     * Centers the map on the device's current {@link android.location.Location}.
     */
    private void centerMapOnMyLocation() {

        com.google.android.gms.maps.MapFragment mapFrag = ((com.google.android.gms.maps.MapFragment)getFragmentManager().findFragmentById(R.id.map));

        GoogleMap mMap = mapFrag.getMap();

        CharSequence cs = "Map is null";

        if(mMap == null){
            Toast.makeText(getActivity(), cs, Toast.LENGTH_LONG);
            return;
        }

        mMap.setMyLocationEnabled(true);
        Location currentLocation = getMyLocation();
        if(currentLocation!=null) {
            LatLng currentCoordinates = new LatLng(
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude());
           // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinates, 16));
        }
    }

        protected Location getMyLocation() {
            // Get location from GPS if it's available
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

    public GoogleMap getMap(){
        return gMap;
    }

    public static MapFragment getInstance(){
        return instance;
    }
}
