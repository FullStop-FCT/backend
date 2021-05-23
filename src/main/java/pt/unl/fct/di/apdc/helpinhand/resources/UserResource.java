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
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Transaction;
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
	
	private Verification verifier = new Verification();

	public UserResource() {
		
	}
	

	@POST
	@Path("/insert") //register
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doRegister(UsersData userData) {
		LOG.warning("Attempt to register user " + userData.getUsername());
		
		if(! verifier.validRegistration(userData)) {
			LOG.warning("Failed verifier with a missing or wrong parameter.");
			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
		}
		
		try {
			Key userKey = database.getUserKey(userData.getUsername());
			Entity userEntity = txn.get(userKey);
			if(userEntity != null) {
				txn.rollback();
				LOG.warning("The user already exists");
				return Response.status(Status.BAD_REQUEST).entity("User already exists.").build();	
			}else {
			//Entity user = datastore.get(userKey);	
//				LOG.warning("DATA: " + userData.getEmail());
//				LOG.warning("DATA: " + userData.getPhoneNumber());
//				LOG.warning("DATA: " + userData.getMobileNumber());
//				LOG.warning("DATA: " + userData.getLocation());
//				LOG.warning("PROFILE: " + Profile.PRIVATE.toString());
//				LOG.warning("STATE: " + userData.getState());
//				LOG.warning("KIND: " + userData.getKind());
//				LOG.warning("ROLE: " + userData.getRole());
				
				userEntity = Entity.newBuilder(userKey)
						.set("user_name", userData.getName())
						.set("user_email", userData.getEmail())
						.set("user_pwd", DigestUtils.sha512Hex(userData.getPassword()))
						.set("user_phone_number", "")
						.set("user_mobile_number", "")
						.set("user_address", "")
//						.set("user_extra_address", "")
						.set("user_location", "")
						.set("user_postal_code", "")
						.set("user_gender", "")
						.set("user_birthday", "")
						.set("user_kind", "")
						.set("user_profile", Profile.PUBLIC.toString())
						.set("user_state", State.ENABLED.toString())
						.set("user_role", Roles.USER.toString())
						.set("user_creation_time", Timestamp.now())
						.set("last_time_modified", Timestamp.now())
						.build();
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
	
	
	
	//in here username is ONIF (O+NIF)
	@POST
	@Path("/insert_org")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doRegisterOrg(UsersData userData) {
		LOG.warning("Attempt to register org " + userData.getUsername());
		
		if(! verifier.validRegistration(userData)) {
			LOG.warning("Failed verifier with a missing or wrong parameter.");
			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
		}
		
		try {
			Key orgKey = database.getOrgKey(userData.getUsername());
			Entity orgEntity = txn.get(orgKey);
			if(orgEntity != null) {
				txn.rollback();
				LOG.warning("The org already exists");
				return Response.status(Status.BAD_REQUEST).entity("Org already exists.").build();	
			}else {
		
				orgEntity = Entity.newBuilder(orgKey)
						.set("user_name", userData.getName())
						.set("user_email", userData.getEmail())
						.set("user_pwd", DigestUtils.sha512Hex(userData.getPassword()))
						.set("user_phone_number", "")
						.set("user_mobile_number", "")
						.set("user_address", "")
//						.set("user_extra_address", "")
						.set("user_location", "")
						.set("user_postal_code", "")
//						.set("user_gender", "")
//						.set("user_birthday", "")
						.set("user_kind", Kinds.ORGANIZATION.toString())
						.set("user_profile", Profile.PUBLIC.toString())
						.set("user_state", State.ENABLED.toString())
						.set("user_role", Roles.USER.toString())
						.set("user_creation_time", Timestamp.now())
						.set("last_time_modified", Timestamp.now())
						.build();
				txn.add(orgEntity);
				LOG.warning("Org registered " + userData.getUsername());
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
			
			if(tokenEntity == null || System.currentTimeMillis() > request.getToken().getExpirationData()) {
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
				
				String address = request.getUserData().getAddress() != null && !request.getUserData().getAddress().isEmpty()
						? request.getUserData().getAddress() : userEntity.getString("user_address");
				
//				String extraAddress = request.getUserData().getExtraAddress() != null && !request.getUserData().getExtraAddress().isEmpty()
//						? request.getUserData().getExtraAddress() : userEntity.getString("user_extra_address");
				
				String location = request.getUserData().getLocation() != null &&  !request.getUserData().getLocation().isEmpty()
						? request.getUserData().getLocation() : userEntity.getString("user_location");
				
				String postalCode = request.getUserData().getPostalCode() !=null && !request.getUserData().getPostalCode().isEmpty()
						? request.getUserData().getPostalCode() : userEntity.getString("user_postalCode");
				
				String birthday = request.getUserData().getBirthday() !=null && !request.getUserData().getBirthday().isEmpty()
						? request.getUserData().getBirthday() : userEntity.getString("user_birthday");
				
				String gender = request.getUserData().getGender() !=null && !request.getUserData().getGender().isEmpty()
						? request.getUserData().getGender() : userEntity.getString("user_gender");
				
				String kind = request.getUserData().getKind() !=null && !request.getUserData().getKind().isEmpty()
						? request.getUserData().getKind() : userEntity.getString("user_kind");
				
				
				userEntity = Entity.newBuilder(datastore.get(userKey))
//						.set("user_email", email)
//						.set("user_pwd", userEntity.getString("user_pwd"))
//						.set("user_pwd", password)
						.set("user_profile", profile)
						.set("user_phone_number", phoneNumber)
						.set("user_mobile_number", mobileNumber)
						.set("user_address", address)
//						.set("user_extra_address", extraAddress)
						.set("user_location", location)
						.set("user_postal_code", postalCode)
						.set("user_birthday", birthday)
						.set("user_gender", gender)
						.set("user_kind", kind)
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

		
		try {
			Entity userEntity = txn.get(userKey);
			
			if(tokenEntity == null || System.currentTimeMillis()>token.getExpirationData()) {
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
			
//			Query<Entity> query = Query.newEntityQueryBuilder()
//					.setKind("User")
//					.build();
//			
//			QueryResults<Entity> results = datastore.run(query);
//
//			List<UsersData> users = new ArrayList<>();
//			
//			results.forEachRemaining(user -> {
				UsersData newUser = new UsersData();
				
				if(userEntity.getString("user_kind").equals(Kinds.ORGANIZATION.toString())) {

					newUser.setUsername(userEntity.getKey().getName());
					newUser.setName(userEntity.getString("user_name"));
					newUser.setEmail(userEntity.getString("user_email"));
					newUser.setProfile(userEntity.getString("user_profile"));
					newUser.setPhoneNumber(userEntity.getString("user_phone_number"));
					newUser.setMobileNumber(userEntity.getString("user_mobile_number"));
					newUser.setAddress(userEntity.getString("user_address"));
					newUser.setLocation(userEntity.getString("user_location"));
					newUser.setPostalCode(userEntity.getString("user_postal_code"));
//					users.add(newUser);
				}
				else {
				
				
				newUser.setUsername(userEntity.getKey().getName());
				newUser.setName(userEntity.getString("user_name"));
				newUser.setEmail(userEntity.getString("user_email"));
				newUser.setProfile(userEntity.getString("user_profile"));
				newUser.setPhoneNumber(userEntity.getString("user_phone_number"));
				newUser.setMobileNumber(userEntity.getString("user_mobile_number"));
				newUser.setAddress(userEntity.getString("user_address"));
				newUser.setLocation(userEntity.getString("user_location"));
				newUser.setPostalCode(userEntity.getString("user_postal_code"));
				newUser.setBirthday(userEntity.getString("user_birthday"));
				newUser.setGender(userEntity.getString("user_gender"));
				
//				users.add(newUser);
				}
				
//			});
			
//			Query<Entity> query = Query.newEntityQueryBuilder()
//					.setKind("User")
//					.
			
//			Query<Entity> query = Query.newEntityQueryBuilder()
//						.setKind("User")
//						.build();
//			query.newProjectionEntityQueryBuilder()
//			.addProjection("user_name", userEntity.getString("user_name"));
			
//			List<Entity> res = datastore.run(query);
//			
			
//			Query<Entity> query = Query.newEntityQueryBuilder()
//					.setKind("User")
//					.setFilter(PropertyFilter.eq(userEntity.getKey().toString(), username))
//					.build();
			
//			Query<ProjectionEntity> query = Query.newProjectionEntityQueryBuilder()
//					.setKind("User")
//					.setProjection(username, null)
			
//			UsersData newUser = new UsersData();
//			newUser.setUsername(userEntity.getKey().getId().toString());
//			newUser.setName(userEntity.getString("user_name"));
//			newUser.setEmail(userEntity.getString("user_email"));
//			newUser.setProfile(userEntity.getString("user_profile"));
//			newUser.setPhoneNumber(userEntity.getString("user_phone_number"));
//			newUser.setMobileNumber(userEntity.getString("user_mobile_number"));
//			newUser.setAddress(userEntity.getString("user_address"));
//			newUser.setLocation(userEntity.getString("user_location"));
//			newUser.setPostalCode(userEntity.getString("user_postal_code"));
//			newUser.setBirthday(userEntity.getString("user_birthday"));
//			newUser.setGender(userEntity.getString("user_gender"));
//			users.get(0);
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
	@Path("/user/{username}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetUserNoLogin(@PathParam("username") String username) {
		
		
		Transaction txn = datastore.newTransaction();
		Key userKey = database.getUserKey(username);
//		Key tokenKey = database.getTokenKey(token);
		
//		Entity tokenEntity = txn.get(tokenKey);
		
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
			
			UsersData newUser = new UsersData();
			
			if(userEntity.getString("user_kind").equals(Kinds.ORGANIZATION.toString())) {

				newUser.setUsername(userEntity.getKey().getName());
				newUser.setName(userEntity.getString("user_name"));
				newUser.setEmail(userEntity.getString("user_email"));
				newUser.setProfile(userEntity.getString("user_profile"));
				newUser.setPhoneNumber(userEntity.getString("user_phone_number"));
				newUser.setMobileNumber(userEntity.getString("user_mobile_number"));
				newUser.setAddress(userEntity.getString("user_address"));
				newUser.setLocation(userEntity.getString("user_location"));
				newUser.setPostalCode(userEntity.getString("user_postal_code"));
				
			}
			else {
			
			
			newUser.setUsername(userEntity.getKey().getName());
			newUser.setName(userEntity.getString("user_name"));
			newUser.setEmail(userEntity.getString("user_email"));
			newUser.setProfile(userEntity.getString("user_profile"));
			newUser.setPhoneNumber(userEntity.getString("user_phone_number"));
			newUser.setMobileNumber(userEntity.getString("user_mobile_number"));
			newUser.setAddress(userEntity.getString("user_address"));
			newUser.setLocation(userEntity.getString("user_location"));
			newUser.setPostalCode(userEntity.getString("user_postal_code"));
			newUser.setBirthday(userEntity.getString("user_birthday"));
			newUser.setGender(userEntity.getString("user_gender"));
			}
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
	
	
	
//	@POST
//	@Path("/insert") //register
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response doRegisterV2(User userData) {
//		LOG.warning("Attempt to register user " + userData.getUsername());
//		
//		if(! verifier.validRegistration(userData)) {
//			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
//		}
//		
//		try {
//			Key userKey = database.getUserKey(userData.getUsername());
//			Entity userEntity = txn.get(userKey);
//			if(userEntity != null) {
//				txn.rollback();
//				return Response.status(Status.BAD_REQUEST).entity("User already exists.").build();	
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
//						.set("user_location", userData.getLocation())
//						.set("user_role", "USER")
//						.set("user_state", "ENABLED")
//						.set("user_creation_time", Timestamp.now())
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
//			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//		}finally{
//			if(txn.isActive()) {
//				txn.rollback();
//				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//			}
//			
//		}	
//				
//	}

//	@POST
//	@Path("/insert") //register
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response doRegister(User userData) {
//		// TODO Auto-generated method stub
//		LOG.warning("Attempt to register user " + userData.getUsername());
//		
//		if(! userData.validRegistration()) {
//			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
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
//				return Response.status(Status.BAD_REQUEST).entity("User already exists.").build();	
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
//				return Response.ok(" {} ").build();
//			}
//			
//			
//		}catch(Exception e) {
//			txn.rollback();
//			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//		}finally{
//			if(txn.isActive()) {
//				txn.rollback();
//				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//			}
//			
//		}
//	}
	
//	@POST
//	@Path("/insertorg") //register
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response doRegisterOrg(UserOrg orgData) {
//		// TODO Auto-generated method stub
//		LOG.warning("Attempt to register Organization " + orgData.getUsername());
//		
//		if(! verifier.validRegistration(orgData)) {
//			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
//		}
//		
//		Transaction txn = datastore.newTransaction();
//
//		try {
//			Key userKey = datastore.newKeyFactory()
//					.setKind("User")
//					.newKey(orgData.getUsername());		
//			Entity userEntity = txn.get(userKey);
//
//			if(userEntity != null) {
//				txn.rollback();
//				return Response.status(Status.BAD_REQUEST).entity("Organization already exists.").build();	
//			}else {
//				
//			//Entity user = datastore.get(userKey);	
//				userEntity = Entity.newBuilder(userKey)
//						.set("user_email", orgData.getEmail())
//						.set("user_pwd", DigestUtils.sha512Hex(orgData.getPassword()))
//						.set("user_profile", orgData.getProfile())
//						.set("user_phone_number", orgData.getPhoneNumber())
//						.set("user_mobile_number", orgData.getMobileNumber())
//						.set("user_address", orgData.getAddress())
//						.set("user_extra_address", orgData.getExtraAddress())
//						.set("location", orgData.getLocation())
//						.set("nif", orgData.getNif())
//						.set("user_role", "USER")
//						.set("user_state", "ENABLED")
//						.set("user_creation_time", Timestamp.now())
//						.build();
//				txn.add(userEntity);
//				LOG.warning("User registered " + orgData.getUsername());
//				txn.commit();
//				
//				return Response.ok(" {} ").build();
//			}
//			
//			
//		}catch(Exception e) {
//			txn.rollback();
//			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//		}finally{
//			if(txn.isActive()) {
//				txn.rollback();
//				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//			}
//			
//		}
//	}



	
	

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

}
