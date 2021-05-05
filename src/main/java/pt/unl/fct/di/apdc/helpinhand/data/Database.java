package pt.unl.fct.di.apdc.helpinhand.data;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Transaction;

import pt.unl.fct.di.apdc.helpinhand.api.*;

public class Database {

private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	public Database() {
		
	}
	
	
}
