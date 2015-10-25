package com.oose2015.group13.backend.datalayer;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.oose2015.group13.backend.user.Settings;
import com.oose2015.group13.backend.user.User;

public class UserDAO extends DataAccessObject<User> {

    public UserDAO(MongoClient client, Properties properties) {
        super(client, properties);
    }

    //Implement your methods below... You can also implement more than just these
    

    @Override
    public Set<User> findAll() {
        Set<User> users = new HashSet<>();
        
        //EXAMPLE USAGE OF MONGODB TAKEN FROM MONGODB DOCS
        MongoDatabase db = client.getDatabase(properties.getProperty("db.database"));
        MongoCollection collection = db.getCollection(properties.getProperty("db.collection.users"));
        FindIterable<Document> iterable = collection.find();
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
                // *** Create new User here and add to users Set to be returned ***
            }
        });
        
        return users;
    }

    @Override
    public User find(String id) {
        // TODO Auto-generated method stub
        //TEST
        User user = new User();
        user.setUserID(id);
        user.setSettings(new Settings(0, 0, false));
        return user;
    }

    @Override
    public void insert(User item) {
        // TODO Auto-generated method stub
        
    }
    
    public void update(String challengedID, String challengerID, String gameID)
    {
        //Update user's record with the new data.
        /*This may or maynot be right
        collection.update(
                {'id':{challengedID}},
        {$set:{'challenges':challengerID,'gameID':gameID}}

        )
        */
    }
}
