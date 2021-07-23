package pt.unl.fct.di.apdc.helpinhand.resources;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.digest.DigestUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.StructuredQuery;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import io.github.cdimascio.dotenv.Dotenv;
import pt.unl.fct.di.apdc.helpinhand.api.AuthToken;
import pt.unl.fct.di.apdc.helpinhand.api.RequestData;



@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AuthenticationResource {

	
	private static final Logger LOG = Logger.getLogger(AuthenticationResource.class.getName());
	
	private static final String SECRET="f7b038a6-f515-4516-93f0-ffa59c7fd00e";
	
	private final Gson g = new Gson();
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	public static final long EXPIRATION_TIME = 1000*60;
	
	public AuthenticationResource() {
		
	}
	
	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doLoginJWT(RequestData request) {
		
		LOG.warning("WARNING: Login atempt by user: " + request.getUsername());
		
		Key userKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(request.getUsername());
		
		
			
			
			Transaction txn = datastore.newTransaction();
			
			try {
				Entity user = txn.get(userKey);
				
				if(user == null) {
					txn.rollback();
					LOG.warning("Failed login attempt");
					return Response.status(Status.FORBIDDEN).entity("inputerror").build();
				}
				
				if(user.getString("user_state").equals("DELETED") || user.getString("user_state").equals("DISABLED")) {
					txn.rollback();
					LOG.warning("Failed login attempt");
					return Response.status(Status.FORBIDDEN).entity("email").build();
				}
				
				String hashedPWD = user.getString("user_pwd");
				if(hashedPWD.equals(DigestUtils.sha512Hex(request.getPassword()))) {
		
//					String token = issueToken(request.getUsername(), 1);
					
//					AuthToken token = new AuthToken(request.getUsername(),user.getString("user_role"));
					
					
//					Key tokenKey = datastore.allocateId(
//							datastore.newKeyFactory()
//								.addAncestor(PathElement.of("User", request.getUsername()))
//								.setKind("JWTToken")
//								.newKey()
//							);
					
//					String encoded = Base64.getEncoder().encodeToString(token.getTokenID().getBytes());
					
					Date now = new Date(System.currentTimeMillis());
//					Date late = newDate(Timestamp.now() + TimeUnit.HOURS.toMillis(1));
					Date later = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));

					
//					Dotenv dotenv = Dotenv.load();
//
//					String secret = dotenv.get("SECRET");
					
					Algorithm algorithm = Algorithm.HMAC512(SECRET);
					String jwtToken = JWT.create()
							.withClaim("role", user.getString("user_role"))
							.withClaim("image", user.getString("user_image"))
							.withIssuedAt(now)
							.withExpiresAt(later)
//							.withIssuedAt(Timestamp.now().toDate())
//							.withExpiresAt(token.getExpirationData().toDate())
							.withIssuer(request.getUsername())
							.sign(algorithm);
					
//					Entity tokenEntity = Entity.newBuilder(tokenKey)
//							.set("issuer", request.getUsername())
//							.set("role", user.getString("user_role"))
//							.set("image", user.getString("user_image"))
//							.set("creationData",now.toString() )
//							.set("expirationData", later.toString())
//							.set("secret","secret")
//							.build();
//					
//					txn.put(tokenEntity);
					txn.commit();
					
					return Response.ok(jwtToken).build();
				
				
				}
				else {
					txn.rollback();
					LOG.warning("Wrong password for username: " + request.getUsername());
					return Response.status(Status.FORBIDDEN).entity("inputerror").build();
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
			
	
		
	}
	
	
	@POST
	@Path("/secretLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doSecretLogin(RequestData request) {
		
		LOG.warning("WARNING: Login atempt by user: " + request.getUsername());
		
		Key staffKey = datastore.newKeyFactory()
				.setKind("Staff")
				.newKey(request.getUsername());
		
		
			
			
			Transaction txn = datastore.newTransaction();
			
			try {
				Entity staff = txn.get(staffKey);
				
				if(staff == null) {
					txn.rollback();
					LOG.warning("Failed login attempt");
					return Response.status(Status.FORBIDDEN).entity("Failed login attempt").build();
				}
				

				
				String hashedPWD = staff.getString("staff_pwd");
				if(hashedPWD.equals(DigestUtils.sha512Hex(request.getPassword()))) {
		

					Date now = new Date(System.currentTimeMillis());

					Date later = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));

					
					Algorithm algorithm = Algorithm.HMAC512(SECRET);
					String jwtToken = JWT.create()
							.withClaim("role", staff.getString("staff_role"))
							.withIssuedAt(now)
							.withExpiresAt(later)
							.withIssuer(request.getUsername())
							.sign(algorithm);
					

					txn.commit();
					
					return Response.ok(jwtToken).build();
				
				
				}
				else {
					txn.rollback();
					LOG.warning("Wrong password for staff: " + request.getUsername());
					return Response.status(Status.FORBIDDEN).entity("Wrong password or username").build();
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
			
	
		
	}
	
	
	
	
	
//	private String issueToken(String login, int expiration, AuthToken token) {
//		
//		try {
//			Algorithm algorithm = Algorithm.HMAC512(token.getTokenID());
//			
//			String jwtToken = JWT.create()
//					.withClaim("role", token.getRole())
////					.withIssuedAt(Timestamp.now().toDate())
////					.withExpiresAt(token.getExpirationData().toDate())
//					.withIssuer(login)
//					.sign(algorithm);
//		}catch(JWTCreationException exception) {
//			
//		}
//
//		
//	}
	
	
//	@POST
//	@Path("/login")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
////	public Response doLogin(Request request, 
////			@Context HttpServletRequest req, 
////			@Context HttpHeaders headers) {
//		public Response doLogin(Request request
//				) {
//		LOG.warning("WARNING: Login atempt by user: " + request.getUsername());
//		
//		Key userKey = datastore.newKeyFactory()
//				.setKind("User")
//				.newKey(request.getUsername());
//		
//		//added
////		Key ctrsKey = datastore.newKeyFactory()
////				.addAncestors(PathElement.of("User", request.getUsername()))
////				.setKind("UserStats").newKey("counters");
////		Key logKey = datastore.allocateId(
////				datastore.newKeyFactory()
////				.addAncestor(PathElement.of("User", request.getUsername()))
////				.setKind("UserLog").newKey()
////				);
////		-----
//		
//		Transaction txn = datastore.newTransaction();
//		
//		try {
//			Entity user = txn.get(userKey);
//			
//			if(user == null) {
//				LOG.warning("Failed login attempt");
//				return Response.status(Status.FORBIDDEN).entity("Failed login attempt").build();
//			}
//			
//			if(user.getString("user_state").equals("DELETED") || user.getString("user_state").equals("DISABLED")) {
//				LOG.warning("Failed login attempt");
//				return Response.status(Status.FORBIDDEN).entity("Failed login attempt").build();
//			}
//			
////			Entity stats = txn.get(ctrsKey);
////			if(stats == null ) {
////				stats = Entity.newBuilder(ctrsKey)
////						.set("user_stats_logins", 0L)
////						.set("user_stats_failed", 0L)
////						.set("user_first_login", Timestamp.now())
////						.set("user_last_login", Timestamp.now())
////						.build();
////			}
//			
////			
//			String hashedPWD = user.getString("user_pwd");
//			if(hashedPWD.equals(DigestUtils.sha512Hex(request.getPassword()))) {
//				//added
////				Entity log = Entity.newBuilder(logKey)
////						.set("user_login_ip", req.getRemoteAddr())
////						.set("user_login_host", req.getRemoteHost())
////						.set("user_login_latlon", 
////								//does not index this property value
////								StringValue.newBuilder(headers.getHeaderString("X-AppEngine-CityLatLong"))
////								.setExcludeFromIndexes(true).build()
////								) //NEEDS HEADERS ON POSTMAN FOR LOCAL WORK
////						.set("user_login_city", headers.getHeaderString("X-Appengine-City")) //NEEDS HEADERS ON POSTMAN FOR LOCAL WORK
////						.set("user_login_country", headers.getHeaderString("X-Appengine-Country")) //NEEDS HEADERS ON POSTMAN FOR LOCAL WORK
////						.set("user_login_time", Timestamp.now())
////						.build();
////				 -----
//				AuthToken token = new AuthToken(request.getUsername(),user.getString("user_role"));
//				Key tokenKey = datastore.newKeyFactory()
//						.addAncestor(PathElement.of("User", request.getUsername()))
//						.setKind("Token")
//						.newKey(token.getTokenID());
//				Entity tokenEntity = Entity.newBuilder(tokenKey)
//						.set("token_ID", token.getTokenID())
//						.set("token_username",token.getUsername())
//						.set("token_creationData",token.getCreationData())
//						.set("token_expirationData", token.getExpirationData())
//						.set("token_role", token.getRole())
//						.build();
////				txn.put(log,tokenEntity); //changed
//				txn.put(tokenEntity); //changed
//				LOG.warning("WARNING: User '" + request.getUsername() + "'logged in successfully.");
//				txn.commit();
//				return Response.ok(g.toJson(token)).build();
//				
//			}
//			else {
//				txn.rollback();
//				LOG.warning("Wrong password for username: " + request.getUsername());
//				return Response.status(Status.FORBIDDEN).entity("Wrong password or username").build();
//			}
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
	
	
	
	//@POST
	@DELETE
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doLogout(AuthToken token) {
		
		Transaction txn = datastore.newTransaction();
		
		Key tokenKey = datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", token.getUsername()))
				.setKind("Token")
				.newKey(token.getTokenID());
		Entity tokenEntity = txn.get(tokenKey);
		
		try {
			
//			if(tokenEntity == null || System.currentTimeMillis() > token.getExpirationData()) {
//				txn.rollback();
//				LOG.warning("Token Authentication Failed");
//				return Response.status(Status.FORBIDDEN).build();
//			}
//			
			if(tokenEntity == null) {
				txn.rollback();
				LOG.warning("Token Authentication Failed");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Token")
					.setFilter(
							StructuredQuery.PropertyFilter.eq("token_username", token.getUsername()))
					.build();
			
			QueryResults<Entity> tokens = datastore.run(query);
			
			List<Key> tokenKeysList = new ArrayList<>();
			
			tokens.forEachRemaining(userTokens -> {
				tokenKeysList.add(userTokens.getKey());
			});
			
			for(Key tokenIDkey : tokenKeysList) {
				
				LOG.warning("deleted token: " + tokenIDkey.toString());
				txn.delete(tokenIDkey);
					
			}
			txn.commit();
			return Response.ok(" {} ").build();
//			return Response.temporaryRedirect(new URI("https://webapp-310017.ey.r.appspot.com/logout/logout.html")).build();
//			return Response.status(200).location(new URI("https://webapp-310017.ey.r.appspot.com/logout/logout.html")).build();
			
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
	
	
	
}
