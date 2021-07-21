package pt.unl.fct.di.apdc.helpinhand.resources;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.KebabCaseStrategy;
import com.google.api.client.json.Json;
import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Cursor;
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
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import com.google.gson.Gson;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.ClickTrackingSetting;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.GoogleAnalyticsSetting;
import com.sendgrid.helpers.mail.objects.OpenTrackingSetting;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.sendgrid.helpers.mail.objects.TrackingSettings;

import pt.unl.fct.di.apdc.helpinhand.api.AuthToken;
import pt.unl.fct.di.apdc.helpinhand.api.Authorize;
import pt.unl.fct.di.apdc.helpinhand.api.RequestData;
import pt.unl.fct.di.apdc.helpinhand.api.Secured;
import pt.unl.fct.di.apdc.helpinhand.api.UsersData;
import pt.unl.fct.di.apdc.helpinhand.data.Database;
import pt.unl.fct.di.apdc.helpinhand.util.Profile;
import pt.unl.fct.di.apdc.helpinhand.util.Roles;
import pt.unl.fct.di.apdc.helpinhand.util.State;
import pt.unl.fct.di.apdc.helpinhand.util.Verification;

import org.json.*;




//*************************template
//LOG.warning("Attempt to confirm signup for user " + username);
//
//Transaction txn = datastore.newTransaction();
//
//Key userKey = database.getUserKey(username);
//
//try {
//	
//	
//	
//	txn.commit();
//	return Response.ok(" {} ").build(); 
//}catch(Exception e) {
//	txn.rollback();
//	LOG.warning("exception "+ e.toString());
//	return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
//}finally {
//	if(txn.isActive()) {
//		txn.rollback();
//		LOG.warning("entered finally");
//		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//	}
//}
//
//}
//***********************************



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
	
	@POST
	@Path("/insert") //register
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doRegister(UsersData userData) {
		LOG.warning("Attempt to register user " + userData.getUsername());
		
		if(! verifier.validRegistration(userData)) {
			LOG.warning("Failed verifier with a missing or wrong parameter.");
			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
		}
		
		if(verifier.existingUsername(userData.getUsername())) {
			LOG.warning("Username exists");
			return Response.status(Status.BAD_REQUEST).entity("username").build();
			
		}
		
		if(verifier.existingEmail(userData.getEmail())) {
			LOG.warning("Email exists");
			return Response.status(Status.BAD_REQUEST).entity("email").build();
		}
		
		try {
			Key userKey = database.getUserKey(userData.getUsername());


			Entity userEntity = txn.get(userKey);
			
			if(userEntity != null ) {
				txn.rollback();
				LOG.warning("The user already exists");
				return Response.status(Status.BAD_REQUEST).entity("User already exists.").build();	
			}else {
			
				
				if(!userData.isOrg()) {
					userEntity = createUserEntity(userData,userKey);
					
					Date now = new Date(System.currentTimeMillis());
					Date later = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
					
					Algorithm algorithm = Algorithm.HMAC512("secret");
					String jwtToken = JWT.create()
							.withClaim("role", userEntity.getString("user_role"))
							.withClaim("image", userEntity.getString("user_image"))
							.withIssuedAt(now)
							.withExpiresAt(later)
//							.withIssuedAt(Timestamp.now().toDate())
//							.withExpiresAt(token.getExpirationData().toDate())
							.withIssuer(userData.getUsername())
							.sign(algorithm);
					
					
					sendMail(userData.getEmail(), jwtToken);
					
					
				}else
					userEntity = createOrgEntity(userData,userKey);

				
				
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
	
	private String createMarketingList(String orgName) {
		
		String SENDGRID_API_KEY="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";
		
		
		SendGrid sg = new SendGrid(SENDGRID_API_KEY);
		
		Request request = new Request();
		
//		String name="";
		String id="";
		
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("/marketing/lists");
			request.setBody("{\"name\":\""+orgName+"\"}");
			request.addHeader("Authorization", "Bearer "+SENDGRID_API_KEY);
			
			com.sendgrid.Response response = sg.api(request);
			
			String string = response.getBody();
			
			JSONObject json = new JSONObject(string);
			
//			System.out.println(json.toString());
//			name = json.getString("name");
			id = json.getString("id");
			
//			System.out.println("name: "+ name);
//			System.out.println("id: " + id);
			
//			System.out.println(response.getStatusCode());
//			System.out.println(response.getBody());
//			System.out.println(response.getHeaders());
			
			
			
		}catch(IOException ex) {
			LOG.warning(ex.getMessage());
		}

		return id;
	}
	
	@GET
	@Path("/gridTest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doSendGridTest() {
		
		LOG.warning("Attempt to confirm signup for user ");
		createMarketingList("test");
		return Response.ok(" {} ").build();  
		
	}
	
	
	private void sendMail(String mailTo, String jwt) {
		
		
		LOG.warning(jwt);
		
		DecodedJWT jwtDecoded = JWT.decode(jwt);
		String username = jwtDecoded.getIssuer();
		
		Email from = new Email("HelpinHand@fullstop.website");

		Email to = new Email("fullstophh@gmail.com");

		Mail mail = new Mail();
		mail.setFrom(from);

		
		mail.setTemplateId("d-ee6fd5c218d84fd096b0d5d1f3265419");
		
		Personalization personalization = new Personalization();
		personalization.addDynamicTemplateData("username", username);
		personalization.addDynamicTemplateData("url", "http://localhost:3000/activeacc/"+jwt);
//		personalization.addDynamicTemplateData("url", "http://www.google.com");
		personalization.addTo(to);
		
		mail.addPersonalization(personalization);
	

		
		TrackingSettings trackingSettings = new TrackingSettings();
		ClickTrackingSetting clickTrackingSetting = new ClickTrackingSetting();
		clickTrackingSetting.setEnable(true);
		clickTrackingSetting.setEnableText(true);

		OpenTrackingSetting openTrackingSetting = new OpenTrackingSetting();
		openTrackingSetting.setEnable(true);
		
	
		
		GoogleAnalyticsSetting googleAnalyticsSetting = new GoogleAnalyticsSetting();
	    googleAnalyticsSetting.setEnable(true);
	    
		trackingSettings.setClickTrackingSetting(clickTrackingSetting);
		trackingSettings.setOpenTrackingSetting(openTrackingSetting);
	    trackingSettings.setGoogleAnalyticsSetting(googleAnalyticsSetting);
	    
	    
		mail.setTrackingSettings(trackingSettings);
		 
		Email replyTo = new Email();
	    replyTo.setName("Full Stop");
	    replyTo.setEmail("HelpinHand@fullstop.website");
	    mail.setReplyTo(replyTo);
		
		String SENDGRID_API_KEY="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";
			 
			 
			 
		SendGrid sg = new SendGrid(SENDGRID_API_KEY);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			request.addHeader("Authorization", "Bearer "+SENDGRID_API_KEY);
				  
				  
			sg.api(request);
				 
				  
				  
//				  com.sendgrid.Response response = sg.api(request);
//				  LOG.warning("status code: " +response.getStatusCode());
//				  System.out.println(response.getStatusCode());
//				  LOG.warning("body: " + response.getBody());
//				  
//				  System.out.println(response.getBody());
//				  
//				  LOG.warning("header : "+ response.getHeaders());
//				  System.out.println(response.getHeaders());
			} catch (IOException ex) {
				LOG.warning(ex.getMessage());
			}
	}
	
	
	@Authorize
	@POST
	@Path("/confirmSignup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doConfirmSignup(String username) {
		LOG.warning("Attempt to confirm signup for user " + username);
		
		Transaction txn = datastore.newTransaction();
		
		Key userKey = database.getUserKey(username);
		
		try {
			
			Entity userEntity = txn.get(userKey);
			
			
			if(userEntity == null) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			if(userEntity.getString("user_state").equals(State.ENABLED.toString())) {
				txn.rollback();
				LOG.warning("User already confirmed sign up");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			
			userEntity = Entity.newBuilder(userEntity)
					.set("user_state", StringValue.newBuilder(State.ENABLED.toString()).setExcludeFromIndexes(true).build())
					.build();
			
			txn.update(userEntity);
			
			
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
		.set("user_state", StringValue.newBuilder(State.DISABLED.toString()).setExcludeFromIndexes(true).build())
		.set("user_role", StringValue.newBuilder(Roles.USER.toString()).setExcludeFromIndexes(true).build())
		.set("user_creation_time", Timestamp.now())
		.set("last_time_modified", Timestamp.now())
		.set("is_org", false)
		.set("user_reports", LongValue.newBuilder(0).build())
			
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
		
		.set("user_reports", LongValue.newBuilder(0).build())
		.set("contact_list_id", createMarketingList(userKey.getName()))

		

		//.set("org_followers", LongValue.newBuilder(0).setExcludeFromIndexes(true).build())
//		.set("org_followers", ListValue.newBuilder().build())
		.set("user_followers", LongValue.newBuilder(0).build())
		.build();
	}

	@Authorize
	@PATCH
//	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doUpdate(@Context HttpHeaders header, RequestData request) {
		// TODO Auto-generated method stub
		
		String username = getUsername(header);
		
		LOG.warning("Attempt to update user: "+ username);
		
		
		
		
		Transaction txn = datastore.newTransaction();
	
		Key userKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(username);
		
//		Key tokenKey = datastore.newKeyFactory()
//				.addAncestor(PathElement.of("User", request.getToken().getUsername()))
//				.setKind("Token")
//				.newKey(request.getToken().getTokenID());
		try {
			
			
			Entity userEntity = txn.get(userKey);
			
			
			
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
				
//				LOG.warning("behind builder " + request.getUserData().getPhoneNumber());
				
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
				LOG.warning("Self User updated: " + username);
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
	
	
	
	@Authorize
	//@POST
	@GET
	@Path("/get/{username}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetUser(@PathParam("username") String username) {
		

		 
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
				newUser.setReports(userEntity.getLong("user_reports"));

				

				if(userEntity.contains("user_joined_activities") )
					newUser.setJoinedActivities(userEntity.getLong("user_joined_activities"));
			
				if(userEntity.contains("user_followers"))
					newUser.setFollowers(userEntity.getLong("user_followers"));
				if(userEntity.contains("user_birthday"))
					newUser.setBirthday(userEntity.getString("user_birthday"));
				if(userEntity.contains("user_gender"))
					newUser.setGender(userEntity.getString("user_gender"));
				
 
				if(userEntity.contains("contact_list_id"))
					newUser.setContactListId(userEntity.getString("contact_list_id"));

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
	
//	@Authorize
	@GET
	@Path("/self/{username}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetSelfUser(@PathParam("username") String username) {
		
		 
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
				newUser.setReports(userEntity.getLong("user_reports"));

				if(userEntity.contains("user_joined_activities") )
					newUser.setJoinedActivities(userEntity.getLong("user_joined_activities"));
//				if(userEntity.contains("created_activities"))
//					
//				if(userEntity.contains("user_following"))
//				
				if(userEntity.contains("user_followers"))
					newUser.setFollowers(userEntity.getLong("user_followers"));
				if(userEntity.contains("user_birthday"))
					newUser.setBirthday(userEntity.getString("user_birthday"));
				if(userEntity.contains("user_gender"))
					newUser.setGender(userEntity.getString("user_gender"));
 
				
				if(userEntity.contains("contact_list_id"))
					newUser.setContactListId(userEntity.getString("contact_list_id"));

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
	
	
	private String getUsername(HttpHeaders header) {
		
		String authHeaderVal = header.getHeaderString("Authorization");
		DecodedJWT jwtDecoded = JWT.decode(authHeaderVal.split(" ")[1]);
		String username = jwtDecoded.getIssuer();
		return username;
	}
	
	@Authorize
	@GET
	@Path("/user")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetUserNoLogin(@Context HttpHeaders header) {
		
		String username = getUsername(header);
		
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
			
			UsersData newUser = new UsersData();
			


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
			
			newUser.setReports(userEntity.getLong("user_reports"));

			if(userEntity.contains("user_joined_activities") )
				newUser.setJoinedActivities(userEntity.getLong("user_joined_activities"));
//			if(userEntity.contains("created_activities"))
//				
//			if(userEntity.contains("user_following"))
//			
			if(userEntity.contains("user_followers"))
				newUser.setFollowers(userEntity.getLong("user_followers"));
			if(userEntity.contains("user_birthday"))
				newUser.setBirthday(userEntity.getString("user_birthday"));
			if(userEntity.contains("user_gender"))
				newUser.setGender(userEntity.getString("user_gender"));
			
			if(userEntity.contains("contact_list_id"))
				newUser.setContactListId(userEntity.getString("contact_list_id"));
			
			
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
	
	
	
	private void addContact(String listID, String userEmail) {
		
		String SENDGRID_API_KEY="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";
		
		
		SendGrid sg = new SendGrid(SENDGRID_API_KEY);
		
		Request request = new Request();
		
		try {
			request.setMethod(Method.PUT);
			request.setEndpoint("/marketing/contacts");
			request.setBody("{\"list_ids\":[\""+listID+"\"],\"contacts\":[{\"email\":\""+userEmail+"\"}]}");
			request.addHeader("Authorization", "Bearer "+SENDGRID_API_KEY);
			
			com.sendgrid.Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
//			"{\"list_ids\":[\"string\"],\"contacts\":[{\"email\":\"string (required)\"}]}"
			
			
		}catch(IOException ex) {
			LOG.warning(ex.getMessage());
		}
		
	}
	
	
	@GET
	@Path("/contactTest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doContactTest() {
		
		LOG.warning("Attempt to add contact to list");
		addContact("1f91cfd6-14f6-4c58-bd46-4b350090e743","fokush@gmail.com");
		return Response.ok(" {} ").build();  
		
	}
	
	
	@Authorize
	@POST
	@Path("/follow/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doFollow(String userSelf, @PathParam("username") String username) {
		
//		String userSelf = getUsername(header);
		
		Transaction txn = datastore.newTransaction();
		
		
		try {
			

				
		
			Key followKey = factory
				.addAncestor(PathElement.of("User", userSelf))
				.setKind("Following")
				.newKey(username);
		
			Entity followEntity = txn.get(followKey);
		

		

			if(followEntity !=null) {
				txn.rollback();
				LOG.warning("This follow already exists");
				return Response.status(Status.BAD_REQUEST).entity("Follow already exists.").build();
			}

		
			Key selfUserKey = database.getUserKey(userSelf);
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
				.set("follower", userSelf)
				.set("following", username)
				.build();
		

			txn.add(followEntity);
			
			
//			addContact(targetEntity.getString("contact_list_id"),selfEntity.getString("user_email"));
			
		
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
	
	
	
	@Authorize
	@GET
	@Path("/user/hours")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetUserHours() {
	 

		 
		Transaction txn = datastore.newTransaction();
				

			try {

				
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
	
	@Authorize
	@GET
	@Path("/listorg")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListOrgs() {
		 	Transaction txn = datastore.newTransaction();
			

			try {

			
				Query<Entity> query = Query.newEntityQueryBuilder()
						.setKind("User")
						.setFilter(
								StructuredQuery.PropertyFilter.eq("is_org", true))
//						.setOrderBy(OrderBy.desc("user_hours"))
						.setOrderBy(OrderBy.desc("created_activities"))
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
					nextUser.setCreatedActivities(user.getLong("created_activities"));
					nextUser.setImage(user.getString("user_image"));
					nextUser.setOrg(user.getBoolean("is_org"));
					
					nextUser.setReports(user.getLong("user_reports"));
					
					nextUser.setContactListId(user.getString("contact_list_id"));
					
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
	
	
	
	private void removeContact(String listID, String userEmail) {
			
		String SENDGRID_API_KEY="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";
		
		
		SendGrid sg = new SendGrid(SENDGRID_API_KEY);
		
		Request request = new Request();
		
		try {
			request.setMethod(Method.PUT);
			request.setEndpoint("/marketing/contacts");
			request.setBody("{\"list_ids\":[\""+listID+"\"],\"contacts\":[{\"email\":\""+userEmail+"\"}]}");
			request.addHeader("Authorization", "Bearer "+SENDGRID_API_KEY);
			
			com.sendgrid.Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
//			"{\"list_ids\":[\"string\"],\"contacts\":[{\"email\":\"string (required)\"}]}"
			
			
		}catch(IOException ex) {
			LOG.warning(ex.getMessage());
		}
		
	}
	
	
	@Authorize
	@POST
	@Path("/unfollow/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doUnfollow(String userSelf, @PathParam("username") String username) {
		
//		String userSelf = getUsername(header);
		
		Transaction txn = datastore.newTransaction();
		

		
		try {
			

		
		
		Key followKey = factory
				.addAncestors(PathElement.of("User", userSelf))
				.setKind("Following")
				.newKey(username);
		
		Entity followEntity = txn.get(followKey);
		

		

		if(followEntity ==null) {
			txn.rollback();
			LOG.warning("This follow doesnt exists");
			return Response.status(Status.BAD_REQUEST).entity("Follow doesnt exists.").build();
		}

		
		Key selfUserKey = database.getUserKey(userSelf);
		Key targetUserKey = database.getUserKey(username);
		
		Entity selfEntity = txn.get(selfUserKey);
		Entity targetEntity = txn.get(targetUserKey);
		
		long followings = selfEntity.getLong("user_following")-1;
		long followers = targetEntity.getLong("user_followers")-1; 
		
		Entity newSelf = Entity.newBuilder(selfEntity)
				.set("user_following", followings)
				.build();
		
		Entity newTarget = Entity.newBuilder(targetEntity)
				.set("user_followers", followers)
				.build();
		
		txn.update(newSelf, newTarget);
		

		LOG.warning("follow on user " + username + " deleted");

//		followEntity = Entity.newBuilder(followKey)
//				.set("follower", token.getUsername())
////				.set("following", token.getUsername())
//				.build();
		
		txn.delete(followKey);
		
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
	
	@Authorize
	@GET
	@Path("/isfollowing/{username}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doIsFollowing(@Context HttpHeaders header, @PathParam("username") String username) {
		
		String userSelf = getUsername(header);
		
		Transaction txn = datastore.newTransaction();
		

		try {

			
			Query <Entity> query = Query.newEntityQueryBuilder()
					.setKind("Following")
					.setFilter(
//							PropertyFilter.gt("__key__", factory.setKind("Following").newKey(username)))
//							PropertyFilter.gt("__key__",factory.newKey(username)))
							CompositeFilter.and(
									PropertyFilter.hasAncestor(factory.setKind("User").newKey(userSelf)),
									PropertyFilter.eq("following", username)
									)
							)
//											factory.setKind("User").addAncestors(PathElement.of("User", token.getUsername()), PathElement.of("User", username)).newKey(username)), 
//							PropertyFilter.eq("follower", token.getUsername())
//							))
					.build();
			
			QueryResults<Entity> followingsQuery = datastore.run(query);
			
			
//			List<String> users = new ArrayList<>(); 
			
			
			boolean isFollowing=false;
			
			
			
			if(followingsQuery.hasNext())
				isFollowing=true;
				
			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(isFollowing)).build();	
				
				

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
	
	
	
	@Authorize
	@GET
	@Path("/get/followings")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetFollowings(@Context HttpHeaders header) {
		
		
		String username = getUsername(header);
		
		Transaction txn = datastore.newTransaction();
		

		try {

			
			Query <Entity> query = Query.newEntityQueryBuilder()
					.setKind("Following")
					.setFilter(
							PropertyFilter.eq("follower", username))
					.build();
			
			QueryResults<Entity> followingsQuery = datastore.run(query);
			
			
			List<String> users = new ArrayList<>(); 
			
			followingsQuery.forEachRemaining(user->{
				String nextUser;
				nextUser = user.getKey().getName();
				users.add(nextUser);
			});
				
				
			txn.commit();
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
	
	
	
	@Authorize
	@GET
	@Path("/listusers")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListUsers() {
		 	Transaction txn = datastore.newTransaction();
			

//		 	int pageSize;
//		 	Cursor pageCursor;
//		 	
			try {
				
//				pageSize = 25;
			
				Query<Entity> query = Query.newEntityQueryBuilder()
						.setKind("User")
						.setFilter(
								CompositeFilter.and(
										PropertyFilter.eq("is_org", false),
										PropertyFilter.eq("user_role", Roles.USER.toString())
//										PropertyFilter.eq("user_image", "default.png")
										)
								)
						.setLimit(30)
						.build();
				
				QueryResults<Entity> rankingQuery = datastore.run(query);

				List<UsersData> users = new ArrayList<>(); 
				
				rankingQuery.forEachRemaining(user -> {
					UsersData nextUser = new UsersData();
					
					nextUser.setUsername(user.getKey().getName());				
//					nextUser.setReports(user.getLong("user_reports"));
								
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
	
	

	
	//need autentication
	@Authorize
	@POST
	@Path("/test")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	//@Secured({Roles.GBO})
	public Response doTest() {
		
		return Response.ok("{}").build();
	}
	
	
	
	//ALL DEPRECATED ----------------------------------------------------------------------------------------------------------------------------------
	
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
	
	

	public Response doRoleChange(RequestData request) {
		// TODO Auto-generated method stub
		return null;
	}


	public Response doStateChange(RequestData request) {
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
