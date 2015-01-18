package com.mapapp.mpi.core.plugins;

import android.graphics.*;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.mapapp.R;
import com.mapapp.mpi.api.GoogleDirection;
import com.mapapp.mpi.api.MapUtils;
import com.mapapp.mpi.api.PluginDetails;
import com.mapapp.mpi.core.exec.LoginActivity;
import com.mapapp.mpi.core.exec.MainActivity;
import com.mapapp.mpi.core.exec.MapFragment;
import com.mapapp.mpi.core.exec.Plugin;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 5:15 PM
 */
@PluginDetails(author = "Ganesh Ravendranathan",
               name = "TakeMeThere",
               description = "Now.",
               version = "0.1",
               requestTab = true)

public class TakeMeTherePlugin extends Plugin{

    private static float zoom;

    private GoogleMap gMap;

    private boolean hasZoomed = false;

    protected LatLng start;
    protected LatLng end;

    protected boolean btnDirectionsClicked = false;

    @Override
    public void onInit() {
        System.out.println("<TakeMeThere> Initializing...");
        gMap = MapFragment.getInstance().getMap();

        if(btnDirectionsClicked) {
            System.out.println("GoogleMap is null? = " + (gMap == null));

            System.out.println("Start = " + start);
            System.out.println("End = " + end);

            GoogleDirection gd = new GoogleDirection(MainActivity.getInstance());
            gd.setOnDirectionResponseListener(new GoogleDirection.OnDirectionResponseListener() {
                public void onResponse(String status, Document doc, GoogleDirection gd) {
                    System.out.println("Performing onResponse!");
                    gMap.addPolyline(gd.getPolyline(doc, 3, Color.RED));
                    gMap.addMarker(new MarkerOptions().position(start)
                            .icon(BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_GREEN)));

                    gMap.addMarker(new MarkerOptions().position(end)
                            .icon(BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_GREEN)));
                }
            });

            gd.setLogging(true);
            gd.request(start, end, GoogleDirection.MODE_DRIVING);

            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 10));
        }
    }

    @Override
    public void mainLoop() {
        System.out.println("<TakeMeThere> Looping");
        zoom = gMap.getCameraPosition().zoom;
        setProgramLoopCode(0);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDraw(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setTextSize(40);
        p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));

        if ((MapFragment.getInstance() != null) && (c != null)) {
            c.drawText("<TakeMeThere> Zoom Level = " + zoom, 0, 55 + p.getTextSize(), p);
            c.drawText("<TakeMeThere> Has clicked> = " + TakeMeThereFragment.hasClicked, 0, 95 + p.getTextSize(), p);
        }

    }

    public void onButtonClick(View v) {
        //Find components by id
        //set listeners
    }

}
