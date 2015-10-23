package com.oose2015.group13.backend.server;
import static spark.Spark.ipAddress;
import static spark.Spark.port;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oose2015.group13.backend.controller.Controller;
import com.oose2015.group13.backend.controller.GameController;
import com.oose2015.group13.backend.controller.UserController;
import com.oose2015.group13.backend.datalayer.DataSourceAccessor;
import com.oose2015.group13.backend.datalayer.GameDAO;
import com.oose2015.group13.backend.datalayer.UserDAO;
import com.oose2015.group13.backend.service.GameService;
import com.oose2015.group13.backend.service.UserService;

public class Server {
    private static final String PROPERTIES_FILE = "config.properties";
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    
    public static void main(String[] args) {
        Properties properties = null;
        
        try {
            properties = readConfigProperties(PROPERTIES_FILE);
        } catch(IOException e) {
            e.printStackTrace();
            logger.error("Cannot read properties file. Aborting execution");
            throw new IllegalStateException("Cannot load properties file from " + PROPERTIES_FILE +
                    "\nABORTING EXECUTION", e); 
        }
        
        ipAddress(properties.getProperty("ip.address"));
        port(Integer.valueOf(properties.getProperty("port")));
        
        logger.info("Starting controllers");
        GameDAO gameDAO = new GameDAO(DataSourceAccessor.getDataSource(properties), properties);
        UserDAO userDAO = new UserDAO(DataSourceAccessor.getDataSource(properties), properties);
        Controller gameController = new GameController(new GameService(gameDAO, userDAO), properties);
        Controller userController = new UserController(new UserService(userDAO, gameDAO), properties);
    }
    
    private static Properties readConfigProperties(String fileName) throws IOException {
        InputStream iStream = Server.class.getClassLoader().getResourceAsStream(fileName);
        Properties properties = new Properties();
        
        if(iStream != null) {
            properties.load(iStream);
        } else {
            throw new IOException("InputStream is null from file: " + fileName);
        }
        
        return properties;
    }
}
