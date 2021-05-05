package pt.unl.fct.di.apdc.helpinhand.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.helpinhand.api.Request;
import pt.unl.fct.di.apdc.helpinhand.api.User;
//import pt.unl.fct.di.apdc.helpinhand.api.service.RestUsers;
import pt.unl.fct.di.apdc.helpinhand.data.Database;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserResource{ 
	
	Database data;
	

	private static final Logger LOG = Logger.getLogger(UserResource.class.getName());
	
	
	private static final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
	public String timestamp;
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	private final Gson g = new Gson();
	

	public UserResource() {
//		data = new Database();
	}
	

	


	@POST
	@Path("/insert") //register
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doRegister(User userData) {
		// TODO Auto-generated method stub
		LOG.warning("Attempt to register user " + userData.getUsername());
		
		if(! userData.validRegistration()) {
			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
		}
		
		Transaction txn = datastore.newTransaction();

		try {
			Key userKey = datastore.newKeyFactory()
					.setKind("User")
					.newKey(userData.getUsername());		
			Entity userEntity = txn.get(userKey);

			if(userEntity != null) {
				txn.rollback();
				return Response.status(Status.BAD_REQUEST).entity("User already exists.").build();	
			}else {
				
			//Entity user = datastore.get(userKey);	
				userEntity = Entity.newBuilder(userKey)
						.set("user_email", userData.getEmail())
						.set("user_pwd", DigestUtils.sha512Hex(userData.getPassword()))
						.set("user_profile", userData.getProfile())
						.set("user_phone_number", userData.getPhoneNumber())
						.set("user_mobile_number", userData.getMobileNumber())
						.set("user_address", userData.getAddress())
						.set("user_extra_address", userData.getExtraAddress())
						.set("location", userData.getLocation())
						.set("user_role", "USER")
						.set("user_state", "ENABLED")
						.set("user_creation_time", Timestamp.now())
						.build();
				txn.add(userEntity);
				LOG.warning("User registered " + userData.getUsername());
				txn.commit();
				
				return Response.ok(" {} ").build();
			}
			
			
		}catch(Exception e) {
			txn.rollback();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}finally{
			if(txn.isActive()) {
				txn.rollback();
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
		LOG.warning("Attempt to update user: "+ request.getUserData().getUsername());
		
		Transaction txn = datastore.newTransaction();
		Key userKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(request.token.getUsername());
		Key tokenKey = datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", request.token.getUsername()))
				.setKind("Token")
				.newKey(request.token.getTokenID());
		try {
			
			Entity userEntity = txn.get(userKey);
			Entity tokenEntity = txn.get(tokenKey);
			
			if(tokenEntity == null || System.currentTimeMillis() > request.token.getExpirationData()) {
				txn.rollback();
				LOG.warning("Token Authentication Failed");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			
			if(userEntity.getString("user_role").equals("USER") && (!userEntity.getString("user_state").equals("DELETED") 
					|| !userEntity.getString("user_state").equals("DISABLED")) ) {
				
				String email= request.getUserData().validateEmail() ? request.getUserData().getEmail() : userEntity.getString("user_email");
				
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
				
				String extraAddress = request.getUserData().getExtraAddress() != null && !request.getUserData().getExtraAddress().isEmpty()
						? request.getUserData().getExtraAddress() : userEntity.getString("user_extra_address");
				
				String location = request.getUserData().getLocation() != null &&  !request.getUserData().getLocation().isEmpty()
						? request.getUserData().getLocation() : userEntity.getString("user_location");
				
				userEntity = Entity.newBuilder(datastore.get(userKey))
						.set("user_email", email)
//						.set("user_pwd", userEntity.getString("user_pwd"))
//						.set("user_pwd", password)
						.set("user_profile", profile)
						.set("user_phone_number", phoneNumber)
						.set("user_mobile_number", mobileNumber)
						.set("user_address", address)
						.set("user_extra_address", extraAddress)
						.set("location", location)
//						.set("user_role", userEntity.getString("user_role"))
//						.set("user_state", userEntity.getString("user_state"))
//						.set("user_creation_time", userEntity.getTimestamp("user_creation_time"))
						.build();
										
				txn.update(userEntity);
				LOG.warning("Self User updated: " + request.getUserData().getUsername());
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
		LOG.warning("Failed update attempt for username: " + request.getUserData().getUsername());
		return Response.status(Status.BAD_REQUEST).entity("ups").build();
	}


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
