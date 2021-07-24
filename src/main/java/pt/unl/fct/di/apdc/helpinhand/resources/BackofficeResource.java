package pt.unl.fct.di.apdc.helpinhand.resources;

import java.util.ArrayList;
import java.util.List;
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
import com.google.cloud.datastore.Cursor;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.helpinhand.api.Authorize;
import pt.unl.fct.di.apdc.helpinhand.api.RequestData;
import pt.unl.fct.di.apdc.helpinhand.api.StaffData;
import pt.unl.fct.di.apdc.helpinhand.api.UsersData;
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
			
//			System.out.println(role);
			
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
	@Path("/suspend/{username}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doSuspend(@PathParam("username") String username, @Context HttpHeaders header) {

		LOG.warning("Attempt to disable user: "+ username);
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			String role = getRole(header);
			
			
			if(!role.equals(Roles.BO.toString()) && !role.equals(Roles.ADMIN.toString())) {
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			
			
			Key userKey = datastore.newKeyFactory()
					.setKind("User")
					.newKey(username);
			Entity userEntity = txn.get(userKey);
			
//			if(userEntity.getString("user_role").equals(Roles.USER.toString()) && (!userEntity.getString("user_state").equals(State.DELETED.toString()) 
//					&& !userEntity.getString("user_state").equals(State.DISABLED.toString())) ) {
			if(!userEntity.getString("user_state").equals(State.ENABLED.toString())) {	
				txn.rollback();
				LOG.warning("NOT ENABLED");
				return Response.status(Status.BAD_REQUEST).entity("notenabled").build();
			}
			
			userEntity = Entity.newBuilder(userEntity)
					.set("user_state", State.SUSPENDED.toString())
					.build();
			txn.update(userEntity);
			
			txn.commit();
			LOG.warning(username +" suspended");
			return Response.ok(username + " suspended").build();
				
				

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
	@POST
	@Path("/enable/{username}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doEnable(@PathParam("username") String username, @Context HttpHeaders header) {

		LOG.warning("Attempt to disable user: "+ username);
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			String role = getRole(header);
			
			
			if(!role.equals(Roles.BO.toString()) && !role.equals(Roles.ADMIN.toString())) {
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			
			
			Key userKey = datastore.newKeyFactory()
					.setKind("User")
					.newKey(username);
			Entity userEntity = txn.get(userKey);

			if(userEntity.getString("user_state").equals(State.ENABLED.toString())) {	
				txn.rollback();
				LOG.warning("ALREADYENABLED");
				return Response.status(Status.BAD_REQUEST).entity("alreadyenabled").build();
			}
			
			userEntity = Entity.newBuilder(userEntity)
					.set("user_state", State.ENABLED.toString())
					.build();
			txn.update(userEntity);
			
			txn.commit();
			LOG.warning(username +"enabled");
			return Response.ok(username +"enabled").build();
				
				

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
	@POST
	@Path("/delete/{username}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doDelete(@PathParam("username") String username, @Context HttpHeaders header) {

		LOG.warning("Attempt to disable user: "+ username);
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			String role = getRole(header);
			
			
			if(!role.equals(Roles.BO.toString()) && !role.equals(Roles.ADMIN.toString())) {
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			
			
			Key userKey = datastore.newKeyFactory()
					.setKind("User")
					.newKey(username);
			Entity userEntity = txn.get(userKey);

			if(userEntity.getString("user_state").equals(State.DELETED.toString())) {	
				txn.rollback();
				LOG.warning("ALREADY DELETED");
				return Response.status(Status.BAD_REQUEST).entity("alreadydeleted").build();
			}
			
//			userEntity = Entity.newBuilder(userEntity)
//					.set("user_state", State.DELETED.toString())
//					.build();
			txn.delete(userKey);
			
			txn.commit();
			LOG.warning(username +"deleted");
			return Response.ok(username +"deleted").build();
				
				

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
	@POST
	@Path("/promote/{staffUsername}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doPromote(@PathParam("staffUsername") String staffUsername, @Context HttpHeaders header) {

		LOG.warning("Attempt to promote user: "+ staffUsername);
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			String role = getRole(header);
			
			
			if(!role.equals(Roles.ADMIN.toString())) {
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			
			
			Key staffKey = datastore.newKeyFactory()
					.setKind("Staff")
					.newKey(staffUsername);
			
			Entity staffEntity = txn.get(staffKey);

			if(staffEntity.getString("staff_role").equals(Roles.ADMIN.toString())) {	
				txn.rollback();
				LOG.warning("ALREADYADMIN");
				return Response.status(Status.BAD_REQUEST).entity("alreadyadmin").build();
			}
			
			staffEntity = Entity.newBuilder(staffEntity)
					.set("staff_role", Roles.ADMIN.toString())
					.build();
			txn.update(staffEntity);
			
			txn.commit();
			LOG.warning(staffUsername +"promoted");
			return Response.ok(staffUsername +"promoted").build();
				
				

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
	@POST
	@Path("/demote/{staffUsername}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doDemote(@PathParam("staffUsername") String staffUsername, @Context HttpHeaders header) {

		LOG.warning("Attempt to demote user: "+ staffUsername);
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			String role = getRole(header);
			
			
			if(!role.equals(Roles.ADMIN.toString())) {
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			
			
			Key staffKey = datastore.newKeyFactory()
					.setKind("Staff")
					.newKey(staffUsername);
			
			Entity staffEntity = txn.get(staffKey);

			if(staffEntity.getString("staff_role").equals(Roles.BO.toString())) {	
				txn.rollback();
				LOG.warning("ALREADYBO");
				return Response.status(Status.BAD_REQUEST).entity("alreadybo").build();
			}
			
			staffEntity = Entity.newBuilder(staffEntity)
					.set("staff_role", Roles.BO.toString())
					.build();
			txn.update(staffEntity);
			
			txn.commit();
			LOG.warning(staffUsername +"demoted");
			return Response.ok(staffUsername +"demoted").build();
				
				

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
	@POST
	@Path("/deleteStaff/{staffUsername}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doDeleteStaff(@PathParam("staffUsername") String staffUsername, @Context HttpHeaders header) {

		LOG.warning("Attempt to delete user: "+ staffUsername);
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			String role = getRole(header);
			
			
			if(!role.equals(Roles.ADMIN.toString())) {
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			
			
			Key staffKey = datastore.newKeyFactory()
					.setKind("Staff")
					.newKey(staffUsername);
			
			Entity staffEntity = txn.get(staffKey);

			
			txn.delete(staffKey);
			
			txn.commit();
			LOG.warning(staffUsername +"demoted");
			return Response.ok(staffUsername +"demoted").build();
				
				

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
	@POST
	@Path("/listreports")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListReports(@Context HttpHeaders header, String startCursorString) {

		LOG.warning("Attempt to list users ");
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			String role = getRole(header);
			
			
			if(!role.equals(Roles.BO.toString()) && !role.equals(Roles.ADMIN.toString())) {
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			int pageSize;
			Cursor startCursor = null;
			
			if(startCursorString !=null && !startCursorString.equals("")) {
				startCursor = Cursor.fromUrlSafe(startCursorString);
			}
			pageSize = 6;
			
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("User")
					.setFilter(
							PropertyFilter.gt("user_reports", 0))
					.setLimit(pageSize)
					.setStartCursor(startCursor)
					.build();
			
			QueryResults<Entity> usersQuery = datastore.run(query);

			List<UsersData> users = new ArrayList<>();
			
			usersQuery.forEachRemaining(user-> {
				UsersData newUser = new UsersData();
				
				newUser.setUsername(user.getKey().getName());
				newUser.setEmail(user.getString("user_email"));
				newUser.setReports(user.getLong("user_reports"));
				newUser.setState(user.getString("user_state"));
			
				users.add(newUser);
			});
			
			Cursor cursor = usersQuery.getCursorAfter();
			
			String cursorString = null;
			
			if(cursor!=null) {
				cursorString = cursor.toUrlSafe();
			}
			
			RequestData data = new RequestData(users, cursorString);
			
			txn.commit();
			LOG.warning("listing users");
			return Response.status(Status.OK).entity(g.toJson(data)).build();
				
				

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
	@POST
	@Path("/listdisabled")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListDisabled(@Context HttpHeaders header, String startCursorString) {

		LOG.warning("Attempt to list users ");
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			String role = getRole(header);
			
			
			if(!role.equals(Roles.BO.toString()) && !role.equals(Roles.ADMIN.toString())) {
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			int pageSize;
			Cursor startCursor = null;
			
			if(startCursorString !=null && !startCursorString.equals("")) {
				startCursor = Cursor.fromUrlSafe(startCursorString);
			}
			pageSize = 6;
			
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("User")
					.setFilter(
							PropertyFilter.eq("user_state", State.DISABLED.toString()))
					.setLimit(pageSize)
					.setStartCursor(startCursor)
					.build();
			
			QueryResults<Entity> usersQuery = datastore.run(query);

			List<UsersData> users = new ArrayList<>();
			
			usersQuery.forEachRemaining(user-> {
				UsersData newUser = new UsersData();
				
				newUser.setUsername(user.getKey().getName());
				newUser.setEmail(user.getString("user_email"));
				newUser.setReports(user.getLong("user_reports"));
				newUser.setState(user.getString("user_state"));
			
				users.add(newUser);
			});
			
			Cursor cursor = usersQuery.getCursorAfter();
			
			String cursorString = null;
			
			if(cursor!=null) {
				cursorString = cursor.toUrlSafe();
			}
			
			RequestData data = new RequestData(users, cursorString);
			
			txn.commit();
			LOG.warning("listing users");
			return Response.status(Status.OK).entity(g.toJson(data)).build();
				
				

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
	@POST
	@Path("/listsuspended")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListSuspended(@Context HttpHeaders header, String startCursorString) {

		LOG.warning("Attempt to list users ");
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			String role = getRole(header);
			
			
			if(!role.equals(Roles.BO.toString()) && !role.equals(Roles.ADMIN.toString())) {
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			int pageSize;
			Cursor startCursor = null;
			
			if(startCursorString !=null && !startCursorString.equals("")) {
				startCursor = Cursor.fromUrlSafe(startCursorString);
			}
			pageSize = 6;
			
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("User")
					.setFilter(
							PropertyFilter.eq("user_state", State.SUSPENDED.toString()))
					.setLimit(pageSize)
					.setStartCursor(startCursor)
					.build();
			
			QueryResults<Entity> usersQuery = datastore.run(query);

			List<UsersData> users = new ArrayList<>();
			
			usersQuery.forEachRemaining(user-> {
				UsersData newUser = new UsersData();
				
				newUser.setUsername(user.getKey().getName());
				newUser.setEmail(user.getString("user_email"));
				newUser.setReports(user.getLong("user_reports"));
				newUser.setState(user.getString("user_state"));
			
				users.add(newUser);
			});
			
			Cursor cursor = usersQuery.getCursorAfter();
			
			String cursorString = null;
			
			if(cursor!=null) {
				cursorString = cursor.toUrlSafe();
			}
			
			RequestData data = new RequestData(users, cursorString);
			
			txn.commit();
			LOG.warning("listing users");
			return Response.status(Status.OK).entity(g.toJson(data)).build();
				
				

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
	@POST
	@Path("/listdeleted")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListDeleted(@Context HttpHeaders header, String startCursorString) {

		LOG.warning("Attempt to list users ");
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			String role = getRole(header);
			
			
			if(!role.equals(Roles.BO.toString()) && !role.equals(Roles.ADMIN.toString())) {
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			int pageSize;
			Cursor startCursor = null;
			
			if(startCursorString !=null && !startCursorString.equals("")) {
				startCursor = Cursor.fromUrlSafe(startCursorString);
			}
			pageSize = 6;
			
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("User")
					.setFilter(
							PropertyFilter.eq("user_state", State.DELETED.toString()))
					.setLimit(pageSize)
					.setStartCursor(startCursor)
					.build();
			
			QueryResults<Entity> usersQuery = datastore.run(query);

			List<UsersData> users = new ArrayList<>();
			
			usersQuery.forEachRemaining(user-> {
				UsersData newUser = new UsersData();
				
				newUser.setUsername(user.getKey().getName());
				newUser.setEmail(user.getString("user_email"));
				newUser.setReports(user.getLong("user_reports"));
				newUser.setState(user.getString("user_state"));
			
				users.add(newUser);
			});
			
			Cursor cursor = usersQuery.getCursorAfter();
			
			String cursorString = null;
			
			if(cursor!=null) {
				cursorString = cursor.toUrlSafe();
			}
			
			RequestData data = new RequestData(users, cursorString);
			
			txn.commit();
			LOG.warning("listing users");
			return Response.status(Status.OK).entity(g.toJson(data)).build();
				
				

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
	@POST
	@Path("/liststaff")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListStaff(@Context HttpHeaders header, String startCursorString) {

		LOG.warning("Attempt to list users ");
		
		Transaction txn = datastore.newTransaction();
		
		try {
			
			
			String role = getRole(header);
			
			
			if(!role.equals(Roles.BO.toString()) && !role.equals(Roles.ADMIN.toString())) {
				txn.rollback();
				LOG.warning("FORBIDDEN REQUEST");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			int pageSize;
			Cursor startCursor = null;
			
			if(startCursorString !=null && !startCursorString.equals("")) {
				startCursor = Cursor.fromUrlSafe(startCursorString);
			}
			pageSize = 6;
			
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Staff")
					.setLimit(pageSize)
					.setStartCursor(startCursor)
					.build();
			
			QueryResults<Entity> usersQuery = datastore.run(query);

			List<StaffData> users = new ArrayList<>();
			
			usersQuery.forEachRemaining(user-> {
				StaffData newUser = new StaffData();
				
				newUser.setUsername(user.getKey().getName());
				newUser.setEmail(user.getString("staff_email"));
				newUser.setRole(user.getString("staff_role"));
			
				users.add(newUser);
			});
			
			Cursor cursor = usersQuery.getCursorAfter();
			
			String cursorString = null;
			
			if(cursor!=null) {
				cursorString = cursor.toUrlSafe();
			}
			
			RequestData data = new RequestData(users, cursorString);
			
			txn.commit();
			LOG.warning("listing users");
			return Response.status(Status.OK).entity(g.toJson(data)).build();
				
				

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
	
	
	
}
