package com.example.documentDb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.mongodb.client.MongoClients;

import dev.morphia.Datastore;
import dev.morphia.Morphia;

@SpringBootApplication(exclude = {
		MongoAutoConfiguration.class, 
	})
public class MorphiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MorphiaApplication.class, args);
	
	

	}
	@Bean
	public  Datastore dbSetUp() {
        //
        //setSslProperties();
        // A datastore instance for establishing a connection to Amazon DoocumentDB
	    String docdb_uri = "mongodb://docdbpocuser:Skierns203!78@docdb-poc.cluster-cresmi2847mm.us-east-1.docdb.amazonaws.com:27017/?replicaSet=rs0&readPreference=secondaryPreferred&retryWrites=false";

		Datastore datastore = Morphia.createDatastore(MongoClients.create(docdb_uri), "demo");
        
        // Instruct Morphia, where to find your entity classes. 
        // Following line can be called multiple times with different packages or classes
        // can be called multiple times with different packages or classes
        datastore.getMapper().mapPackage("com.example.documentDb.model");
        datastore.ensureIndexes();
        return datastore;
    }

}
