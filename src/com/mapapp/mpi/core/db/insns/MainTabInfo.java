package com.mapapp.mpi.core.db.insns;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 4:10 PM
 */
public enum MainTabInfo {

    MY_PLUGINS("My Plugins", "Manage my Plugins"), MAP("Map", "Map");

    private final String DESC, TITLE;

    MainTabInfo(final String TITLE, final String DESC){
        this.TITLE = TITLE;
        this.DESC = DESC;
    }

    public String getTitle(){
        return TITLE;
    }

    public String getDesc(){
        return DESC;
    }
   
}
