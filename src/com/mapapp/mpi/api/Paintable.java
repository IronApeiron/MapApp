package com.mapapp.mpi.api;

import android.graphics.Canvas;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 5:11 PM
 */
public interface Paintable {

    /**
     * Called on every repaint of a {@link android.graphics.Canvas}.
     *
     * @param c The {@link android.graphics.Canvas} to be drawn on.
     */
    public void onDraw(Canvas c);

}
