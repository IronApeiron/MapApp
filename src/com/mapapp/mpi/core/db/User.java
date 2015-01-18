package com.mapapp.mpi.core.db;

/**
 * Creates a representation of a user.
 *
 * @author Ganesh Ravendranathan
 */
public class User {

    String alias;
    boolean isValid;

    /**
     * Creates a {@link User} object.
     *
     * @param alias The username.
     */
    public User(String alias){
        this.alias = alias;
    }

    /**
     * TODO:
     * Checks if the user is currently online.
     *
     * @return
     */
    public boolean isActive(){
        return isValid;
    }

    /**
     * Todo:
     * Checks if the user credentials verifies.
     *
     * Use with EXTREME CAUTION.
     * This MUST BE PROTECTED!
     */
    protected boolean verifies(){
       return false;
    }
}
