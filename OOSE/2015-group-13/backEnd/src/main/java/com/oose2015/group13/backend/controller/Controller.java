package com.oose2015.group13.backend.controller;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Controller {
    protected final Logger logger = LoggerFactory.getLogger(Controller.class);
    protected Properties properties;
    protected final String context;
    
    /**
     * Takes service (which has the datasource in it) as pararmeter.
     * Calls setupEndpoints() in constructor, which defines the REST API 
     * @param service
     * @param properties Properties object which contains api.context, constants, etc.
     */
    public Controller(Properties properties) {
        this.properties = properties;
        this.context = properties.getProperty("api.context");
        setupEndpoints();
    }
    
    /**
     * method is where we setup the GET, POST, PUT, DELETE
     * REST API methods for the Spark Server
     */
    protected abstract void setupEndpoints();
}
