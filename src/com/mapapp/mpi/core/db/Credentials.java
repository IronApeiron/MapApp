package com.mapapp.mpi.core.db;

/**
 * Represents a {@link com.mapapp.mpi.core.db.User}'s credentials.
 * @author Ganesh Ravendranathan
 * @deprecated Unused.
 */
public abstract class Credentials<K, V> {

    private K key;

    private V val;


    public Credentials(K key, V val){
        this.key = key;
        this.val = val;
    }

}
