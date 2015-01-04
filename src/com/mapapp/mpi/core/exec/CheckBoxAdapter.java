package com.mapapp.mpi.core.exec;

import android.app.ActionBar;
import android.app.ProgressDialog;
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
 * TODO: Add a list for custom plugins
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 2:33 PM
 */
public class CheckBoxAdapter extends BaseAdapter {

    private List<Plugin> plugins;

    private LayoutInflater inflater;

    private Context ctx;

    private HashMap<CheckBox, Boolean> checkBoxMap = new HashMap<>();

    private int layout;

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
                        Paintable p = plugins.get((Integer)buttonView.getTag());
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
