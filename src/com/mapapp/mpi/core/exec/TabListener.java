package com.mapapp.mpi.core.exec;

import android.app.*;

import com.mapapp.R;
import com.mapapp.mpi.core.db.insns.MainTabInfo;
import com.mapapp.mpi.core.plugins.TakeMeThereFragment;
import com.mapapp.mpi.core.plugins.TakeMeTherePlugin;

import java.util.ArrayList;

/**
 * Handles the various {@link android.app.Fragment}s, including ones of {@link com.mapapp.mpi.core.exec.Plugin}s
 * and the {@link com.google.android.gms.maps.GoogleMap} itself.
 *
 */
public class TabListener implements ActionBar.TabListener {

    /**
     * The next {@link android.app.Fragment} to be displayed.
     */
    Fragment nextFrag;

    /**
     * The previous {@link android.app.Fragment} displayed.
     */
    static Fragment prevFrag;

    /**
     * Creates a new {@link com.mapapp.mpi.core.exec.TabListener} with a {@link android.app.Fragment}.
     * @param fragment The initial {@link android.app.Fragment} to be displayed.
     */
    public TabListener(Fragment fragment) {
        this.nextFrag = fragment;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub
        MainTabInfo selectedTabInfo = null;

        for(MainTabInfo m : MainTabInfo.values()){
            if(m.getTitle() == tab.getText()){
                selectedTabInfo = m;
            }
        }

        if(selectedTabInfo != null){
            MainActivity instance = MainActivity.getInstance();
            if(instance != null){
                instance.getActionBar().setTitle(selectedTabInfo.getDesc());
            }
        }

        System.out.println("Replacing fragment!");

        ft.replace(R.id.fragment_container, nextFrag);

        prevFrag = nextFrag;
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub
        ft.remove(nextFrag);
    }


    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        ft.show(nextFrag);
    }
}