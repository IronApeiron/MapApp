package com.mapapp.mpi.core.exec;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.mapapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 2:40 PM
 */
public class PluginFragment extends ListFragment {

    static List<Plugin> plugins;
    protected static ArrayList<String> pluginTabTitles = new ArrayList<>();

    {
        if(plugins == null)
           plugins = PluginManager.getAll();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new CheckBoxAdapter(getActivity(), R.layout.pluginlayout, plugins));
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id){
        System.out.println("Click! " + pos);
    }

}
