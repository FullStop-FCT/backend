package pt.unl.fct.di.apdc.helpinhand.data;

import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Transaction;

import pt.unl.fct.di.apdc.helpinhand.api.*;
import pt.unl.fct.di.apdc.helpinhand.util.Result;

public class Database {

	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	private static final Logger LOG = Logger.getLogger(Database.class.getName());
	
	
	public Database() {
		
	}
	
	
	
	public Key getUserKey(String username) {
		Key userKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(username);
		return userKey;
	}
	
//	public Key getOrgKey(String username) {
//		Key orgKey = datastore.newKeyFactory()
//				.setKind("Organization")
//				.newKey(username);
//		return orgKey;
//	}
	
	
//	public Key getOrgKey(String nif) {
//		Key orgKey = datastore.newKeyFactory()
//				.setKind("User")
//				.newKey(nif);
//		return orgKey;
//	}
	
	
//	public Key getTokenKey(AuthToken token) {
//		Key tokenKey = datastore.newKeyFactory()
//				.addAncestor(PathElement.of("User", token.getUsername()))
//				.setKind("Token")
//				.newKey(token.getTokenID());
//		
//		return tokenKey;
//	}
//	
	
	public Key getActivityKey(ActivitiesData data) {
		Key activityKey = datastore.allocateId(datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", data.getActivityOwner()))
				.setKind("Activity")
				.newKey()) ;
		
		return activityKey;
	}
	
	
//	public Key getRouteKey() {
//		Key routesKey = datastore.allocateId(datastore.newKeyFactory()
//				.setKind("Route")
//				.newKey());
//		
//		return routesKey;
//	}
//	
//	public Result<String> doRegister(User userData) throws WebApplicationException{
//		
//		LOG.warning("Attempt to register user " + userData.getUsername());
//		
//		if(! userData.validRegistration()) {
//			return Result.error(Result.ErrorCode.BAD_REQUEST);
////			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
//		}
//		
//		Transaction txn = datastore.newTransaction();
//
//		try {
//			Key userKey = datastore.newKeyFactory()
//					.setKind("User")
//					.newKey(userData.getUsername());		
//			Entity userEntity = txn.get(userKey);
//
//			if(userEntity != null) {
//				txn.rollback();
//				throw new WebApplicationException(Response.Status.BAD_REQUEST);
////				return Result.error(Result.ErrorCode.BAD_REQUEST);
////				return Response.status(Status.BAD_REQUEST).entity("User already exists.").build();	
//			}else {
//				
//			//Entity user = datastore.get(userKey);	
//				userEntity = Entity.newBuilder(userKey)
//						.set("user_email", userData.getEmail())
//						.set("user_pwd", DigestUtils.sha512Hex(userData.getPassword()))
//						.set("user_profile", userData.getProfile())
//						.set("user_phone_number", userData.getPhoneNumber())
//						.set("user_mobile_number", userData.getMobileNumber())
//						.set("user_address", userData.getAddress())
//						.set("user_extra_address", userData.getExtraAddress())
//						.set("location", userData.getLocation())
//						.set("user_role", "USER")
//						.set("user_state", "ENABLED")
//						.set("user_creation_time", Timestamp.now())
//						.build();
//				txn.add(userEntity);
//				LOG.warning("User registered " + userData.getUsername());
//				txn.commit();
//				
//				return Result.ok(" {} ");
////				return Response.ok(" {} ").build();
//			}
//			
//			
//		}catch(Exception e) {
//			txn.rollback();
//			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
////			return Result.error(Result.ErrorCode.INTERNAL_ERROR);
////			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//		}finally{
//			if(txn.isActive()) {
//				txn.rollback();
//				throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
////				return Result.error(Result.ErrorCode.INTERNAL_ERROR);
////				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//			}
//			
//		}
//		
//	}
	
}
