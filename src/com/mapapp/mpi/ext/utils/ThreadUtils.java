package com.mapapp.mpi.ext.utils;

/**
 * @author Ganesh Ravendranathan
 */
public class ThreadUtils {

    /**
     * Allows a thread to sleep.
     *
     * @param l The amount of time to sleep in milliseconds.
     */
    public static void sleep(long l){
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
