package com.mapapp.mpi.core.exec;

import com.mapapp.mpi.api.Paintable;
import com.mapapp.mpi.api.PluginMsg;

/**
 * Represents a {@link com.mapapp.mpi.core.exec.Plugin} outline.
 *
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 5:06 PM
 */
public abstract class Plugin implements Paintable{

    /**
     * Called on initialization of a {@link com.mapapp.mpi.core.exec.Plugin}.
     */
    public abstract void onInit();

    /**
     * Called repeatedly when the map is active.
     */
    public abstract void mainLoop();

    /**
     * Called when the {@link com.mapapp.mpi.core.exec.Plugin} is stopped by the user.
     */
    public abstract void onDestroy();

    /**
     * A variable to keep track if a plugin is active or not.
     */
    private boolean active = false;

    /**
     * On every loop of mainLoop, a developer can set a code for its state, or to instruct
     * the plugin manager to do something.
     *
     * @param code A code found in {@link com.mapapp.mpi.api.PluginMsg}.
     */
    protected void setProgramLoopCode(int code){
        this.programLoopCode = code;
    }

    /**
     * A variable to keep track if a plugin has initialized.
     */
    protected boolean hasInitialized = false;

    /**
     * A method to keep track if a plugin is active or not.
     * @return True if the {@link com.mapapp.mpi.core.exec.Plugin} is active, false if otherwise.
     */
    protected boolean isActive(){
        return active;
    }

    /**
     * A method to toggle if a {@link com.mapapp.mpi.core.exec.Plugin} is active or not.
     */
    protected void setActive(boolean isActive){
        active = isActive;
    }

    /**
     * A variable to keep track of the {@link com.mapapp.mpi.core.exec.Plugin}'s state.
     */
    protected int programLoopCode = 0;

}
