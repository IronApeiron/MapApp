package com.mapapp.mpi.core;

/**
 * Represents a series of servers used to retrieve and store data.
 *
 * To be completed near the end of the project.
 *
 * @author Ganesh Ravendranathan
 */
public enum Server {

    MAIN("Main");

    /**
     * The name of the server.
     */
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
