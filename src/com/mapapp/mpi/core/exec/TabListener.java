package com.mapapp.mpi.core.exec;

import android.app.*;

import com.mapapp.R;
import com.mapapp.mpi.core.db.insns.MainTabInfo;

import java.util.ArrayList;

public class TabListener implements ActionBar.TabListener {

    Fragment nextFrag;

    static Fragment prevFrag;
    static ArrayList<Fragment> hiddenFrags = new ArrayList<>();

    public TabListener(Fragment fragment) {
        // TODO Auto-generated constructor stub
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