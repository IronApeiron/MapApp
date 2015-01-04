package com.mapapp.mpi.core.plugins;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapapp.R;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 9/3/2014 at 12:30 AM
 */
public class TakeMeThereFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("Inflating hello world!");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.plugin_takemethere, container, false);
    }
}
