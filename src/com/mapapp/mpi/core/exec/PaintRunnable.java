package com.mapapp.mpi.core.exec;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import com.mapapp.mpi.api.Paintable;

import java.util.ArrayList;

/**
 * Responsible for dispatching custom graphics onto a {@link com.google.android.gms.maps.GoogleMap}.
 *
 * @author Ganesh Ravendranathan
 *         Last Modified: 9/4/2014 at 11:17 AM
 */
public class PaintRunnable implements Runnable {

    /**
     * A {@link android.graphics.Canvas} to be drawn to.
     */
    static Canvas canvas;

    /**
     * A set of {@link com.mapapp.mpi.api.Paintable}s to be used with a {@link android.graphics.Canvas}.
     */
    ArrayList<Paintable> paintables;

    /**
     * Creates a new {@link com.mapapp.mpi.core.exec.PaintRunnable}.
     *
     * @param canvas The {@link android.graphics.Canvas} to be drawn to.
     * @param paintables The set of {@link com.mapapp.mpi.api.Paintable}s to be fed a {@link android.graphics.Canvas}.
     */
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

    /**
     * Sets a canvas to be drawn to.
     *
     * @param canvas A {@link android.graphics.Canvas} to be drawn on.
     */
    protected static void setCanvas(Canvas canvas){
        PaintRunnable.canvas = canvas;
    }

}
