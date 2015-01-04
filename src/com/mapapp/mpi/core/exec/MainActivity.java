package com.mapapp.mpi.core.exec;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.mapapp.R;
import com.mapapp.mpi.core.db.insns.MainTabInfo;

import java.util.ArrayList;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 2:36 PM
 */
public class MainActivity extends Activity{

    private ViewPager viewPager;

    protected static ActionBar actionBar;

    private ArrayList<String> mainTabTitles = new ArrayList<>();

    private static MainActivity mainActivityInstance;

    protected static ArrayList<Fragment> pluginFragmentTabs = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        mainActivityInstance = MainActivity.this;

        actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);

        // Decided for this to be #1 - to be automated via enum w/for loop
        pluginFragmentTabs.add(new MapFragment());
        pluginFragmentTabs.add(new PluginFragment());

        mainTabTitles.add(MainTabInfo.MAP.getTitle());
        mainTabTitles.add(MainTabInfo.MY_PLUGINS.getTitle());

        actionBar.setTitle(MainTabInfo.MAP.getDesc());
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.addTab(actionBar.newTab().setText(MainTabInfo.MAP.getTitle())
                .setTabListener(new TabListener(new MapFragment())));

        actionBar.addTab(actionBar.newTab().setText(MainTabInfo.MY_PLUGINS.getTitle())
                .setTabListener(new TabListener(new PluginFragment())));

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

       /*
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

                MainTabInfo selectedTabInfo = null;

                for(MainTabInfo m : MainTabInfo.values()){
                    if(m.getTitle() == actionBar.getSelectedTab().getText()){
                        selectedTabInfo = m;
                    }
                }

                if(selectedTabInfo != null){
                    actionBar.setTitle(selectedTabInfo.getDesc());
                }

                System.out.println("ASDD" + " " + position);

            }


            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        */
    }

    protected static MainActivity getInstance(){
        return mainActivityInstance;
    }

}
