package com.mapapp.mpi.core.plugins;

import android.app.Fragment;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import com.google.android.gms.maps.model.LatLng;

import com.mapapp.R;
import com.mapapp.mpi.api.MapUtils;
import com.mapapp.mpi.core.exec.MainActivity;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * A {@link android.app.Fragment} that gives you directions to a location from your device or between locations.
 *
 * @author Ganesh Ravendranathan
 *         Last Modified: 9/3/2014 at 12:30 AM
 */
public class TakeMeThereFragment extends Fragment implements Button.OnClickListener {

    /**
     * Checks to see if a component was clicekd on.
     */
    protected static boolean hasClicked = false;

    /**
     * Holds the starting location.
     */
    AutoCompleteTextView startTxtView;

    /**
     * Holds the ending location.
     */
    AutoCompleteTextView endTxtView;

    /**
     * A {@link android.widget.Button} which routes between the two locations when clicked.
     */
    Button btnRoute;

    /**
     * The associated active {@link com.mapapp.mpi.core.plugins.TakeMeTherePlugin}.
     */
    TakeMeTherePlugin plugin;

    /**
     * The {@link com.mapapp.mpi.core.plugins.TakeMeThereFragment} of the {@link com.mapapp.mpi.core.plugins.TakeMeTherePlugin}.
     * @param plugin The active {@link com.mapapp.mpi.core.plugins.TakeMeTherePlugin}.
     */
    public TakeMeThereFragment(TakeMeTherePlugin plugin){
        this.plugin = plugin;
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.plugin_takemethere, container, false);
        System.out.println("Createview");
        //Find components by id
        this.startTxtView = (AutoCompleteTextView) fragView.findViewById(R.id.startAutoCompTxtView);
        this.endTxtView = (AutoCompleteTextView) fragView.findViewById(R.id.endAutoCompTxtView);

        this.btnRoute = (Button) fragView.findViewById(R.id.btnRoute);

        // Find components, set listeners
        btnRoute.setOnClickListener(this);

        //set listeners
        return fragView;
    }

    @Override
    public void onClick(View v) {
        hasClicked = !hasClicked;

        if(v == btnRoute){
            if(startTxtView.getText().toString().equalsIgnoreCase("my location")) {
                Location myLoc = MapUtils.getMyLocation();
                plugin.start = new LatLng(myLoc.getLatitude(), myLoc.getLongitude());
            }else{
                plugin.start = MapUtils.getLatLngLocation(startTxtView.getText().toString());
            }

            if(endTxtView.getText().toString().equalsIgnoreCase("my location")){
                Location myLoc = MapUtils.getMyLocation();
                plugin.end = new LatLng(myLoc.getLatitude(), myLoc.getLongitude());
            }else{
                plugin.end = MapUtils.getLatLngLocation(endTxtView.getText().toString());
            }
            plugin.btnDirectionsClicked = true;
        }
    }

}
