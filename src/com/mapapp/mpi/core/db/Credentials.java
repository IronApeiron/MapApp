package com.mapapp.mpi.core.db;

/**
 * @author Ganesh Ravendranathan
 */
public abstract class Credentials<K, V> {

    private K key;

    private V val;


    public Credentials(K key, V val){
        this.key = key;
        this.val = val;
    }

}
