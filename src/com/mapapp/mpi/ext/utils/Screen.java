package com.mapapp.mpi.ext.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Various utilities related to the device.
 *
 * @author Ganesh Ravendranathan
 */
public class Screen {

    /**
     * Creates a popup message dialog with a message.
     *
     * @param title The title of the dialog.
     * @param msg The message of the dialog.
     * @param ctx The {@link android.app.Activity} of where its being called from.
     */
    public static void showMessageDialog(String title, String msg, Context ctx){
        AlertDialog ald = new AlertDialog.Builder(ctx).create();
        ald.setTitle(title);
        ald.setMessage(msg);
        ald.show();
    }

}
