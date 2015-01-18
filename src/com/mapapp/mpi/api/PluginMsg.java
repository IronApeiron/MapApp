package com.mapapp.mpi.api;

/**
 * To be used with {@link com.mapapp.mpi.core.exec.Plugin}s.
 *
 * The following are exit codes.
 *
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 5:35 PM
 */
public interface PluginMsg {

    /**
     * Indicates that the {@link com.mapapp.mpi.core.exec.Plugin} should be shut down immediately.
     */
    public int FORCE_SHUTDOWN = -1;

}
