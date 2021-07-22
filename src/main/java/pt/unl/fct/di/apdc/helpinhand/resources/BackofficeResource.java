package pt.unl.fct.di.apdc.helpinhand.resources;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.helpinhand.api.Authorize;
import pt.unl.fct.di.apdc.helpinhand.api.StaffData;
import pt.unl.fct.di.apdc.helpinhand.data.Database;
import pt.unl.fct.di.apdc.helpinhand.util.Roles;
import pt.unl.fct.di.apdc.helpinhand.util.State;

@Path("/backoffice")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class BackofficeResource {
Database database = new Database();
	

	private static final Logger LOG = Logger.getLogger(BackofficeResource.class.getName());
	
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	private final Gson g = new Gson();
	
	private Transaction txn = datastore.newTransaction();
	private KeyFactory factory = datastore.newKeyFactory();
	
	
	
	public BackofficeResource() {
		
	}
	
	
	
	
	
	
	@Authorize
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doInsert(@Context HttpHeaders header, StaffData staff) {
		
		LOG.warning("Attempt to register backofficer: "+ staff.getUsername());
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			String role = getRole(header);
			
			System.out.println(role);
			
			if(!role.equals(Roles.BO.toString()) && !role.equals(Roles.ADMIN.toString())) {
//				System.out.println(!role.equals(Roles.GBO.toString()));
//				System.out.println(!role.equals(Roles.SU.toString()));
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			Key staffKey = datastore.newKeyFactory().setKind("Staff").newKey(staff.getUsername());
			
			Entity staffEntity = txn.get(staffKey);
			if(staffEntity != null) {
				txn.rollback();
				LOG.warning("The staff already exists");
				return Response.status(Status.BAD_REQUEST).entity("Staff already exists.").build();	
			}else {
				staffEntity = Entity.newBuilder(staffKey)
						.set("staff_email",staff.getEmail())
						.set("staff_pwd", StringValue.newBuilder(DigestUtils.sha512Hex(staff.getPassword())).setExcludeFromIndexes(true).build())
						.set("staff_role", staff.getRole())
						.build();
			}
			
			txn.add(staffEntity);
			LOG.warning("STAFF registered " + staff.getUsername());
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
	
	
	private String getRole(HttpHeaders header) {
		
		String authHeaderVal = header.getHeaderString("Authorization");
		DecodedJWT jwtDecoded = JWT.decode(authHeaderVal.split(" ")[1]);
		String role = jwtDecoded.getClaim("role").asString();
		return role;
	}	
	
	
	@Authorize
	@POST
	@Path("/disable/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doDisable(String userSelf, @PathParam("username") String username) {

		LOG.warning("Attempt to disable user: "+ username);
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			Key userKey = datastore.newKeyFactory()
					.setKind("User")
					.newKey(username);
			Entity userEntity = txn.get(userKey);
			
			if(userEntity.getString("user_role").equals(Roles.USER.toString()) && (!userEntity.getString("user_state").equals(State.DELETED.toString()) 
					|| !userEntity.getString("user_state").equals(State.DISABLED.toString())) ) {
				
				userEntity = Entity.newBuilder(userEntity)
						.set("user_state", State.DISABLED.toString())
						.build();
				
				txn.update(userEntity);
				
				txn.commit();
				
				return Response.ok(" {} ").build();
						
				
				
				
			}
			
			txn.commit();
			return Response.status(Status.OK).build();	
				
				

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
