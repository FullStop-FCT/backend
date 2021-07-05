package pt.unl.fct.di.apdc.helpinhand.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.LongValue;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.StructuredQuery;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import com.google.gson.Gson;

import pt.unl.fct.di.apdc.helpinhand.api.AuthToken;
import pt.unl.fct.di.apdc.helpinhand.api.Request;
import pt.unl.fct.di.apdc.helpinhand.api.UsersData;
import pt.unl.fct.di.apdc.helpinhand.api.UserOrg;
//import pt.unl.fct.di.apdc.helpinhand.api.service.RestUsers;
import pt.unl.fct.di.apdc.helpinhand.data.Database;
import pt.unl.fct.di.apdc.helpinhand.util.Profile;
import pt.unl.fct.di.apdc.helpinhand.util.Roles;
import pt.unl.fct.di.apdc.helpinhand.util.State;
import pt.unl.fct.di.apdc.helpinhand.util.Kinds;
import pt.unl.fct.di.apdc.helpinhand.util.Verification;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserResource{ 
	
	Database database = new Database();
	

	private static final Logger LOG = Logger.getLogger(UserResource.class.getName());
	
	
	private static final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
	public String timestamp;
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	private final Gson g = new Gson();
	
	private Transaction txn = datastore.newTransaction();
	private KeyFactory factory = datastore.newKeyFactory();
	
	private Verification verifier = new Verification();

	public UserResource() {
		
	}
	

	
	//updated with nonIndexedParameters
//	@POST
//	@Path("/insert") //register
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response doRegister(UsersData userData) {
//		LOG.warning("Attempt to register user " + userData.getUsername());
//		
//		if(! verifier.validRegistration(userData)) {
//			LOG.warning("Failed verifier with a missing or wrong parameter.");
//			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
//		}
//		
//		if(verifier.existingEmail(userData.getEmail())) {
//			LOG.warning("Email exists");
//			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
//		}
//		
//		try {
//			Key userKey = database.getUserKey(userData.getUsername());
//			Entity userEntity = txn.get(userKey);
//			if(userEntity != null ) {
//				txn.rollback();
//				LOG.warning("The user already exists");
//				return Response.status(Status.BAD_REQUEST).entity("User already exists.").build();	
//			}else {
//			
//				
//				userEntity = Entity.newBuilder(userKey)
//						.set("user_name", userData.getName())
//						.set("user_email", userData.getEmail())
//						.set("user_pwd", StringValue.newBuilder(DigestUtils.sha512Hex(userData.getPassword())).setExcludeFromIndexes(true).build())
//						.set("user_phone_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_mobile_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//
//						.set("user_location", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//
//						.set("user_gender", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_birthday", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//
//						.set("user_image", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_hours", userData.getHoursDone())
//						
//						.set("user_following", ListValue.newBuilder().build()) //followed orgs
//						.set("user_participated_activities", ListValue.newBuilder().build())
//						.set("user_own_activities", ListValue.newBuilder().build())
////						.set("activities_created", LongValue.newBuilder(activities).setExcludeFromIndexes(true).build() )
//						
//						.set("user_profile", StringValue.newBuilder(Profile.PUBLIC.toString()).setExcludeFromIndexes(true).build())
//						.set("user_state", StringValue.newBuilder(State.ENABLED.toString()).setExcludeFromIndexes(true).build())
//						.set("user_role", StringValue.newBuilder(Roles.USER.toString()).setExcludeFromIndexes(true).build())
//						.set("user_creation_time", Timestamp.now())
//						.set("last_time_modified", Timestamp.now())
//						.build();
//				txn.add(userEntity);
//				LOG.warning("User registered " + userData.getUsername());
//				txn.commit();
//				
//				return Response.ok(" {} ").build();
//			}
//			
//			
//		}catch(Exception e) {
//			txn.rollback();
//			LOG.warning("Something went wrong entered exception e: " + e.toString());
//			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//		}finally{
//			if(txn.isActive()) {
//				txn.rollback();
//				LOG.warning("Something went wrong entered finally.");
//				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//			}
//			
//		}	
//				
//	}
	
	
	@POST
	@Path("/insert") //register
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doRegister(UsersData userData) {
		LOG.warning("Attempt to register user " + userData.getUsername());
		
		if(! verifier.validRegistration(userData)) {
			LOG.warning("Failed verifier with a missing or wrong parameter.");
			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
		}
		
		if(verifier.existingEmail(userData.getEmail())) {
			LOG.warning("Email exists");
			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
		}
		
		try {
			Key userKey = database.getUserKey(userData.getUsername());
//			Key userKey = datastore.newKeyFactory()
//					.addAncestor(PathElement.of("Parent", userData.getUsername()))
//					.setKind("Supporter")
//					.newKey(userData.getUsername());

			Entity userEntity = txn.get(userKey);
			
			if(userEntity != null ) {
				txn.rollback();
				LOG.warning("The user already exists");
				return Response.status(Status.BAD_REQUEST).entity("User already exists.").build();	
			}else {
			
				
				if(!userData.isOrg()) {
					userEntity = createUserEntity(userData,userKey);
				}else
					userEntity = createOrgEntity(userData,userKey);

//				userEntity = Entity.newBuilder(userKey)
//						.set("user_name", userData.getName())
//						.set("user_email", userData.getEmail())
//						.set("user_pwd", StringValue.newBuilder(DigestUtils.sha512Hex(userData.getPassword())).setExcludeFromIndexes(true).build())
//						.set("user_phone_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_mobile_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_location", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_image", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_following", ListValue.newBuilder().build()) //followed orgs
//						.set("user_own_activities", ListValue.newBuilder().build())
//						.set("user_profile", StringValue.newBuilder(Profile.PUBLIC.toString()).setExcludeFromIndexes(true).build())
//						.set("user_state", StringValue.newBuilder(State.ENABLED.toString()).setExcludeFromIndexes(true).build())
//						.set("user_role", StringValue.newBuilder(Roles.USER.toString()).setExcludeFromIndexes(true).build())
//						.set("user_creation_time", Timestamp.now())
//						.set("last_time_modified", Timestamp.now())
//						
//
//						.set("user_gender", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_birthday", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_hours", userData.getHoursDone())
//						.set("user_participated_activities", ListValue.newBuilder().build())
//
////						.set("activities_created", LongValue.newBuilder(activities).setExcludeFromIndexes(true).build() )
//						
//						
//						.build();
				
				
				txn.add(userEntity);
				LOG.warning("User registered " + userData.getUsername());
				txn.commit();
				
				return Response.ok(" {} ").build();
			}
			
			
		}catch(Exception e) {
			txn.rollback();
			LOG.warning("Something went wrong entered exception e: " + e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}finally{
			if(txn.isActive()) {
				txn.rollback();
				LOG.warning("Something went wrong entered finally.");
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
			
		}	
				
	}
	
	
	private Entity createUserEntity(UsersData userData, Key userKey) {
		return Entity.newBuilder(userKey)
		.set("user_name", userData.getName())
		.set("user_email", userData.getEmail())
		.set("user_pwd", StringValue.newBuilder(DigestUtils.sha512Hex(userData.getPassword())).setExcludeFromIndexes(true).build())
		.set("user_phone_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
		.set("user_mobile_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
		.set("user_location", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
		.set("user_image", StringValue.newBuilder("default.png").setExcludeFromIndexes(true).build())
//		.set("user_following", ListValue.newBuilder().build()) //followed orgs //LONG incrementar e decrementar metodo
//		.set("created_activities", ListValue.newBuilder().build())
		.set("user_following", LongValue.newBuilder(0).build()) //followed orgs //LONG incrementar e decrementar metodo
		.set("created_activities", LongValue.newBuilder(0).build())
		.set("user_profile", StringValue.newBuilder(Profile.PUBLIC.toString()).setExcludeFromIndexes(true).build())
		.set("user_state", StringValue.newBuilder(State.ENABLED.toString()).setExcludeFromIndexes(true).build())
		.set("user_role", StringValue.newBuilder(Roles.USER.toString()).setExcludeFromIndexes(true).build())
		.set("user_creation_time", Timestamp.now())
		.set("last_time_modified", Timestamp.now())
		.set("is_org", false)

		
		.set("user_followers", LongValue.newBuilder(0).build())
		.set("user_gender", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
		.set("user_birthday", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
		.set("user_hours", userData.getHoursDone())
//		.set("user_joined_activities", ListValue.newBuilder().build())
		.set("user_joined_activities", LongValue.newBuilder(0).build())
		.build();
	}
	
	
	private Entity createOrgEntity(UsersData userData, Key userKey) {
		return Entity.newBuilder(userKey)
		.set("user_name", userData.getName())
		.set("user_email", userData.getEmail())
		.set("user_pwd", StringValue.newBuilder(DigestUtils.sha512Hex(userData.getPassword())).setExcludeFromIndexes(true).build())
		.set("user_phone_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
		.set("user_mobile_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
		.set("user_location", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
		.set("user_image", StringValue.newBuilder("default.png").setExcludeFromIndexes(true).build())
//		.set("user_following", ListValue.newBuilder().build()) //followed orgs
//		.set("org_followers", ListValue.newBuilder().build())
//		.set("created_activities", ListValue.newBuilder().build())
		.set("user_following", LongValue.newBuilder(0).build()) //followed orgs //LONG incrementar e decrementar metodo
		.set("created_activities", LongValue.newBuilder(0).build())
		.set("user_profile", StringValue.newBuilder(Profile.PUBLIC.toString()).setExcludeFromIndexes(true).build())
		.set("user_state", StringValue.newBuilder(State.ENABLED.toString()).setExcludeFromIndexes(true).build())
		.set("user_role", StringValue.newBuilder(Roles.USER.toString()).setExcludeFromIndexes(true).build())
		.set("user_creation_time", Timestamp.now())
		.set("last_time_modified", Timestamp.now())
		.set("is_org", true)
		

		//.set("org_followers", LongValue.newBuilder(0).setExcludeFromIndexes(true).build())
//		.set("org_followers", ListValue.newBuilder().build())
		.set("user_followers", LongValue.newBuilder(0).build())
		.build();
	}
	
//	@POST
//	@Path("/insertOrg") //register
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response doRegisterOrg(UsersData userData) {
//		LOG.warning("Attempt to register user " + userData.getUsername());
//		
////		if(! verifier.validRegistration(userData)) {
////			LOG.warning("Failed verifier with a missing or wrong parameter.");
////			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
////		}
//		
////		if(verifier.existingEmail(userData.getEmail())) {
////			LOG.warning("Email exists");
////			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
////		}
//		
//		try {
//			Key orgKey = database.getOrgKey(userData.getUsername());
//			Entity orgEntity = txn.get(orgKey);
//			if(orgEntity != null ) {
//				txn.rollback();
//				LOG.warning("The Organization already exists");
//				return Response.status(Status.BAD_REQUEST).entity("Organization already exists.").build();	
//			}else {
//			
//				
//				orgEntity = Entity.newBuilder(orgKey)
//						.set("org_name", userData.getName())
//						.set("org_email", userData.getEmail())
//						.set("org_pwd", StringValue.newBuilder(DigestUtils.sha512Hex(userData.getPassword())).setExcludeFromIndexes(true).build())
//						.set("org_phone_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("org_mobile_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//
//						.set("org_location", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//
////						.set("user_gender", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
////						.set("user_birthday", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//
//						.set("org_image", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
////						.set("user_hours", userData.getHoursDone())
//						
//						.set("org_following", ListValue.newBuilder().build()) //followed orgs
//						.set("org_followers", LongValue.newBuilder(0).setExcludeFromIndexes(true).build())
////						.set("user_activities", ListValue.newBuilder().build())
//						.set("org_own_activities", ListValue.newBuilder().build())
////						.set("activities_created", LongValue.newBuilder(activities).setExcludeFromIndexes(true).build() )
//						
//						.set("org_profile", StringValue.newBuilder(Profile.PUBLIC.toString()).setExcludeFromIndexes(true).build())
//						.set("org_state", StringValue.newBuilder(State.ENABLED.toString()).setExcludeFromIndexes(true).build())
//						.set("org_role", StringValue.newBuilder(Roles.USER.toString()).setExcludeFromIndexes(true).build())
//						.set("org_creation_time", Timestamp.now())
//						.set("last_time_modified", Timestamp.now())
//						.build();
//				txn.add(orgEntity);
//				LOG.warning("Organization registered " + userData.getUsername());
//				txn.commit();
//				
//				return Response.ok(" {} ").build();
//			}
//			
//			
//		}catch(Exception e) {
//			txn.rollback();
//			LOG.warning("Something went wrong entered exception e: " + e.toString());
//			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//		}finally{
//			if(txn.isActive()) {
//				txn.rollback();
//				LOG.warning("Something went wrong entered finally.");
//				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//			}
//			
//		}	
//				
//	}
	
	
//	//in here username is ONIF (O+NIF)
//	@POST
//	@Path("/insert_org")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response doRegisterOrg(UsersData userData) {
//		LOG.warning("Attempt to register org " + userData.getUsername());
//		
//		if(! verifier.validRegistration(userData)) {
//			LOG.warning("Failed verifier with a missing or wrong parameter.");
//			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
//		}
//		
//		if(verifier.existingEmail(userData.getEmail())) {
//			LOG.warning("Email exists");
//			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
//		}
//		
//		try {
//			Key orgKey = database.getOrgKey(userData.getUsername());
//			Entity orgEntity = txn.get(orgKey);
//			if(orgEntity != null) {
//				txn.rollback();
//				LOG.warning("The org already exists");
//				return Response.status(Status.BAD_REQUEST).entity("Org already exists.").build();	
//			}else {
//		
//
//				
//				orgEntity = Entity.newBuilder(orgKey)
//						.set("user_name", userData.getName())
//						.set("user_email", userData.getEmail())
//						.set("user_pwd", StringValue.newBuilder(DigestUtils.sha512Hex(userData.getPassword())).setExcludeFromIndexes(true).build())
//						.set("user_phone_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_mobile_number", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//
//						.set("user_location", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//
//						.set("user_gender", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_birthday", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//
//						.set("user_image", StringValue.newBuilder("").setExcludeFromIndexes(true).build())
//						.set("user_hours", userData.getHoursDone())
//						
//						.set("user_following", ListValue.newBuilder().build()) //followed orgs
//						.set("user_activities", ListValue.newBuilder().setExcludeFromIndexes(true).build())
//						.set("created_activities", ListValue.newBuilder().build())
////						.set("activities_created", activities )
//						
//						.set("user_profile", StringValue.newBuilder(Profile.PUBLIC.toString()).setExcludeFromIndexes(true).build())
//						.set("user_state", StringValue.newBuilder(State.ENABLED.toString()).setExcludeFromIndexes(true).build())
//						.set("user_role", StringValue.newBuilder(Roles.USER.toString()).setExcludeFromIndexes(true).build())
//						.set("user_creation_time", Timestamp.now())
//						.set("last_time_modified", Timestamp.now())
//
//
//
//						.build();
//				txn.add(orgEntity);
//				LOG.warning("Org registered " + userData.getUsername());
//				txn.commit();
//				
//				return Response.ok(" {} ").build();
//			}
//			
//			
//		}catch(Exception e) {
//			txn.rollback();
//			LOG.warning("Something went wrong entered exception e: " + e.toString());
//			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//		}finally{
//			if(txn.isActive()) {
//				txn.rollback();
//				LOG.warning("Something went wrong entered finally.");
//				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//			}
//			
//		}	
//	}
	
	
	@PATCH
//	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doUpdate(Request request) {
		// TODO Auto-generated method stub
		LOG.warning("Attempt to update user: "+ request.getToken().getUsername());
		
		Transaction txn = datastore.newTransaction();
		Key userKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(request.getToken().getUsername());
		Key tokenKey = datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", request.getToken().getUsername()))
				.setKind("Token")
				.newKey(request.getToken().getTokenID());
		try {
			 
			Entity userEntity = txn.get(userKey);
			Entity tokenEntity = txn.get(tokenKey);
			
//			if(tokenEntity == null || System.currentTimeMillis() > request.getToken().getExpirationData()) {
//				txn.rollback();
//				LOG.warning("Token Authentication Failed");
//				return Response.status(Status.FORBIDDEN).build();
//			}
			if(tokenEntity == null) {
				txn.rollback();
				LOG.warning("Token Authentication Failed");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			if(userEntity.getString("user_role").equals(Roles.USER.toString()) && (!userEntity.getString("user_state").equals(State.DELETED.toString()) 
					|| !userEntity.getString("user_state").equals(State.DISABLED.toString())) ) {
				
//				String email= request.getUserData().validateEmail() ? request.getUserData().getEmail() : userEntity.getString("user_email");
	
//				String email= verifier.validateEmail(request.getUserData().getEmail()) ? request.getUserData().getEmail() : userEntity.getString("user_email");
				
//				String password = data.getUserData().validatePassword() ? data.getUserData().getPassword() : userEntity.getString("user_password");
				
//				String password = data.getUserData().validatePassword() ? DigestUtils.sha512Hex(data.getUserData().getPassword()) : userEntity.getString("user_pwd");
				
				String profile = request.getUserData().getProfile()!=null && !request.getUserData().getProfile().isEmpty() 
						? request.getUserData().getProfile() : userEntity.getString("user_profile");
				
				String phoneNumber = request.getUserData().getPhoneNumber() != null && !request.getUserData().getPhoneNumber().isEmpty()
						? request.getUserData().getPhoneNumber() : userEntity.getString("user_phone_number");
				
				String mobileNumber = request.getUserData().getMobileNumber() != null && !request.getUserData().getMobileNumber().isEmpty()
						? request.getUserData().getMobileNumber() : userEntity.getString("user_mobile_number");
				
//				String address = request.getUserData().getAddress() != null && !request.getUserData().getAddress().isEmpty()
//						? request.getUserData().getAddress() : userEntity.getString("user_address");
				
//				String extraAddress = request.getUserData().getExtraAddress() != null && !request.getUserData().getExtraAddress().isEmpty()
//						? request.getUserData().getExtraAddress() : userEntity.getString("user_extra_address");
				
				String location = request.getUserData().getLocation() != null &&  !request.getUserData().getLocation().isEmpty()
						? request.getUserData().getLocation() : userEntity.getString("user_location");
				
//				String postalCode = request.getUserData().getPostalCode() !=null && !request.getUserData().getPostalCode().isEmpty()
//						? request.getUserData().getPostalCode() : userEntity.getString("user_postalCode");
				
				String birthday = request.getUserData().getBirthday() !=null && !request.getUserData().getBirthday().isEmpty()
						? request.getUserData().getBirthday() : userEntity.getString("user_birthday");
				
				String gender = request.getUserData().getGender() !=null && !request.getUserData().getGender().isEmpty()
						? request.getUserData().getGender() : userEntity.getString("user_gender");
				
				
				String image = request.getUserData().getImage() !=null && !request.getUserData().getImage().isEmpty()
						? request.getUserData().getImage() : userEntity.getString("user_image");
				
				userEntity = Entity.newBuilder(datastore.get(userKey))
//						.set("user_email", email)
//						.set("user_pwd", userEntity.getString("user_pwd"))
//						.set("user_pwd", password)
						.set("user_profile", profile)
						.set("user_phone_number", phoneNumber)
						.set("user_mobile_number", mobileNumber)
//						.set("user_address", address)
//						.set("user_extra_address", extraAddress)
						.set("user_location", location)
//						.set("user_postal_code", postalCode)
						.set("user_birthday", birthday)
						.set("user_gender", gender)
						.set("user_image", image)
						.set("last_time_modified", Timestamp.now())
//						.set("user_role", userEntity.getString("user_role"))
//						.set("user_state", userEntity.getString("user_state"))
//						.set("user_creation_time", userEntity.getTimestamp("user_creation_time"))
						.build();
										
				txn.update(userEntity);
				LOG.warning("Self User updated: " + request.getToken().getUsername());
				txn.commit();
				return Response.ok(" {} ").build(); 
				}
			
			
			
		}catch(Exception e) {
			txn.rollback();
			LOG.warning("exception "+ e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
		}finally {
			if(txn.isActive()) {
				txn.rollback();
				LOG.warning("entered finally");
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		LOG.warning("Failed update attempt for username: " + request.getToken().getUsername());
		return Response.status(Status.BAD_REQUEST).entity("ups").build();
	}
	
	
	@POST
	@Path("/get/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetUser(AuthToken token, @PathParam("username") String username) {
		
		 
		Transaction txn = datastore.newTransaction();
		
		Key tokenKey = database.getTokenKey(token);
		
		Entity tokenEntity = txn.get(tokenKey);
		
		Key userKey = database.getUserKey(username);

//		Key userKey = datastore.newKeyFactory()
//				.addAncestor(PathElement.of("Parent", username))
//				.setKind("Supporter")
//				.newKey(username);

		
		try {
			Entity userEntity = txn.get(userKey);
			
//			if(tokenEntity == null || System.currentTimeMillis()>token.getExpirationData()) {
//				txn.rollback();
//				LOG.warning("Token Authentication Failed");
//				return Response.status(Status.FORBIDDEN).build();
//			}
			if(tokenEntity == null) {
				txn.rollback();
				LOG.warning("Token Authentication Failed");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			if(userEntity == null) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			if(!userEntity.getString("user_state").equals(State.ENABLED.toString())) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			if(userEntity.getString("user_profile").equals(Profile.PRIVATE.toString())) {
				UsersData newUser = new UsersData();
				
				newUser.setUsername(userEntity.getKey().getName());
				newUser.setName(userEntity.getString("user_name"));
				newUser.setImage(userEntity.getString("user_image"));
				
				txn.commit();
				return Response.status(Status.OK).entity(g.toJson(newUser)).build();
			}

			
				UsersData newUser = new UsersData();
				//List<com.google.cloud.datastore.Value<?>> list = userEntity.contains("user_activities") ? userEntity.getList("user_activities") : new List;
				

				
				newUser.setUsername(userEntity.getKey().getName());
				newUser.setName(userEntity.getString("user_name"));
				newUser.setEmail(userEntity.getString("user_email"));
				newUser.setProfile(userEntity.getString("user_profile"));
				newUser.setPhoneNumber(userEntity.getString("user_phone_number"));
				newUser.setMobileNumber(userEntity.getString("user_mobile_number"));
				newUser.setLocation(userEntity.getString("user_location"));
				newUser.setFollowings(userEntity.getLong("user_following"));
				
				newUser.setImage(userEntity.getString("user_image"));
				newUser.setOrg(userEntity.getBoolean("is_org"));
				newUser.setCreatedActivities(userEntity.getLong("created_activities"));
				

				if(userEntity.contains("user_joined_activities") )
					newUser.setJoinedActivities(userEntity.getLong("user_joined_activities"));
			
				if(userEntity.contains("org_followers"))
					newUser.setFollowers(userEntity.getLong("org_followers"));
				if(userEntity.contains("user_birthday"))
					newUser.setBirthday(userEntity.getString("user_birthday"));
				if(userEntity.contains("user_gender"))
					newUser.setGender(userEntity.getString("user_gender"));
 

			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(newUser)).build();
			
			
		}catch(Exception e) {
			txn.rollback();
			LOG.warning("exception "+ e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
		}finally {
			if(txn.isActive()) {
				txn.rollback();
				LOG.warning("entered finally");
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		
	}
	
	
	@GET
	@Path("/self/{username}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetUser(@PathParam("username") String username) {
		
		 
		Transaction txn = datastore.newTransaction();
		
//		Key tokenKey = database.getTokenKey(token);
		
//		Entity tokenEntity = txn.get(tokenKey);
		
		Key userKey = database.getUserKey(username);

//		Key userKey = datastore.newKeyFactory()
//				.addAncestor(PathElement.of("Parent", username))
//				.setKind("Supporter")
//				.newKey(username);

		
		try {
			Entity userEntity = txn.get(userKey);
			
//			if(tokenEntity == null || System.currentTimeMillis()>token.getExpirationData()) {
//				txn.rollback();
//				LOG.warning("Token Authentication Failed");
//				return Response.status(Status.FORBIDDEN).build();
//			}
//			if(tokenEntity == null) {
//				txn.rollback();
//				LOG.warning("Token Authentication Failed");
//				return Response.status(Status.FORBIDDEN).build();
//			}
//			
			if(userEntity == null) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			if(!userEntity.getString("user_state").equals(State.ENABLED.toString())) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			if(userEntity.getString("user_profile").equals(Profile.PRIVATE.toString())) {
				UsersData newUser = new UsersData();
				
				newUser.setUsername(userEntity.getKey().getName());
				newUser.setName(userEntity.getString("user_name"));
				newUser.setImage(userEntity.getString("user_image"));
				
				txn.commit();
				return Response.status(Status.OK).entity(g.toJson(newUser)).build();
			}

			
				UsersData newUser = new UsersData();
				//List<com.google.cloud.datastore.Value<?>> list = userEntity.contains("user_activities") ? userEntity.getList("user_activities") : new List;
				

				
				newUser.setUsername(userEntity.getKey().getName());
				newUser.setName(userEntity.getString("user_name"));
				newUser.setEmail(userEntity.getString("user_email"));
				newUser.setProfile(userEntity.getString("user_profile"));
				newUser.setPhoneNumber(userEntity.getString("user_phone_number"));
				newUser.setMobileNumber(userEntity.getString("user_mobile_number"));
				newUser.setLocation(userEntity.getString("user_location"));
				newUser.setFollowings(userEntity.getLong("user_following"));
				
				newUser.setImage(userEntity.getString("user_image"));
				newUser.setOrg(userEntity.getBoolean("is_org"));
				newUser.setCreatedActivities(userEntity.getLong("created_activities"));
				

				if(userEntity.contains("user_joined_activities") )
					newUser.setJoinedActivities(userEntity.getLong("user_joined_activities"));
//				if(userEntity.contains("created_activities"))
//					
//				if(userEntity.contains("user_following"))
//				
				if(userEntity.contains("org_followers"))
					newUser.setFollowers(userEntity.getLong("org_followers"));
				if(userEntity.contains("user_birthday"))
					newUser.setBirthday(userEntity.getString("user_birthday"));
				if(userEntity.contains("user_gender"))
					newUser.setGender(userEntity.getString("user_gender"));
 

			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(newUser)).build();
			
			
		}catch(Exception e) {
			txn.rollback();
			LOG.warning("exception "+ e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
		}finally {
			if(txn.isActive()) {
				txn.rollback();
				LOG.warning("entered finally");
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		
	}
	
	
	
	
	
	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetUserNoLogin(AuthToken token) {
		
		
		Transaction txn = datastore.newTransaction();
		
		Key tokenKey = database.getTokenKey(token);
		
		Entity tokenEntity = txn.get(tokenKey);
		
		Key userKey = database.getUserKey(token.getUsername());	
		
		
		try {
				
			Entity userEntity = txn.get(userKey);
			
//			if(tokenEntity == null || System.currentTimeMillis()>token.getExpirationData()) {
//			txn.rollback();
//			LOG.warning("Token Authentication Failed");
//			return Response.status(Status.FORBIDDEN).build();
//		}
			if(tokenEntity == null) {
				txn.rollback();
				LOG.warning("Token Authentication Failed");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			if(userEntity == null) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			if(!userEntity.getString("user_state").equals(State.ENABLED.toString())) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			UsersData newUser = new UsersData();
			
//			newUser.setUsername(userEntity.getKey().getName());
//			newUser.setName(userEntity.getString("user_name"));
//			newUser.setEmail(userEntity.getString("user_email"));
//			newUser.setProfile(userEntity.getString("user_profile"));
//			newUser.setPhoneNumber(userEntity.getString("user_phone_number"));
//			newUser.setMobileNumber(userEntity.getString("user_mobile_number"));
//			newUser.setLocation(userEntity.getString("user_location"));
//			newUser.setBirthday(userEntity.getString("user_birthday"));
//			newUser.setGender(userEntity.getString("user_gender"));
//			newUser.setImage(userEntity.getString("user_image"));
//			newUser.setImage(userEntity.getString("user_hours"));
//			newUser.setJoinedActivities(userEntity.getList("user_activities"));
//			newUser.setCreatedActivities(userEntity.getList("created_activities"));
//			newUser.setFollowings(userEntity.getList("user_following"));

			newUser.setUsername(userEntity.getKey().getName());
			newUser.setName(userEntity.getString("user_name"));
			newUser.setEmail(userEntity.getString("user_email"));
			newUser.setProfile(userEntity.getString("user_profile"));
			newUser.setPhoneNumber(userEntity.getString("user_phone_number"));
			newUser.setMobileNumber(userEntity.getString("user_mobile_number"));
			newUser.setLocation(userEntity.getString("user_location"));
			newUser.setFollowings(userEntity.getLong("user_following"));
			
			newUser.setImage(userEntity.getString("user_image"));
			newUser.setOrg(userEntity.getBoolean("is_org"));
			newUser.setCreatedActivities(userEntity.getLong("created_activities"));
			

			if(userEntity.contains("user_joined_activities") )
				newUser.setJoinedActivities(userEntity.getLong("user_joined_activities"));
//			if(userEntity.contains("created_activities"))
//				
//			if(userEntity.contains("user_following"))
//			
			if(userEntity.contains("org_followers"))
				newUser.setFollowers(userEntity.getLong("org_followers"));
			if(userEntity.contains("user_birthday"))
				newUser.setBirthday(userEntity.getString("user_birthday"));
			if(userEntity.contains("user_gender"))
				newUser.setGender(userEntity.getString("user_gender"));
			

			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(newUser)).build();
			
			
		}catch(Exception e) {
			txn.rollback();
			LOG.warning("exception "+ e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
		}finally {
			if(txn.isActive()) {
				txn.rollback();
				LOG.warning("entered finally");
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	
	
	@POST
	@Path("/follow/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doFollow(AuthToken token, @PathParam("username") String username) {
		Transaction txn = datastore.newTransaction();
		
		Key tokenKey = database.getTokenKey(token);
		
		Entity tokenEntity = txn.get(tokenKey);
		try {
			
//			if(tokenEntity == null || System.currentTimeMillis()>token.getExpirationData()) {
//			txn.rollback();
//			LOG.warning("Token Authentication Failed");
//			return Response.status(Status.FORBIDDEN).build();
//		}
		
		if(tokenEntity == null) {
			txn.rollback();
			LOG.warning("Token Authentication Failed");
			return Response.status(Status.FORBIDDEN).build();
		}
		
		
		Key followKey = factory
				.addAncestors(PathElement.of("User", token.getUsername()),PathElement.of("User", username))
				.setKind("Following")
				.newKey(username);
		
		Entity followEntity = txn.get(followKey);
		

		

		if(followEntity !=null) {
			txn.rollback();
			LOG.warning("This follow already exists");
			return Response.status(Status.BAD_REQUEST).entity("Follow already exists.").build();
		}

		
		Key selfUserKey = database.getUserKey(token.getUsername());
		Key targetUserKey = database.getUserKey(username);
		
		Entity selfEntity = txn.get(selfUserKey);
		Entity targetEntity = txn.get(targetUserKey);
		
		long followings = selfEntity.getLong("user_following")+1;
		long followers = targetEntity.getLong("user_followers")+1; 
		
		Entity newSelf = Entity.newBuilder(selfEntity)
				.set("user_following", followings)
				.build();
		
		Entity newTarget = Entity.newBuilder(targetEntity)
				.set("user_followers", followers)
				.build();
		
		txn.update(newSelf, newTarget);
		

		LOG.warning("follow on user " + username + " registered");

		followEntity = Entity.newBuilder(followKey)
				.set("follower", token.getUsername())
//				.set("following", token.getUsername())
				.build();
		

		txn.add(followEntity);
		
		txn.commit();
		return Response.ok(" {} ").build();

		}catch(Exception e) {
			txn.rollback();
			LOG.warning("exception "+ e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
		}finally {
			if(txn.isActive()) {
				txn.rollback();
				LOG.warning("entered finally");
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}

		
	}

	

	 @POST
	 @Path("/user/hours")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response doGetUserHours(AuthToken token) {
		 
		 Transaction txn = datastore.newTransaction();
				
			Key tokenKey = database.getTokenKey(token);
			
			Entity tokenEntity = txn.get(tokenKey);
			try {
//				if(tokenEntity == null || System.currentTimeMillis()>token.getExpirationData()) {
//					txn.rollback();
//					LOG.warning("Token Authentication Failed");
//					return Response.status(Status.FORBIDDEN).build();
//				}
				
				if(tokenEntity == null) {
					txn.rollback();
					LOG.warning("Token Authentication Failed");
					return Response.status(Status.FORBIDDEN).build();
				}
				
				Query<Entity> query = Query.newEntityQueryBuilder()
						.setKind("User")
						.setOrderBy(OrderBy.desc("user_hours"))
						.setLimit(25)
						.build();
				
				
				QueryResults<Entity> hoursQuery = datastore.run(query);
				
				List<UsersData> users = new ArrayList<>(); 
				
				
				hoursQuery.forEachRemaining(user -> {
					
					UsersData nextUser = new UsersData();
					nextUser.setUsername(user.getKey().getName());
					nextUser.setName(user.getString("user_name"));
					nextUser.setHoursDone(user.getLong("user_hours"));
					
					users.add(nextUser);
					
				});
				
				txn.commit();
//				return Response.ok(" {} ").build();
				return Response.status(Status.OK).entity(g.toJson(users)).build();
				
			}catch(Exception e) {
				txn.rollback();
				LOG.warning("exception "+ e.toString());
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
			}finally {
				if(txn.isActive()) {
					txn.rollback();
					LOG.warning("entered finally");
					return Response.status(Status.INTERNAL_SERVER_ERROR).build();
				}
			}
		 
	 }
	
	
	
	@POST
	@Path("/listorg")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListOrgs(AuthToken token) {
		 	Transaction txn = datastore.newTransaction();
			
			Key tokenKey = database.getTokenKey(token);
			
			Entity tokenEntity = txn.get(tokenKey);
			try {
//				if(tokenEntity == null || System.currentTimeMillis()>token.getExpirationData()) {
//				txn.rollback();
//				LOG.warning("Token Authentication Failed");
//				return Response.status(Status.FORBIDDEN).build();
//			}
			
				if(tokenEntity == null) {
					txn.rollback();
					LOG.warning("Token Authentication Failed");
					return Response.status(Status.FORBIDDEN).build();
				}
			
				Query<Entity> query = Query.newEntityQueryBuilder()
						.setKind("User")
						.setFilter(
								StructuredQuery.PropertyFilter.eq("is_org", true))
//						.setOrderBy(OrderBy.desc("user_hours"))
						.setLimit(25)
						.build();
				
				QueryResults<Entity> rankingQuery = datastore.run(query);

				List<UsersData> users = new ArrayList<>(); 
				
				rankingQuery.forEachRemaining(user -> {
					UsersData nextUser = new UsersData();
					
					nextUser.setUsername(user.getKey().getName());
					nextUser.setName(user.getString("user_name"));
					nextUser.setEmail(user.getString("user_email"));
					nextUser.setProfile(user.getString("user_profile"));
					nextUser.setPhoneNumber(user.getString("user_phone_number"));
					nextUser.setMobileNumber(user.getString("user_mobile_number"));
					nextUser.setLocation(user.getString("user_location"));
					nextUser.setFollowings(user.getLong("user_following"));
					
					nextUser.setImage(user.getString("user_image"));
					nextUser.setOrg(user.getBoolean("is_org"));
					
					users.add(nextUser);
					
				});
			
				txn.commit();
//				return Response.ok(" {} ").build();
				return Response.status(Status.OK).entity(g.toJson(users)).build();
				
			}catch(Exception e) {
				txn.rollback();
				LOG.warning("exception "+ e.toString());
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
			}finally {
				if(txn.isActive()) {
					txn.rollback();
					LOG.warning("entered finally");
					return Response.status(Status.INTERNAL_SERVER_ERROR).build();
				}
			}
	}
	
	
	
	
	
	
	@Deprecated
	@PATCH
	@Path("/addpoint/{username}/{points}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doAddPoints(@PathParam("username") String username, @PathParam("points") long points) {
		
		Transaction txn = datastore.newTransaction();
		Key userKey = database.getUserKey(username);
		
		try {
			
			
			Entity userEntity = txn.get(userKey);
			
			if(userEntity == null) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			if(!userEntity.getString("user_state").equals(State.ENABLED.toString())) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			userEntity = Entity.newBuilder(datastore.get(userKey))
					.set("user_points", userEntity.getLong("user_points")+points)
					.set("last_time_modified", Timestamp.now())
					.build();
			
			txn.update(userEntity);
			LOG.warning("Points added to User : " + username);
			txn.commit();
			return Response.ok(" {} ").build(); 
			
		}catch(Exception e) {
			txn.rollback();
			LOG.warning("exception "+ e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
		}finally {
			if(txn.isActive()) {
				txn.rollback();
				LOG.warning("entered finally");
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		
	}
	
	
//	@GET
//	@Path("/ranking")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response doRanking() {
//		Transaction txn = datastore.newTransaction();
//		
//		try {
//			Query<Entity> query = Query.newEntityQueryBuilder()
//					.setKind("User")
//					.setFilter(
//							StructuredQuery.PropertyFilter.ge("user_points", 0)
//							)
//					.setOrderBy(OrderBy.desc("user_points"))
//					.setLimit(5)
//					.build();
//			
//			QueryResults<Entity> rankingQuery = datastore.run(query);
//			
//			
//			
//			List<UsersData> users = new ArrayList<>();
//			
//			rankingQuery.forEachRemaining(user -> {
//				UsersData nextUser = new UsersData();
//				nextUser.setUsername(user.getKey().getName());
////				long newPoints;
////				newPoints = user.getLong("user_points")+0;
////				nextUser.setPoints(newPoints);
////				nextUser.setPoints(user.getLong("user_points"));
//				
//				users.add(nextUser);
//			});
//			
//			txn.commit();
//			return Response.status(Status.OK).entity(g.toJson(users)).build();
//			
//		}catch(Exception e) {
//			txn.rollback();
//			LOG.warning("exception "+ e.toString());
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
//		}finally {
//			if(txn.isActive()) {
//				txn.rollback();
//				LOG.warning("entered finally");
//				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//			}
//		}
//	}
//	
//	
	
	
	
	


	
	

	public Response doRoleChange(Request request) {
		// TODO Auto-generated method stub
		return null;
	}


	public Response doStateChange(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	//put?
//	@POST
//	@Path("/delete")
//	//@Consumes ??
//	public Response doDelete(Request request);
	
	
	//put? even maybe pAtch
//	@POST
//	@Path("/update")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response doUpdate(Request request);
//	
//	
//	@POST //also put/patch ?
//	@Path("/role/change")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response doRoleChange(Request request);
//	
//	@POST //also put/patch '
//	@Path("/state/change")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response doStateChange(Request request);
	
	
	//change password
	//show attributes
	//show other public online users
	
	//for gbo
	//show all  other users , profile and rolees
	//show all other user, profile, and roles that are logged
	//show a user given the attribute
	//show all users given a role
	//show all accounts created betwen x and y
	
	//for ga
	//remove or disable/enable a account of a user or gbo
	//all other options for gbo
	
	
	// get/{user}
	//addpoints
	//adicionar mais atributos no get
	

}
