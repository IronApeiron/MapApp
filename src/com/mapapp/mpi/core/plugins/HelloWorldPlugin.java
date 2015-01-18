package com.mapapp.mpi.core.plugins;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.mapapp.mpi.api.PluginDetails;
import com.mapapp.mpi.core.exec.Plugin;


@PluginDetails(author = "Ganesh Ravendranathan",
               name = "Hello World",
               description = "Says hello world on the map!",
               version = "0.1",
               requestTab = true)

/**
 * A simple plugin used to display "Hello World" on the map screen when activated.
 *
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 5:15 PM
 */
public class HelloWorldPlugin extends Plugin {

    @Override
    public void onInit(){

    }

    @Override
    public void mainLoop(){
      setProgramLoopCode(0);
    }

    @Override
    public void onDestroy(){

    }

    @Override
    public void onDraw(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.GREEN);
        p.setTextSize(40);
        p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));

        c.drawText("Hello World!", 0, 0 + p.getTextSize(), p);

    }

}
