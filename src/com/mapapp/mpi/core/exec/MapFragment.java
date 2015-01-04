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
 * @author Ganesh Ravendranathan
 */
public class MapFragment extends Fragment {

    private GoogleMap gMap;
    public static MapFragment instance;

    Fragment prevFrag;

    private static boolean isReady;

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
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinates, 16));
        }
    }

        private Location getMyLocation() {
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

    public float getCameraZoomLevel(){
        return gMap.getCameraPosition().zoom;
    }

    public static MapFragment getInstance(){
        return instance;
    }
}
