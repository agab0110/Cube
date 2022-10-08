package com.videogame.app;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoClass {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static void connect() {
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.SEVERE);
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("videogame");
    }

    public static void disconnect() {
        mongoClient.close();
    }

    public static MongoDatabase getDatabase() {
        return database;
    }
}
