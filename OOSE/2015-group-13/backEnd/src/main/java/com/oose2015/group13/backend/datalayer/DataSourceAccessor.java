package com.oose2015.group13.backend.datalayer;

import java.util.Properties;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * DataSourceAccessor is a singleton class which creates a database 
 * connection (MongoDB) for clients to use.
 */
public class DataSourceAccessor {
    private static MongoClient _client;
    
    /**
     * MongoClient is a synchronized method (to prevent race conditions on the database client).
     * If the client is null, it creates the MongoClient with the properties object.
     * Otherwise it just returns the existing client.
     * @param properties, which contains the database properties needed
     * to connect to the database. (ie, username, password, host, port, and database)
     * @return
     */
    public static synchronized MongoClient getDataSource(Properties properties) {
        
        if(_client == null) {
            String uriString = String.format("mongodb://%s:%s@%s:%s/%s", properties.getProperty("db.username"),
                    properties.getProperty("db.password"), properties.getProperty("db.host"),
                    properties.getProperty("db.port"), properties.getProperty("db.database"));
            MongoClientURI uri = new MongoClientURI(uriString);        
            _client = new MongoClient(uri);
        }
        
        return _client;
    }
    
    public static synchronized boolean closeDataSource() {
        if(_client != null) {
            _client.close();
            return true;
        }
        
        return false;
    }
}
