package com.mapapp.mpi.core.exec;

import com.mapapp.mpi.api.Paintable;
import com.mapapp.mpi.api.PluginMsg;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 5:06 PM
 */
public abstract class Plugin implements Paintable{

    public abstract void onInit();

    public abstract void mainLoop();

    public abstract void onDestroy();

    private boolean active = false;

    protected void setProgramLoopCode(int code){
        this.programLoopCode = code;
    }

    private boolean tabRequested = false;

    protected void toggleActive(){
        active = !active;
    }

    protected boolean isActive(){
        return active;
    }

    protected void setActive(boolean isActive){
        active = isActive;
    }

    public void requestTab(){   // To be changed to directories!
        tabRequested = true;
    }

    protected boolean hasRequestedTab(){
        return  tabRequested;
    }

    protected int programLoopCode = 0;

}
