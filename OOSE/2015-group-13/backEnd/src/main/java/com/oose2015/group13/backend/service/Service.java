package com.oose2015.group13.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service is parameterized and handles requests from its related
 * controller. It decides how to create, find, update, delete its related
 * objects.
 * @param <E> (Usually Game or User, etc)
 */
public abstract class Service<E> {
    protected final Logger logger = LoggerFactory.getLogger(Service.class);
    
    /**
     * Accepts JSON string and creates E object based on JSON fields
     * @param body String
     * @return E object
     */
    public abstract E create();
    
    /**
     * Accepts ID of object to update and JSON string of fields/values to update,
     * and updates object
     * @param id String
     * @param body (JSON String)
     * @return updated E Object
     */
    public abstract E update(String id, String body);
    
    /**
     * Find the E Object given its ID string
     * @param id
     * @return E Object
     */
    public abstract E find(String id);
    
    /**
     * Delete E Object given its ID string
     * @param id String
     * @return boolean (whether the delete was successful)
     */
    public abstract boolean delete(String id);
}
