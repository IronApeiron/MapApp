package com.mapapp.mpi.core.exec;

import android.app.*;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.mapapp.R;
import com.mapapp.mpi.api.Paintable;
import com.mapapp.mpi.api.PluginDetails;
import com.mapapp.mpi.ext.utils.Screen;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Used to populate a list with {@link android.widget.CheckBox}s.
 *
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 2:33 PM
 */
public class CheckBoxAdapter extends BaseAdapter {

    /**
     * A set of {@link com.mapapp.mpi.core.exec.Plugin}s to be displayed on the list.
     */
    private List<Plugin> plugins;

    /**
     * A {@link android.view.LayoutInflater} used to inflate the UI components.
     */
    private LayoutInflater inflater;

    /**
     * The {@link android.content.Context} of the application.
     */
    private Context ctx;

    /**
     * The layout ID of a single {@link android.widget.CheckBox} layout.
     */
    private int layout;

    /**
     * Creates a new {@link com.mapapp.mpi.core.exec.CheckBoxAdapter}.
     *
     * @param ctx The {@link android.content.Context} of this application.
     * @param layout The layout ID of the layout containing a single row with a checkbox.
     * @param plugins The list of {@link com.mapapp.mpi.core.exec.Plugin}s to be displayed on the list.
     */
    public CheckBoxAdapter(Context ctx, int layout, List<Plugin> plugins){
        this.ctx = ctx;
        this.plugins = plugins;
        this.layout = layout;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return plugins.size();
    }

    @Override
    public Object getItem(int position) {
        return plugins.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        System.out.println("POS = " + pos);
        View view = convertView;
        if(view == null) {
            view = inflater.inflate(layout, parent, false);
        }

        try{
            Plugin p = plugins.get(pos);

            TextView tv = (TextView) view.findViewById(R.id.label);
            tv.setText(p.getClass().getAnnotation(PluginDetails.class).name());

            CheckBox cb = (CheckBox) view.findViewById(R.id.chkBox);
            cb.setTag(pos);
            cb.setChecked(p.isActive());

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    plugins.get((Integer)buttonView.getTag()).setActive(isChecked);

                    if(isChecked){
                        ProgressDialog dialog = ProgressDialog.show(ctx, "Plugin Manager", "Deploying plugin, please wait...", true);
                        PluginManager.submitNewPluginInstance((Integer) buttonView.getTag());
                        dialog.dismiss();
                    }else if (!isChecked){
                        Plugin plugin = plugins.get((Integer)buttonView.getTag());
                        Paintable p = plugin;

                        if(!PluginManager.pluginFragmentMap.isEmpty() &&
                                PluginManager.pluginFragmentMap.containsKey(plugin)) {
                            FragmentManager fm = MainActivity.getInstance().getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.remove(PluginManager.pluginFragmentMap.get(plugin));
                            ft.commit();
                        }


                        if(PaintOverlayPanel.paintables.contains(p)){
                            PaintOverlayPanel.paintables.remove(p);
                        }
                    }
                }
            });


        }catch (Exception ex){
            Log.println(Log.ERROR, "PluginListDataAdapter", "PluginDetails not hooked to " + plugins.get(pos).getClass().getSimpleName() + "!");
        }
        return view;
    }

}
