package com.mapapp.mpi.ext.utils;

import android.graphics.*;
import android.view.SurfaceHolder;
import com.mapapp.mpi.core.exec.PaintOverlayPanel;

/**
 * This class paints onto the {@link com.google.android.gms.maps.GoogleMap}.
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 11:40 AM
 */
public class MapOverlayPainter extends Thread{

    /**
     * A {@link android.view.SurfaceHolder} to dispatch events to.
     */
    private SurfaceHolder surfaceHolder;

    /**
     * A {@link com.mapapp.mpi.core.exec.PaintOverlayPanel} to be painted to.
     */
    private PaintOverlayPanel paintPanel;

    /**
     * Toggle to see if the painter is running.
     */
    private boolean running = false;

    /**
     * True if the {@link android.graphics.PorterDuffXfermode} was successful.
     */
    public boolean hasPorterDuffed = false;

    /**
     * Constructs a new {@link com.mapapp.mpi.ext.utils.MapOverlayPainter}.
     * @param surfaceHolder The surface to be edited.
     * @param paintPanel The {@link com.mapapp.mpi.core.exec.PaintOverlayPanel} to be painted to.
     */
    public MapOverlayPainter(SurfaceHolder surfaceHolder, PaintOverlayPanel paintPanel){
        this.surfaceHolder = surfaceHolder;
        this.paintPanel = paintPanel;
    }

    /**
     * Toggle for when the painter is running.
     *
     * @param running True if it's running, false otherwise.
     */
    public void setRunning(boolean running){
        this.running = running;
    }


    @Override
    public void run(){
        Canvas canvas = null;

        while(running){
            try{
                canvas = surfaceHolder.lockCanvas();

                synchronized (surfaceHolder){
                    if(canvas != null) {
                        Paint paint = new Paint();
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                        canvas.drawPaint(paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                        System.out.println("Porterduff set");
                        hasPorterDuffed = true;
                        paintPanel.onDraw(canvas);
                    }
                }
            }finally {
                if(canvas != null){
                    System.out.println("POST to canvas");
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

    }
}
