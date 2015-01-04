package com.mapapp.mpi.core.exec;

import android.app.ActionBar;
import android.app.Fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.view.View;
import com.mapapp.R;
import com.mapapp.mpi.api.Paintable;
import com.mapapp.mpi.api.PluginDetails;
import com.mapapp.mpi.api.PluginMsg;
import com.mapapp.mpi.core.plugins.HelloWorldPlugin;
import com.mapapp.mpi.core.plugins.TakeMeTherePlugin;
import com.mapapp.mpi.ext.utils.ClassFinder;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 5:24 PM
 */
public class PluginManager {

    private static List<Plugin> plugins = Collections.synchronizedList(new ArrayList<Plugin>());

    private static boolean hasInitialized = false;

    private static ExecutorService exeServ;


    static{
        if(!hasInitialized){
            initialize();
            hasInitialized = true;
        }
    }

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

    protected static void submitNewPluginInstance(int pos){
        final Plugin plugin = plugins.get(pos);

        // Init plugins based on if the user sets it to be active
        plugin.setActive(true);

        LoginActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                plugin.onInit();
            }
        });

        Fragment pluginFragClass = null;
        try {
            pluginFragClass = (Fragment)Class.forName("com.mapapp.mpi.core.plugins." + plugin.getClass().getSimpleName().replace("Plugin", "Fragment")).newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(plugin.hasRequestedTab() && pluginFragClass != null){
            addTab(plugin.getClass().getAnnotation(PluginDetails.class).name(), pluginFragClass);
        }else{
            System.out.println("Cannot request tab without fragment!");
        }

        submitPaintable(plugin);

        final Fragment finalPluginFragClass = pluginFragClass;

        exeServ.submit(new Runnable() {
            @Override
            public void run() {
                while((plugin.programLoopCode != PluginMsg.FORCE_SHUTDOWN)
                        && (plugin.isActive())){
                    LoginActivity.instance.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            plugin.mainLoop();
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

                if(plugin.hasRequestedTab()){
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
    }

    private static void selectTab(ActionBar b, int pos) {
        try {
            //do the normal tab selection in case all tabs are visible
            b.setSelectedNavigationItem(pos);

            //now use reflection to select the correct Spinner if
            // the bar's tabs have been reduced to a Spinner
            View action_bar_view = MainActivity.getInstance().findViewById(MainActivity.getInstance().getResources().getIdentifier("action_bar", "id", "android"));
            Class<?> action_bar_class = action_bar_view.getClass();
            Field tab_scroll_view_prop = action_bar_class.getDeclaredField("mTabScrollView");
            tab_scroll_view_prop.setAccessible(true);
            //get the value of mTabScrollView in our action bar
            Object tab_scroll_view = tab_scroll_view_prop.get(action_bar_view);
            if (tab_scroll_view == null) return;
            Field spinner_prop = tab_scroll_view.getClass().getDeclaredField("mTabSpinner");
            spinner_prop.setAccessible(true);
            //get the value of mTabSpinner in our scroll view
            Object tab_spinner = spinner_prop.get(tab_scroll_view);
            if (tab_spinner == null) return;
            Method set_selection_method = tab_spinner.getClass().getSuperclass().getDeclaredMethod("setSelection", Integer.TYPE, Boolean.TYPE);
            set_selection_method.invoke(tab_spinner, pos, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    protected static List<Plugin> getAll(){
        return plugins;
    }

    private static void submitPaintable(Paintable p){
        PaintOverlayPanel.paintables.add(p);
    }

    private static ActionBar getActionBar(){
        return MainActivity.actionBar;
    }

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

    public static ArrayList<String>getClassNamesFromPackage(String packageName){
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL packageURL;
            ArrayList<String> names = new ArrayList<String>();

            packageName = packageName.replace(".", "/");
            packageURL = classLoader.getResource(packageName);

            if (packageURL.getProtocol().equals("jar")) {
                String jarFileName;
                JarFile jf;
                Enumeration<JarEntry> jarEntries;
                String entryName;

                // build jar file name, then loop through zipped entries
                jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
                jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
                System.out.println(">" + jarFileName);
                jf = new JarFile(jarFileName);
                jarEntries = jf.entries();
                while (jarEntries.hasMoreElements()) {
                    entryName = jarEntries.nextElement().getName();
                    if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
                        entryName = entryName.substring(packageName.length(), entryName.lastIndexOf('.'));
                        names.add(entryName);
                    }
                }

                // loop through files in classpath
            } else {
                URI uri = new URI(packageURL.toString());
                File folder = new File(uri.getPath());
                // won't work with path which contains blank (%20)
                // File folder = new File(packageURL.getFile());
                File[] contenuti = folder.listFiles();
                String entryName;
                for (File actual : contenuti) {
                    entryName = actual.getName();
                    entryName = entryName.substring(0, entryName.lastIndexOf('.'));
                    names.add(entryName);
                }
            }
            return names;
        }catch(IOException e){
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


}
