package com.mapapp.mpi.core.exec;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.mapapp.mpi.api.Paintable;
import com.mapapp.mpi.ext.utils.MapOverlayPainter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 11:32 AM
 */
public class PaintOverlayPanel extends SurfaceView implements SurfaceHolder.Callback {

    MapOverlayPainter m;
    Context ctx;
    protected static boolean delegateToUiThread = true;

    PaintRunnable pr;

    protected static ArrayList<Paintable> paintables = new ArrayList<>();

    public PaintOverlayPanel(Context ctx) {
        super(ctx);
        this.ctx = ctx;
        initPanel();
    }

    public PaintOverlayPanel(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        this.ctx = ctx;
        initPanel();
    }

    public PaintOverlayPanel(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        this.ctx = ctx;
        initPanel();
    }

    private void initPanel() {
        setFocusable(true);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        getHolder().addCallback(this);
        m = new MapOverlayPainter(getHolder(), this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        m.setRunning(true);
        m.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("Surface Destroyed.");
        boolean retry = true;
        m.setRunning(false);
        while (retry) {
            try {
                m.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDraw(final Canvas canvas) {
        if (canvas != null && m.hasPorterDuffed) {

            if (MapFragment.isReady() && delegateToUiThread) {
                if (pr == null) {
                    pr = new PaintRunnable(canvas, paintables);
                } else {
                    PaintRunnable.setCanvas(canvas);
                }
                pr.run();
            }

        }
        super.onDraw(canvas);
    }
}