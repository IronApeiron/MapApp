package com.mapapp.mpi.core.plugins;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mapapp.R;
import com.mapapp.mpi.core.exec.Plugin;

/**
 * A simple {@link android.app.Fragment} that displayes "Hello World" in a tab when the Hello World
 * plugin is activated.
 *
 * @author Ganesh Ravendranathan
 *         Last Modified: 9/2/2014 at 10:21 AM
 */
public class HelloWorldFragment extends Fragment {

    /**
     * Creates a new {@link com.mapapp.mpi.core.plugins.HelloWorldFragment}
     * @param p An active {@link com.mapapp.mpi.core.plugins.HelloWorldPlugin}.
     */
    public HelloWorldFragment(HelloWorldPlugin p){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("Inflating hello world!");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.plugin_helloworld, container, false);
    }
}
