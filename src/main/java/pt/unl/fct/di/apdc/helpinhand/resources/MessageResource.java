package pt.unl.fct.di.apdc.helpinhand.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Cursor;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.helpinhand.api.Authorize;
import pt.unl.fct.di.apdc.helpinhand.api.MessageData;
import pt.unl.fct.di.apdc.helpinhand.api.RequestData;

@Path("/comments")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MessageResource {

	private static final Logger LOG = Logger.getLogger(MessageResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	private final Gson g = new Gson();
	
	private Transaction txn = datastore.newTransaction();
	private KeyFactory factory = datastore.newKeyFactory();
	
	public MessageResource() {
		
	}
	
	private String getUsername(HttpHeaders header) {
		
		String authHeaderVal = header.getHeaderString("Authorization");
		DecodedJWT jwtDecoded = JWT.decode(authHeaderVal.split(" ")[1]);
		String username = jwtDecoded.getIssuer();
		return username;
	}
	
	@Authorize
	@POST
	@Path("/insert/{activityID}/{owner}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doAdd(@PathParam("activityID") String activityID, @PathParam("owner") String owner, @Context HttpHeaders header, MessageData msg) {
		
		LOG.warning("Attempt to add comment on activity " + activityID);
		
		String username = getUsername(header);
	
		Transaction txn = datastore.newTransaction();
		try {
			Key messageKey = datastore.allocateId(factory
					.addAncestors(PathElement.of("User",owner ), PathElement.of("Activity", activityID))
					.setKind("Message")
					.newKey());
//			
//			Key userKey = factory
//					.setKind("User")
//					.newKey(username);
//			
//			Entity userEntity = txn.get(userKey);
//			
//			Key activityKey = factory
//					.addAncestor(PathElement.of("User", owner))
//					.setKind("Activity")
//					.newKey(activityID);
			
			Entity messageEntity = txn.get(messageKey);
			
			
			messageEntity = Entity.newBuilder(messageKey)
					.set("activity_ID", activityID)
					.set("owner", owner)
					.set("user", username)
					.set("message", msg.getMessage())
					.set("image", msg.getImage())
					.set("date",Timestamp.now())
					.set("id", messageKey.getId())
					.build();
			
			txn.add(messageEntity);
			LOG.warning("comment added");
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
	@DELETE
	@Path("/delete/{activityID}/{owner}/{msgID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doDelete(@PathParam("activityID") String activityID, @PathParam("owner") String owner, @PathParam("msgID") String msgID, @Context HttpHeaders header) {
		
		LOG.warning("Attempt to add comment on activity " + msgID);
		
		String username = getUsername(header);
		
		Transaction txn = datastore.newTransaction();
		try {
			
			Key messageKey = factory
					.addAncestors(PathElement.of("User",owner ), PathElement.of("Activity", activityID))
					.setKind("Message")
					.newKey(Long.valueOf(msgID));
			
			Entity messageEntity = txn.get(messageKey);
			
//			LOG.warning("we here");
			
			if(!username.equals(messageEntity.getString("user")) && !username.equals(messageEntity.getString("owner"))) {
				txn.rollback();
				LOG.warning("Cant do that");
				return Response.status(Status.FORBIDDEN).entity("cantdelete").build();
			}
			
			
			txn.delete(messageKey);
			txn.commit();
			LOG.warning("comment deleted sucessfully");

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
	@Path("/get")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doGet() {
		try {
			

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
	@POST
	@Path("/list/{activityID}/{owner}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doList(@PathParam("activityID") String activityID, @PathParam("owner") String owner, String startCursorString) {
		LOG.warning("Attempt to list comments on activity " + activityID);
		
		Transaction txn = datastore.newTransaction();
		
		
		int pageSize;
		Cursor startCursor = null;
		
		if(startCursorString !=null && !startCursorString.equals("")) {
			startCursor = Cursor.fromUrlSafe(startCursorString);
		}
		
		
		
		try {
			
			pageSize = 5;

			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Message")
					.setFilter(PropertyFilter.hasAncestor(
							factory.setKind("Activity").addAncestor(PathElement.of("User", owner)).newKey(activityID)
							)
						)
					.setOrderBy(OrderBy.desc("date"))
					.setLimit(pageSize)
					.setStartCursor(startCursor)
					.build();
			
			QueryResults<Entity> msgsQuery = datastore.run(query);
			
			List<MessageData> messages = new ArrayList<>();
			
			msgsQuery.forEachRemaining(message ->{
				MessageData newMsg = new MessageData();
				
				newMsg.setAuthor(message.getString("user"));
				newMsg.setReceiver(message.getString("user"));
				newMsg.setMsgID(message.getLong("id"));
				newMsg.setMessage(message.getString("message"));
				newMsg.setImage(message.getString("image"));
				newMsg.setDate(message.getTimestamp("date").toString());
				newMsg.setActivityID(message.getString("activity_ID"));
				
				messages.add(newMsg);
	
			});
			
			
			Cursor cursor = msgsQuery.getCursorAfter();
			
			String cursorString = null;
			
			if(cursor!=null) {
				cursorString = cursor.toUrlSafe();
			}
			
			RequestData data = new RequestData(messages, cursorString);
			
			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(data)).build();
//			return Response.ok(" {} ").build();
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
	@Path("/send")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doSend() {
		try {
			

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
	
}
