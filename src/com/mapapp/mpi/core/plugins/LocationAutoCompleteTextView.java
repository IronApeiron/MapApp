package com.mapapp.mpi.core.plugins;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import java.util.HashMap;

public class LocationAutoCompleteTextView extends AutoCompleteTextView {

        public LocationAutoCompleteTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        /** Returns the place description corresponding to the selected item */
        @Override
        protected CharSequence convertSelectionToString(Object selectedItem) {
            /** Each item in the autocompetetextview suggestion list is a hashmap object */
            HashMap<String, String> hm = (HashMap<String, String>) selectedItem;
            System.out.println("description = " + hm.get("description"));
            return hm.get("description");
        }
    }
