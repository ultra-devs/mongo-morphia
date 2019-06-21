package com.ultradev.mongo.util;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.mongodb.WriteConcern;

/**
 * @author sm211q
 *
 */
@Configuration
//@ConfigurationProperties(prefix = "mongo.config")
@ConfigurationProperties(prefix = "mongo.localconfig")
public class MongoConfiguration {

	private String description;

	private String applicationName;

	private int maxConnectionsPerHost;
	
	private int minConnectionsPerHost;

	private int maxConnectionIdleTime;

	private int maxConnectionLifeTime;

	@Max(3)
	@Min(-1)
	private int writeConcern;

	private String uri;
	
	private String db;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public int getMaxConnectionIdleTime() {
		return maxConnectionIdleTime;
	}

	public void setMaxConnectionIdleTime(int maxConnectionIdleTime) {
		this.maxConnectionIdleTime = maxConnectionIdleTime;
	}

	public int getMaxConnectionLifeTime() {
		return maxConnectionLifeTime;
	}

	public void setMaxConnectionLifeTime(int maxConnectionLifeTime) {
		this.maxConnectionLifeTime = maxConnectionLifeTime;
	}

	public WriteConcern getWriteConcern() {
		switch (writeConcern) {
		case 0:
			return WriteConcern.UNACKNOWLEDGED;
		case 1:
			return WriteConcern.W1;
		case 2:
			return WriteConcern.W2;
		case 3:
			return WriteConcern.W3;
		case -1:
			return WriteConcern.ACKNOWLEDGED;
		default:
			return WriteConcern.ACKNOWLEDGED;

		}

	}

	public void setGetWriteConcern(int writeConcern) {
		this.writeConcern = writeConcern;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public int getMinConnectionsPerHost() {
		return minConnectionsPerHost;
	}

	public void setMinConnectionsPerHost(int minConnectionsPerHost) {
		this.minConnectionsPerHost = minConnectionsPerHost;
	}

}