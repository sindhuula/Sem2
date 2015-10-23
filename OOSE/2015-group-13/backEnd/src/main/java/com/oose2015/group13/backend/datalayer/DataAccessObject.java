package com.oose2015.group13.backend.datalayer;

import java.util.Properties;
import java.util.Set;

import com.mongodb.MongoClient;

public abstract class DataAccessObject<E> {
    protected MongoClient client;
    protected Properties properties;
    
    public DataAccessObject(MongoClient client, Properties properties) {
        this.client = client;
        this.properties = properties;
    }
    
    //Write data accessing methods here
    //ie, a method to get all users, a method to get user by ID...
    public abstract Set<E> findAll();
    public abstract E find(String id);
    public abstract void insert(E item);
}
