package com.mapapp.mpi.ext.utils;

import android.graphics.*;
import android.view.SurfaceHolder;
import com.mapapp.mpi.core.exec.PaintOverlayPanel;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 11:40 AM
 */
public class MapOverlayPainter extends Thread{

    private SurfaceHolder surfaceHolder;
    private PaintOverlayPanel paintPanel;
    private boolean running = false;

    public boolean hasPorterDuffed = false;

    public MapOverlayPainter(SurfaceHolder surfaceHolder, PaintOverlayPanel paintPanel){
        this.surfaceHolder = surfaceHolder;
        this.paintPanel = paintPanel;
    }

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
