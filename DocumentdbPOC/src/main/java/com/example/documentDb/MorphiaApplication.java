package com.example.documentDb;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import dev.morphia.Datastore;
import dev.morphia.Morphia;

@SpringBootApplication(exclude = { MongoAutoConfiguration.class, })
public class MorphiaApplication {

	String docdb_uri = "mongodb://docdbpocuser:Skierns203!78@docdb-poc.cluster-cresmi2847mm.us-east-1.docdb.amazonaws.com:27017/?replicaSet=rs0&readPreference=secondaryPreferred&retryWrites=false";

	public static void main(String[] args) {
		SpringApplication.run(MorphiaApplication.class, args);

	}

	@Bean
	public Datastore morphiaSetUp() {
		//
		// setSslProperties();
		// A datastore instance for establishing a connection to Amazon DoocumentDB

		Datastore datastore = Morphia.createDatastore(MongoClients.create(docdb_uri), "demo");

		// Instruct Morphia, where to find your entity classes.
		// Following line can be called multiple times with different packages or
		// classes
		// can be called multiple times with different packages or classes
		datastore.getMapper().mapPackage("com.example.documentDb.model");
		datastore.ensureIndexes();
		MongoCursor<String> collections = datastore.getDatabase().listCollectionNames().cursor();
		while(collections.hasNext()) {
			System.out.println("Morphia Collection Name"+ collections.next());
		}
		return datastore;
	}

	@Bean
	public MongoDatabase mongoDriverSetUp() {
		ConnectionString connectionString = new ConnectionString(docdb_uri);

		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());

		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.codecRegistry(codecRegistry).build();
		MongoClient mongoClient = MongoClients.create(clientSettings);
		MongoDatabase testDB = mongoClient.getDatabase("demo");
		
		MongoCursor<String> collections = testDB.listCollectionNames().cursor();
		while(collections.hasNext()) {
			System.out.println("Mongo Collection Name"+ collections.next());
		}
		
		return testDB;
	}

}
