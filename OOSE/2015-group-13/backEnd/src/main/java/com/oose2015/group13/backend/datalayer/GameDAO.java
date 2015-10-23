package com.oose2015.group13.backend.datalayer;

import java.util.Properties;
import java.util.Set;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.oose2015.group13.backend.gamelogic.game.Game;

public class GameDAO extends DataAccessObject<Game> {

    public GameDAO(MongoClient client, Properties properties) {
        super(client, properties);
    }

    //Implement your methods below... You can also implement more than just these

    @Override
    public Set<Game> findAll() {
        //EXAMPLE USAGE OF MONGODB
        MongoDatabase db = client.getDatabase(properties.getProperty("db.database"));
        MongoCollection collection = db.getCollection(properties.getProperty("db.collection.games"));
        
        return null;
    }

    @Override
    public Game find(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insert(Game item) {
        // TODO Auto-generated method stub
        
    }

}
