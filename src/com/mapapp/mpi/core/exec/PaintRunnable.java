package com.mapapp.mpi.core.exec;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import com.mapapp.mpi.api.Paintable;

import java.util.ArrayList;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 9/4/2014 at 11:17 AM
 */
public class PaintRunnable implements Runnable {

    static Canvas canvas;
    ArrayList<Paintable> paintables;
    static boolean hasInited = false;

    public PaintRunnable(Canvas canvas, ArrayList<Paintable> paintables){
        PaintRunnable.canvas = canvas;
        this.paintables = paintables;
    }

    @Override
    public void run() {
        System.out.println("Entering");

        for (Paintable p : paintables) {
            p.onDraw(canvas);
            System.out.println("Iteration");
        }
        System.out.println("Exiting");
    }

    protected static void setCanvas(Canvas canvas){
        PaintRunnable.canvas = canvas;
    }

}
