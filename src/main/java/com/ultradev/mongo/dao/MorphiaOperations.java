package com.ultradev.mongo.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ultradev.mongo.model.Account;
import com.ultradev.mongo.util.MongoConnectionManager;

@Component
public class MorphiaOperations {
	@Autowired
	MongoConnectionManager mongoConnectionManager;
	Logger log=LoggerFactory.getLogger(MorphiaOperations.class);

	public void performMorphiaCrud() {
		saveAccounts("ultradev");
		updateAccounts("ultradev", "ultradevnew");
		deleteExisting("ultradevtmp");
	}
	
	private void saveAccounts(String userName)
	{
		Account accounts = getDummyAccount(userName);
		mongoConnectionManager.getDatastore().save(accounts);
		log.info(" saved "+userName+" successfully ");
		
		
		
	}

	private void updateAccounts(String userName,String newUserName)
	{
		Account accounts = getDummyAccount(userName);
		mongoConnectionManager.getDatastore().save(accounts);
		UpdateOperations<Account> updateOperation=mongoConnectionManager.getDatastore().createUpdateOperations(Account.class).set("user", newUserName);
		mongoConnectionManager.getDatastore().update(mongoConnectionManager.getDatastore().find(Account.class).field("user").equalIgnoreCase("ultradev"), updateOperation);
		log.info(" updated "+userName+" to "+newUserName);
		
	}
	private void deleteExisting(String userName)
	{
		
		// insertnew and delete it
		
		saveAccounts(userName);
		mongoConnectionManager.getDatastore().delete(mongoConnectionManager.getDatastore().find(Account.class).filter("user", userName));
		log.info(" deleted "+userName+" successfully ");
	}
	
	
	private Account getDummyAccount(String userName) {
		Account account = new Account();
		account.setAmount(500.0);
		account.setCreatedDate(System.currentTimeMillis());
		account.setId(new ObjectId());
		account.setUser(userName);
		return account;

	}
}
