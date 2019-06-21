package com.ultradev.mongo.util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

@Component
public class MongoConnectionManager {

	private static Logger logger = Logger.getLogger(MongoConnectionManager.class);

	@Autowired
	private MongoConfiguration config;

	private MongoClient mongoClient = null;
	private Datastore dataStore = null;
	private Morphia morphia = null;

	@PostConstruct
	public void init() {
		getMongoClient();
		getMorphia();
		getDatastore();
	}

	private MongoClientOptions.Builder getMongoBuilder() {

		return MongoClientOptions.builder()
				// Logical name of the application. Set the pod/mS name to
				// capture details on server side
				.applicationName(config.getApplicationName())
				// To describe the mongo client which accessing the collections.
				.description(config.getDescription())
				// The maximum number of connections per pod.
				.connectionsPerHost(config.getMaxConnectionsPerHost())
				// The maximum number of connections during startup.
				.minConnectionsPerHost(config.getMinConnectionsPerHost())
				// The maximum idle time for a pooled connection. Given -1 to
				// specify no limit as the pool will have idle open connections
				// within max con count
				.maxConnectionIdleTime(config.getMaxConnectionIdleTime())
				// The maximum life time for a pooled connection. Given -1 to
				// specify no limit as the pool will up till the pod lifetime
				.maxConnectionLifeTime(config.getMaxConnectionLifeTime())
				// The write concern. By default ACK
				.writeConcern(config.getWriteConcern());

	}

	public MongoClient getMongoClient() {
		if (mongoClient == null) {
			logger.debug("Config:::::" + config);
			MongoClientURI uri = new MongoClientURI(config.getUri(), getMongoBuilder());
			try {
				mongoClient = new MongoClient(uri);
			} catch (MongoException me) {
				logger.error("Exception occurred in getMongoClient ", me);
			} catch (Exception e) {
				logger.error("Exception occurred in getMongoClient ", e);
			}

		}

		return mongoClient;
	}

	public Morphia getMorphia() {
		// Create a base object with _id and meta-data and use that across all
		// collections.
		return (morphia == null) ? new Morphia() : morphia;
		// .mapPackage(null)
	}

	public Datastore getDatastore() {
		return (dataStore == null) ? getMorphia().createDatastore(getMongoClient(), config.getDb()) : dataStore;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public void setDataStore(Datastore dataStore) {
		this.dataStore = dataStore;
	}

	public void setMorphia(Morphia morphia) {
		this.morphia = morphia;
	}

	@PreDestroy
	public void close() {
		logger.info("cleaning up resources");

		if (mongoClient != null) {
			try {
				mongoClient.close();
				setMongoClient(null);
				setMorphia(null);
				setDataStore(null);
			} catch (Exception e) {
				logger.error((e));
			}
		} else {
			logger.warn("could not clean up");
		}
	}

}