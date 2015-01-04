package com.mapapp.mpi.core.plugins;

import android.graphics.*;
import com.google.android.gms.maps.GoogleMap;
import com.mapapp.mpi.api.PluginDetails;
import com.mapapp.mpi.core.exec.MapFragment;
import com.mapapp.mpi.core.exec.Plugin;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 5:15 PM
 */
@PluginDetails(author = "Ganesh Ravendranathan",
               name = "TakeMeThere",
               description = "Now.",
               version = "0.1")

public class TakeMeTherePlugin extends Plugin {

    private static float zoom;

    @Override
    public void onInit(){
        System.out.println("<TakeMeThere> Initializing...");
        requestTab();
        zoom = MapFragment.getInstance().getCameraZoomLevel();
    }

    @Override
    public void mainLoop(){
        System.out.println("<TakeMeThere> Looping");
        zoom = MapFragment.getInstance().getCameraZoomLevel();
        setProgramLoopCode(0);
    }

    @Override
    public void onDestroy(){

    }

    @Override
    public void onDraw(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setTextSize(40);
        p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));

        if((MapFragment.getInstance() != null) && (c != null))
            c.drawText("<TakeMeThere> Zoom Level = " + zoom, 0, 55 + p.getTextSize(), p);

    }

}
