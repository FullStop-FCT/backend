package pt.unl.fct.di.apdc.helpinhand.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.helpinhand.api.Request;
import pt.unl.fct.di.apdc.helpinhand.data.Database;

@Path("/activities")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ActivityResource {

	Database database = new Database();
	
	private static final Logger LOG = Logger.getLogger(ActivityResource.class.getName());
	
	
	private static final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
	public String timestamp;
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	private final Gson g = new Gson();
	
	private Transaction txn = datastore.newTransaction();
	private KeyFactory factory = datastore.newKeyFactory();
	
	public ActivityResource() {
		
	}

	@POST
	@Path("/insert") //register
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doInsert(Request request) {
		LOG.warning("Attempt to create activity " + request.getActivityData().getTitle());
		
//		Transaction txn = datastore.newTransaction();
//		Key orgKey = database.getOrgKey(request.getToken().getUsername());
		Key tokenKey = database.getTokenKey(request.getToken());
		
		
		try {
//			Entity orgEntity=txn.get(orgKey);
			Entity tokenEntity=txn.get(tokenKey);
			
			if(tokenEntity == null || System.currentTimeMillis() > request.getToken().getExpirationData()) {
				txn.rollback();
				LOG.warning("Token Authentication Failed");
				return Response.status(Status.FORBIDDEN).build();
			}
			Key activityKey = database.getActivityKey(request.getActivityData());
//			Key activityKey = factory
//					.addAncestor(PathElement.of("User", request.getActivityData().getActivityOwner()))
//					.setKind("Activity")
//					.newKey(request.getActivityData().getTitle());
			
			
			Entity activityEntity=txn.get(activityKey);
			
			if(activityEntity != null) {
				txn.rollback();
				LOG.warning("This activity already exists");
				return Response.status(Status.BAD_REQUEST).entity("Activity already exists.").build();
			}else {
				activityEntity = Entity.newBuilder(activityKey)
						.set("activity_title", request.getActivityData().getTitle())
						.set("activity_description", request.getActivityData().getDescription())
						.set("activity_date", request.getActivityData().getDate())
						.set("activity_location", request.getActivityData().getLocation())
						.set("activity_total_participants", request.getActivityData().getTotalParticipants())
//						.set("activity_participants", request.getActivityData().getList());
						.set("activity_owner", request.getToken().getUsername())
						.build();
				
				txn.add(activityEntity);
				LOG.warning("activity registered " + request.getActivityData().getTitle());
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
		
//		return null;
	}
	
}
