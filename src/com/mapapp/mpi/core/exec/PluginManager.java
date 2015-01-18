package com.mapapp.mpi.core.exec;

import android.app.ActionBar;
import android.app.Fragment;

import android.content.Context;
import android.view.View;

import com.mapapp.mpi.api.Paintable;
import com.mapapp.mpi.api.PluginDetails;
import com.mapapp.mpi.api.PluginMsg;
import com.mapapp.mpi.core.plugins.HelloWorldPlugin;
import com.mapapp.mpi.core.plugins.TakeMeTherePlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class to manage the set of all {@link com.mapapp.mpi.core.exec.Plugin}s.
 *
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 5:24 PM
 */
public class PluginManager {

    /**
     * A set of all {@link com.mapapp.mpi.core.exec.Plugin}s
     */
    private static List<Plugin> plugins = Collections.synchronizedList(new ArrayList<Plugin>());

    /**
     * A variable to keep track if the {@link com.mapapp.mpi.core.exec.PluginManager} has initialized.
     */
    private static boolean hasInitialized = false;

    /**
     * An {@link java.util.concurrent.ExecutorService} to manage the concurrency of {@link Plugin}s.
     */
    private static ExecutorService exeServ;

    /**
     * A {@link java.util.HashMap} that keeps track of {@link com.mapapp.mpi.core.exec.Plugin}s that might
     * have UI {@link android.app.Fragment}s.
     */
    protected static HashMap<Plugin, Fragment> pluginFragmentMap = new HashMap<>();

    static{
        if(!hasInitialized){
            initialize();
            hasInitialized = true;
        }
    }

    /**
     * Called when {@link com.mapapp.mpi.core.exec.PluginManager} is being initialized.
     */
    private static void initialize(){
        System.out.println("Stat");
        //TODO: Search and reference plugin files
        // Add all plugins
        Context ctx = LoginActivity.context;

        if(ctx == null){
            System.out.println("ctx is null!");
        }

        // TODO: Get all classes
        plugins.add(new HelloWorldPlugin());
        plugins.add(new TakeMeTherePlugin());

        System.out.println("pSize = " + plugins.size());
        exeServ = Executors.newFixedThreadPool(plugins.size());
    }

    /**
     * Activates a {@link com.mapapp.mpi.core.exec.Plugin} found at position pos.
     * @param pos The index of the {@link com.mapapp.mpi.core.exec.Plugin}.
     */
    protected static void submitNewPluginInstance(int pos){
        Plugin placeholder = plugins.get(pos);

        final Plugin plugin;

        //Refresh plugin instance
        try {
            Plugin plug = (Plugin) Class.forName(placeholder.getClass().getName()).newInstance();
            plugins.set(pos, plug);
            plugin = plug;
            // Init plugins based on if the user sets it to be active
            plugin.setActive(true);


            Fragment pluginFragClass = null;
            try {
                pluginFragClass = (Fragment)Class.forName("com.mapapp.mpi.core.plugins." + placeholder.getClass().getSimpleName().replace("Plugin", "Fragment")).getConstructor(plugin.getClass()).newInstance(plugin);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            final PluginDetails pluginDetails = plugin.getClass().getAnnotation(PluginDetails.class);

            if(pluginDetails.requestTab() && pluginFragClass != null){
                addTab(pluginDetails.name(), pluginFragClass);
            }else{
                throw new NoClassDefFoundError("Cannot request tab without fragment!");
            }

            submitPaintable(plugin);

            final Fragment finalPluginFragClass = pluginFragClass;
            pluginFragmentMap.put(plugin, pluginFragClass);

            exeServ.submit(new Runnable() {
                @Override
                public void run() {
                    while((plugin.programLoopCode != PluginMsg.FORCE_SHUTDOWN)
                            && (plugin.isActive())){
                        MainActivity.getInstance().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(plugin.hasInitialized) {
                                    plugin.mainLoop();
                                }
                            }

                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    plugin.onDestroy();
                    plugin.setActive(false);
                    plugin.hasInitialized = false;

                    if(pluginDetails.requestTab()){
                        int idx = 0;
                        for(ActionBar.Tab t : getActiveTabs()){
                            if(t.getText().toString().equals(plugin.getClass().getAnnotation(PluginDetails.class).name())){
                                break;
                            }
                            ++idx;
                        }
                        MainActivity.getInstance().getFragmentManager().beginTransaction().remove(finalPluginFragClass);
                        getActionBar().removeTabAt(idx + 2);
                        getActionBar().setSelectedNavigationItem(1);
                    }

                }
            });

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * Retrieves an {@link java.util.ArrayList} of active {@link android.app.ActionBar.Tab}s.
     * @return A list of all {@link android.app.ActionBar.Tab}s that are available.
     */
    protected static ArrayList<ActionBar.Tab> getActiveTabs(){
        int numOfActivePlugs = 0;
        ArrayList<ActionBar.Tab> tabs = new ArrayList<>();

        for(Plugin p : plugins){
            if(p.isActive()){
                ++numOfActivePlugs;
            }
        }

        if(numOfActivePlugs > 0) {
            for (int i = 0; i < numOfActivePlugs; ++i){
                tabs.add(getActionBar().getTabAt(i + 2));
            }
        }

        return tabs;
    }

    /**
     * Retrives a list of all {@link Plugin}s.
     * @return A {@link java.util.List} of all {@link com.mapapp.mpi.core.exec.Plugin}s installed.
     *
     */
    protected static List<Plugin> getAll(){
        return plugins;
    }

    /**
     * Submits a new {@link com.mapapp.mpi.api.Paintable} job to the {@link com.mapapp.mpi.core.exec.PaintOverlayPanel}.
     * @param p The {@link com.mapapp.mpi.api.Paintable} used to draw graphics.
     */
    private static void submitPaintable(Paintable p){
        PaintOverlayPanel.paintables.add(p);
    }

    /**
     * Retrieves the {@link android.app.ActionBar}of {@link com.mapapp.mpi.core.exec.MainActivity}.
     * @return An {@link android.app.ActionBar} of the {@link com.mapapp.mpi.core.exec.MainActivity}.
     */
    private static ActionBar getActionBar(){
        return MainActivity.actionBar;
    }

    /**
     * Adds a new {@link android.app.ActionBar.Tab} to the {@link android.app.ActionBar}.
     *
     * @param title The title of the tab as a {@link java.lang.String}.
     * @param pluginLayoutFrag The {@link android.app.Fragment} containing the UI components of a {@link com.mapapp.mpi.core.exec.Plugin}.
     */
    private static void addTab(String title, Fragment pluginLayoutFrag) {
        ActionBar ac = getActionBar();

        if (ac != null) {
            System.out.println("Actionbar exists!");
            ActionBar.Tab tab = ac.newTab();
            tab.setText(title);
            tab.setTabListener(new TabListener(pluginLayoutFrag));
            ac.addTab(tab);
        } else {
            System.out.println("ActionBar is null!");
        }
    }

}
