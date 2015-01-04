package com.mapapp.mpi.core;

/**
 * @author Ganesh Ravendranathan
 */
public enum Server {

    MAIN("Main");


    private String name;

    /**
     * Constructs a {@link Server} constant with various utilities.
     * @see com.mapapp.mpi.core.db.AuthClient
     *
     * @param name The name of the server.
     */
    private Server(String name){
        this.name = name;
    }

}
