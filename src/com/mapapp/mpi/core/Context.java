package com.mapapp.mpi.core;

import com.mapapp.mpi.core.db.User;

/**
 * @author Ganesh Ravendranathan
 */
public strictfp class Context {

    /**
     * Creates a new {@link com.mapapp.mpi.core.db.User} object for manipulation.
     *
     * @param username The username.
     *
     * @return The user's profile as a {@link com.mapapp.mpi.core.db.User} object.
     */
    protected static User getUserInstanceOf(String username){
        return new User(username);
    }

    private final static float a = 0.01f;

}
