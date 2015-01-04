package com.mapapp.mpi.core.plugins;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mapapp.R;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 9/2/2014 at 10:21 AM
 */
public class HelloWorldFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("Inflating hello world!");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.plugin_helloworld, container, false);
    }
}
