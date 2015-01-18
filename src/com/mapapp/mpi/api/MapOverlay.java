package com.mapapp.mpi.api;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.mapapp.mpi.core.Context;

/**
 * This class provides the overlay on the map itself.
 *
 * Other developers could use this with their plugins to display information.
 *
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 12:11 PM
 *
 * @deprecated
 */
public class MapOverlay extends Overlay{

    /*
     * Unused.
     */
    final GeoPoint gPoint;

    /**
     * The {@link android.content.Context} of the application.
     */
    final Context ctx;

    /*
     * Unused
     */
    final int drawable;

    public MapOverlay(Context ctx, GeoPoint gPoint, int drawable){
        this.ctx = ctx;
        this.gPoint = gPoint;
        this.drawable = drawable;
    }

    @Override
    public void draw(Canvas canvas, MapView mView, boolean shadow){
        super.draw(canvas, mView, shadow);
        Paint paint = new Paint();
        Typeface t = Typeface.create("Arial", Typeface.BOLD);
        paint.setColor(Color.GREEN);
        paint.setTypeface(t);
        paint.setTextSize(20);
        canvas.drawText("Hello World!", 500, 1000, paint);
    }

    @Override
    public boolean onTap(GeoPoint p, MapView mView){
        return super.onTap(p, mView);
    }


}
