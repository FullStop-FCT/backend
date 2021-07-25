package pt.unl.fct.di.apdc.helpinhand.resources;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskQueuePb.TaskQueueQueryAndOwnTasksResponse;
import com.google.appengine.repackaged.com.google.common.collect.Iterators;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Cursor;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityQuery;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.StructuredQuery;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;

import pt.unl.fct.di.apdc.helpinhand.api.ActivitiesData;
import pt.unl.fct.di.apdc.helpinhand.api.AuthToken;
import pt.unl.fct.di.apdc.helpinhand.api.Authorize;
import pt.unl.fct.di.apdc.helpinhand.api.RequestData;
import pt.unl.fct.di.apdc.helpinhand.api.UsersData;
import pt.unl.fct.di.apdc.helpinhand.data.Database;
import pt.unl.fct.di.apdc.helpinhand.util.Profile;
import pt.unl.fct.di.apdc.helpinhand.util.State;

@Path("/activities")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ActivityResource {

	Database database = new Database();
	
	private static final Logger LOG = Logger.getLogger(ActivityResource.class.getName());
	private static final String SENDGRID_API_KEY="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";
	
	private static final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
	public String timestamp;
	/**
	 * 
	 */
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	private final Gson g = new Gson();
	
	private Transaction txn = datastore.newTransaction();
	private KeyFactory factory = datastore.newKeyFactory();
	
	public ActivityResource() {
		
	}
	
	
	private String getUsername(HttpHeaders header) {
		
		String authHeaderVal = header.getHeaderString("Authorization");
		DecodedJWT jwtDecoded = JWT.decode(authHeaderVal.split(" ")[1]);
		String username = jwtDecoded.getIssuer();
		return username;
	}
	
	@Authorize
	@POST
	@Path("/insert") //register
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doInsert(@HeaderParam ("Authorization") String header, RequestData request) {
		LOG.warning("Attempt to create activity " + request.getActivityData().getTitle());
		

		String token= header.split(" ")[1];
		
		
		DecodedJWT jwtDecoded = JWT.decode(token);
		String username = jwtDecoded.getIssuer();

		try {

			ActivitiesData act = new ActivitiesData(
					request.getActivityData().getTitle(), 
					request.getActivityData().getDescription(),
					request.getActivityData().getDate(), 
					request.getActivityData().getLocation(),
					request.getActivityData().getTotalParticipants(),
					username,
					request.getActivityData().getCategory(),
					request.getActivityData().getLat(),
					request.getActivityData().getLon(),
					request.getActivityData().getStartHour(), //added
					request.getActivityData().getEndHour(), 
					request.getActivityData().getKeywords(),
					request.getActivityData().getWaypoints(),
					request.getActivityData().getActivityTime()
//					request.getActivityData().getMaxWayPoints()
					);
			
			
			
					
			Key activityKey = factory
					.addAncestor(PathElement.of("User", act.getActivityOwner()))
					.setKind("Activity")
					.newKey(act.getID());
			
			Key userKey = database.getUserKey(username);
			
			Entity userEntity = txn.get(userKey);
			
			//recently added
			if(!userEntity.getString("user_state").equals(State.ENABLED.toString())) {
				txn.rollback();
				LOG.warning("CANT DO THAT");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			Key createdKey = datastore.allocateId(factory
//					.addAncestors(PathElement.of("User", act.getActivityOwner()), PathElement.of("Activity", act.getID()))
					.addAncestor(PathElement.of("Activity", act.getID()))
					.setKind("CreatedActivityBy")
					.newKey()
					);
			
			Entity activityEntity=txn.get(activityKey);
			
			Entity createdActivityEntity = txn.get(createdKey);
			
			
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
						.set("activity_participants", request.getActivityData().getParticipants()) //added
						.set("activity_total_participants", request.getActivityData().getTotalParticipants()) //reworked
//						.set("activity_participants", request.getActivityData().getList());
						.set("activity_category", request.getActivityData().getCategory())
						.set("activity_owner", username)
						.set("activity_lat", request.getActivityData().getLat())
						.set("activity_lon", request.getActivityData().getLon())
						.set("activity_startHour", request.getActivityData().getStartHour())
						.set("activity_endHour", request.getActivityData().getEndHour())
						
						.set("activity_time", request.getActivityData().getActivityTime())

						.set("activity_waypoints", convertToValueList(request.getActivityData().getWaypoints()))
						.set("activity_keywords", convertToValueList(request.getActivityData().getKeywords()))
						
						.set("is_org", userEntity.getBoolean("is_org"))
						.build();
				
				createdActivityEntity = Entity.newBuilder(createdKey)
						.set("created_by", username)
						.set("activity_title", request.getActivityData().getTitle())
						.set("activity_ID", act.getID()) //just added
						.set("is_org", userEntity.getBoolean("is_org")) //just added
						.build();
				
				txn.add(activityEntity, createdActivityEntity);
				
				long createdActivities = userEntity.getLong("created_activities")+1;
				
				Entity newUser = Entity.newBuilder(userEntity)
						.set("created_activities", createdActivities)
						.build();
				txn.update(newUser);
				
				
				if(userEntity.getBoolean("is_org")) {
					sendMail(userEntity.getString("contact_list_id"), request.getActivityData().getTitle());
				}

				
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
		
	}	
	
	@POST
	@Path("/testemail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doEmailTest() {
		
		sendMail("dd8a4474-c56c-401a-b471-4551bb61e995","teste");
		return Response.ok().build();
	}
	
	
	private void sendMail(String listID, String title) {
		LOG.warning("sending email to all contacts in list");
		
		
		SendGrid sg = new SendGrid(SENDGRID_API_KEY);	 
		 
//		SendGrid sg = new SendGrid(SENDGRID_API_KEY);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("/marketing/singlesends");
			request.setBody("{\"name\":\"Example API Created Single Send\",\"send_at\":\"2021-07-25T10:20:52Z\",\"send_to\":{\"all\":true},\"email_config\":{\"design_id\":\"72fa35dc-8076-4637-bfeb-813fba6dfff8\",\"sender_id\":1762877}}");
			request.addHeader("Authorization", "Bearer "+SENDGRID_API_KEY);
//			request.addHeader("content-type", "application/json");
				  
				  
			sg.api(request);
				 
				  
				  
				  com.sendgrid.Response response = sg.api(request);
				  System.out.println(response.getStatusCode());
				  System.out.println(response.getBody());
				  System.out.println(response.getHeaders());
			} catch (IOException ex) {
				LOG.warning(ex.getMessage());
			}
	}
	
	
	private List<Value<String>> convertToValueList(List<String> list) {
		// TODO Auto-generated method stub
		List<Value<String>> result = new ArrayList<Value<String>>();
		
		for(String s : list) {
			result.add(StringValue.of(s));
		}
		
		return result;
	}
	
	private List<String> convertToList(List<Value<String>> listValue){
		
		List<String> result = new ArrayList<String>();
		
		for (Value<String> v : listValue) {
			result.add(v.get());
			
		}
		return result;
		
	}


	
	
//	private LocalDateTime getTime(String time) {
//		LocalDateTime dateTime = LocalDate.parse(time).atStartOfDay();
//		
////		System.out.println(dateTime);
//		return dateTime;
//	}
	
	
//	@POST
//	@Path("/test")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response doTest(String time) {
//		
//		
//		Date date1;
//		try {
//			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(time);
//			Timestamp.of(date1);
//			if(Timestamp.of(date1).compareTo(Timestamp.now())==-1) {
//				System.out.println(Timestamp.of(date1) + " e menor");
//			}
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
////		if(today.isAfter(date)) {
////			System.out.println("date is after today");
////		}
//		return Response.ok(" {} ").build();
//	}

	
	private Timestamp toTimestamp(String date) {
		Date timestamp = null;
		try {
			timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(date);

		}catch(ParseException e) {
			e.printStackTrace();
		}
		return Timestamp.of(timestamp);
	}
	
	@Authorize
	@POST
	@Path("/join/{activityID}/{activityOwner}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doJoin(String username, @PathParam("activityID") String activityID, @PathParam("activityOwner") String activityOwner) {

		Transaction txn = datastore.newTransaction();

		try {

			//recently added
			Key selfKey = database.getUserKey(username);
			Entity selfEntity = txn.get(selfKey);
			if(!selfEntity.getString("user_state").equals(State.ENABLED.toString())) {
				txn.rollback();
				LOG.warning("CANT DO THAT");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			
			if(username.equals(activityOwner)) {
				txn.rollback();
				LOG.warning("Can't join this activity");
				return Response.status(Status.BAD_REQUEST).entity("Can't join this activity.").build();
			}
		
		
			Key joinKey = datastore.newKeyFactory()
					.addAncestors(PathElement.of("User", activityOwner), PathElement.of("Activity", activityID)) //alterado user para activityOwner
					.setKind("UserJoinedActivity")
					.newKey(username);
			
			Key activityKey = datastore.newKeyFactory()
					.addAncestor(PathElement.of("User", activityOwner))
					.setKind("Activity")
					.newKey(activityID);
			
			Entity joinedEntity = txn.get(joinKey);
			
			if(joinedEntity != null) {
				txn.rollback();
				LOG.warning("This user already joined this activity");

				return Response.status(Status.BAD_REQUEST).entity("This user already joined this activity.").build();
			}
			
			Entity activityEntity = txn.get(activityKey);
			
			
			long participants = activityEntity.getLong("activity_participants")+1;
			long total = activityEntity.getLong("activity_total_participants");

			
			if(participants>total) {
				txn.rollback();
				LOG.warning("This activity is full :(");
				return Response.ok(" Activity is full :( ").build();
//				return Response.status(Status.BAD_REQUEST).entity("Activity is full :(").build();
			}
			activityEntity = Entity.newBuilder(datastore.get(activityKey))
					.set("activity_participants", participants)
					.build();
			
			Timestamp date = toTimestamp(activityEntity.getString("activity_date")+"T"+activityEntity.getString("activity_endHour")+":00"+"Z");
			
			joinedEntity = Entity.newBuilder(joinKey)
					.set("activity_ID", activityID)
					.set("activity_title", activityEntity.getString("activity_title"))
					.set("user", username) //just added
					.set("owner", activityOwner)
					.set("activity_date", date)//just added
					.set("activity_time", activityEntity.getLong("activity_time")) // just added
					.set("is_org", activityEntity.getBoolean("is_org")) // just added
//					activityEntity.get
//					.set("user_username", token.getUsername())
					.build();
			

			
			txn.update(activityEntity);
			txn.add(joinedEntity);
			LOG.warning("joined activity " + activityID );
			
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
	@Path("/isjoined/{activityID}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doIsJoined(@Context HttpHeaders header, @PathParam("activityID") String activityID) {
		
		String username = getUsername(header);
		
		Transaction txn = datastore.newTransaction();
		

		try {

		
		Query<Entity> query = Query.newEntityQueryBuilder()
				.setKind("UserJoinedActivity")
				.setFilter(CompositeFilter.and(
//						PropertyFilter.hasAncestor(factory.setKind("User").newKey(username)),
						PropertyFilter.eq("user", username), //alterado
						PropertyFilter.eq("activity_ID", activityID)
//						PropertyFilter.eq("user", username)
						))
				
				.build();
		
		QueryResults<Entity> joinedQuery = datastore.run(query);
		
		boolean isJoined = false;
		
		if(joinedQuery.hasNext())
			isJoined=true;
		
		txn.commit();
		return Response.status(Status.OK).entity(g.toJson(isJoined)).build();
			
			
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
	@Path("/leave/{activityID}/{activityOwner}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doLeave(String username, @PathParam("activityID") String activityID, @PathParam("activityOwner") String activityOwner) {
	
		
//		String username = getUsername(header);
		
		Transaction txn = datastore.newTransaction();
		

		
		try {

		
			Key joinKey = datastore.newKeyFactory()
					.addAncestors(PathElement.of("User", activityOwner), PathElement.of("Activity", activityID)) //alterado para activityOwner
					.setKind("UserJoinedActivity")
					.newKey(username);
			
			Key activityKey = datastore.newKeyFactory()
					.addAncestor(PathElement.of("User", activityOwner))
					.setKind("Activity")
					.newKey(activityID);
			
			Entity joinedEntity = txn.get(joinKey);
			
			if(joinedEntity == null) {
				txn.rollback();
				LOG.warning("This user didn't join this activity");
				return Response.status(Status.BAD_REQUEST).entity("This user didnt join this activity.").build();
			}
			
			Entity activityEntity = txn.get(activityKey);
			
			
			long participants = activityEntity.getLong("activity_participants")-1;

			

			activityEntity = Entity.newBuilder(datastore.get(activityKey))
					.set("activity_participants", participants)
					.build();
			


			
			txn.update(activityEntity);
			
			txn.delete(joinKey);
			LOG.warning("Leaved activity " + activityID );
			
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
	
//add hours
	
	
	private ActivitiesData createActivity(Entity activity) {
		
		ActivitiesData newAct = new ActivitiesData();
		newAct.setID(activity.getKey().getName());
		newAct.setCategory(activity.getString("activity_category"));
		newAct.setDate(activity.getString("activity_date"));
		newAct.setDescription(activity.getString("activity_description"));
		newAct.setStartHour(activity.getString("activity_startHour"));
		newAct.setEndHour(activity.getString("activity_endHour"));
		newAct.setKeywords(convertToList(activity.getList("activity_keywords")));
		newAct.setLat(activity.getString("activity_lat"));
		newAct.setLon(activity.getString("activity_lon"));
		newAct.setLocation(activity.getString("activity_location"));
		newAct.setActivityOwner(activity.getString("activity_owner"));
		newAct.setParticipants(activity.getLong("activity_participants"));
		newAct.setTotalParticipants(activity.getLong("activity_total_participants"));
		newAct.setTitle(activity.getString("activity_title"));
		newAct.setWaypoints(convertToList(activity.getList("activity_waypoints")));
//		newAct.setWaypoints(activity.getString("activity_waypoints")); //just added
		newAct.setActivityTime(activity.getLong("activity_time"));//just added

		return newAct;
	}
	
	
	
	
	//REWORK
	
	@Authorize
	@GET
	@Path("/search")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doSearch(@QueryParam("keyword") String keyword) {
		
		Transaction txn = datastore.newTransaction();
		
		
		try {
	
		LOG.warning("keyword: " + keyword);
		if(!keyword.isEmpty()) {
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Activity")
					.setFilter(
							StructuredQuery.PropertyFilter.eq("activity_keywords", keyword))

					.setLimit(25)
					.build();
			
			QueryResults<Entity> search = datastore.run(query);
			
			List<ActivitiesData> activities = new ArrayList<>();
			
			search.forEachRemaining(activity ->{
				ActivitiesData newAct = createActivity(activity);
						
			
				activities.add(newAct);
				
				
			});
			

			
			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(activities)).build();
			
		}else {
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Activity")
					.setLimit(25)
					.build();
			
			QueryResults<Entity> search = datastore.run(query);
			
			List<ActivitiesData> activities = new ArrayList<>();
			
			search.forEachRemaining(activity ->{
				ActivitiesData newAct = createActivity(activity);
				
				activities.add(newAct);
				
				
			});
			
			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(activities)).build();
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

	
//	@Authorize
//	@GET
//	@Path("/list")
////	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response doListUsers() {
//		
//		Transaction txn = datastore.newTransaction();
//		
//
//		
//		LOG.warning("Doing list activities");
//		
//		try {
//
//			
//			Query<Entity> query = Query.newEntityQueryBuilder()
//					.setKind("Activity")
////					.setFilter(
////									PropertyFilter.hasAncestor(
////											datastore.newKeyFactory().setKind("User").newKey(token.getUsername()))
////							)
//					.setOrderBy(OrderBy.desc("activity_date"))
//					.setLimit(10)
//					.build();
//										
//						
//			QueryResults<Entity> titlesQuery = datastore.run(query);
//			
//			List<ActivitiesData> activities = new ArrayList<>();
//			
//			titlesQuery.forEachRemaining(activity -> {
//				
//				ActivitiesData newAct = createActivity(activity);
//
//
//				activities.add(newAct);
//			});
//			
//			
//			txn.commit();
////			return Response.ok(" {} ").build();
//			return Response.status(Status.OK).entity(g.toJson(activities)).build();
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
//		
//	}
	
	
	@Authorize
	@POST
	@Path("/listCursor")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListActivitiesCursor(String startCursorString) {
		
		Transaction txn = datastore.newTransaction();
		
		int pageSize;
		
		Cursor startCursor = null;
		
		if(startCursorString !=null && !startCursorString.equals("")) {
			startCursor = Cursor.fromUrlSafe(startCursorString);
		}
		
//		pageCursor = Cursor.copyFrom(bytes);
		
//		Cursor pageCursor;
		
		
		LOG.warning("Doing list activities");
		
		try {

			pageSize = 5;
			
			EntityQuery.Builder queryBuilder = Query.newEntityQueryBuilder()
					.setKind("Activity")
					.setOrderBy(OrderBy.desc("activity_date"))
					.setLimit(pageSize)
					.setStartCursor(startCursor);


						
			QueryResults<Entity> titlesQuery = datastore.run(queryBuilder.build());
			
			List<ActivitiesData> activities = new ArrayList<>();
			
			titlesQuery.forEachRemaining(activity -> {
				
				ActivitiesData newAct = createActivity(activity);


				activities.add(newAct);
			});
			
			
			Cursor cursor = titlesQuery.getCursorAfter();
			
			String cursorString = null;
			
			if(cursor!=null) {
				cursorString = cursor.toUrlSafe();
			}
			
			
			RequestData data = new RequestData(activities, cursorString);
			
			txn.commit();
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
	
	
	
	//rework
	
	@Authorize
	@GET
	@Path("/listByOrg")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListByOrg() {
		
		Transaction txn = datastore.newTransaction();
		

		
		
		try {
		

			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Activity")

					.build();

			
			QueryResults<Entity> activitiesQuery = datastore.run(query);
			
			Query<Entity> queryU = Query.newEntityQueryBuilder()
					.setKind("User")
					.setFilter(
							PropertyFilter.eq("is_org", true))
					.setLimit(25)
					.build();
			
			QueryResults<Entity> usersQuery = datastore.run(queryU);
			
			List<String> users = new ArrayList<>();
			
			usersQuery.forEachRemaining(user->{	

				String nextUser = user.getKey().getName();
				
				users.add(nextUser);
				
			});
		
			
			List<ActivitiesData> activities = new ArrayList<>();
			
			activitiesQuery.forEachRemaining(activity -> {
				
				for(String  u : users) {
					if(u.contains(activity.getString("activity_owner"))) {

						
						ActivitiesData newAct = createActivity(activity);
						activities.add(newAct);
					}
				}
				
				
			});
			
			
			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(activities)).build();
//			
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
	
	
	
	//rework
	
	@Authorize
	@GET
	@Path("/listByUser")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListByUser() {
		
		Transaction txn = datastore.newTransaction();
		

		
		
		try {

			

			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Activity")
					.build();

			
			QueryResults<Entity> activitiesQuery = datastore.run(query);
			
			Query<Entity> queryU = Query.newEntityQueryBuilder()
					.setKind("User")
					.setFilter(
							PropertyFilter.eq("is_org", false))
					.build();
			
			QueryResults<Entity> usersQuery = datastore.run(queryU);
			
			List<String> users = new ArrayList<>();
			
			usersQuery.forEachRemaining(user->{	

				String nextUser = user.getKey().getName();
				
				users.add(nextUser);
				
			});
		
			
			List<ActivitiesData> activities = new ArrayList<>();
			
			activitiesQuery.forEachRemaining(activity -> {
				
				for(String  u : users) {
					if(u.contains(activity.getString("activity_owner"))) {
						
						ActivitiesData newAct = createActivity(activity);
						activities.add(newAct);
					}
				}
				
				
			});
			
			
			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(activities)).build();
//			
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
	@Path("/get/{activityID}/{activityOwner}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetActivity(@PathParam("activityID") String activityID, @PathParam("activityOwner") String activityOwner) {
		

		
		Transaction txn = datastore.newTransaction();
		

		
		Key activityKey = datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", activityOwner))
				.setKind("Activity")
				.newKey(activityID);
		
		try {
			
			Entity activityEntity = txn.get(activityKey);
			
	
			if(activityEntity == null) {
				txn.rollback();
				LOG.warning("No such activity");
				return Response.status(Status.FORBIDDEN).build();
			}
			ActivitiesData newAct = createActivity(activityEntity);
		
			
			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(newAct)).build();
			


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
	@Path("/listJoinedUsers/{activityID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetJoinedUsers(@PathParam("activityID") String activityID) {
		Transaction txn = datastore.newTransaction();
		
		
		try {
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("UserJoinedActivity")
					.setFilter(
							PropertyFilter.eq("activity_ID", activityID))
					.build();
			
			QueryResults<Entity> results = datastore.run(query);
			
			List<String> users = new ArrayList<>();
			
			results.forEachRemaining(result-> {
				
				String newUser = result.getString("user");
				
//				ActivitiesData newAct = new ActivitiesData();
//				newAct.setID(activity.getString("activity_ID"));
//				newAct.setTitle(activity.getString("activity_title"));
				
				users.add(newUser);
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
//	
//	
	@Authorize
	@GET
	@Path("/compute/{activityID}/{points}")
	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
	public Response triggerExecuteComputeTask(@PathParam("activityID") String activityID, @PathParam("points") String points) {
		Queue queue = QueueFactory.getDefaultQueue();
		
		String url="/rest/activities/computeAddHours/"+activityID+"/"+points;
//		LOG.info(header);
//		queue.add(TaskOptions.Builder.withUrl("/rest/activities/computeAddHours/"+activityID+"/"+minutes).);
		queue.add(TaskOptions.Builder.withUrl(url));
//		LOG.warning("hello?");
		return Response.ok().build();
	}
	  
	@POST
	@Path("/computeAddHours/{activityID}/{points}")
//	@Consumes(MediaType.TEXT_HTML)
//	@Produces(MediaType.APPLICATION_JSON)
	public Response executeComputeTask(@PathParam("activityID") String activityID, @PathParam("points") String points) {
//	public Response executeComputeTask() {
		LOG.warning("Starting to execute computation tasks");
		Transaction txn = datastore.newTransaction();

		try {
			
			
		
//			String activityID2="d156ef4a-a378-4143-9c53-8702868e1cac";
			
//			String minutes="10";
			
			LOG.warning("Getting users");
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("UserJoinedActivity")
					.setFilter(
							PropertyFilter.eq("activity_ID", activityID))
					.build();
			
			QueryResults<Entity> results = datastore.run(query);
			
			List<String> users = new ArrayList<>();
			
			
			results.forEachRemaining(result-> {
				
				String newUser = result.getString("user");
						
				users.add(newUser);
			});
			
//			String minutes="5";
			
			LOG.warning("Giving points");
			
			users.forEach(user->{
				Key userKey = database.getUserKey(user);
				Entity userEntity = txn.get(userKey);
				if(userEntity!=null) {
					long hours = userEntity.getLong("user_hours")+Long.valueOf(points);
					userEntity = Entity.newBuilder(userEntity)
							.set("user_hours", hours)
							.build();
					txn.update(userEntity);
					LOG.warning("Points added to User : " + user);
				}
			});
			

//			Thread.sleep(1000 * 60);
		} catch (Exception e) {
			LOG.logp(Level.SEVERE, this.getClass().getCanonicalName(), "executeComputeTask", "An exception has ocurred", e);
			txn.rollback();
			return Response.serverError().build();
		} //Simulates 5m execution
		
		
		txn.commit();
		return Response.ok().build();
	}
	
	
	
//	@Authorize
//	@POST
//	@Path("/addhours")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response doAddHours(RequestData data) {
//		LOG.warning("Trying to add points to users");
//		
//		Transaction txn = datastore.newTransaction();
//		
//		try {
//		
//			data.getUsers().forEach(user->{
//				Key userKey = database.getUserKey(user);
//				Entity userEntity = txn.get(userKey);
//				if(userEntity!=null) {
//					long hours = userEntity.getLong("user_hours")+data.getMinutes();
//					userEntity = Entity.newBuilder(userEntity)
//							.set("user_hours", hours)
//							.build();
//					txn.update(userEntity);
//					LOG.warning("Points added to User : " + user);
//				}
//			});
//			txn.commit();
//			return Response.ok(" {} ").build();
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
	
	
	//-------------------------------3 listagens
	
	
	@Authorize
	@POST
	@Path("/listCreatedActivities")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doCreatedBy(@QueryParam("username") String username, String startCursorString, @Context HttpHeaders header) {

		
		
		
		Transaction txn = datastore.newTransaction();
		
		// for private/public profile see
		
		String self = getUsername(header);
		
		
		if(!self.equals(username)) {
			Key userkey = database.getUserKey(username);
			Entity userEntity = txn.get(userkey);
			
			if(userEntity == null) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			if(userEntity.getString("user_profile").equals(Profile.PRIVATE.toString())) {
				txn.rollback();
				LOG.warning("Private User");
				return Response.ok(" {} ").build(); 
			}
		}
		
		//added for private / public profile see
		
		
		int pageSize;
		
		Cursor startCursor = null;
		
		if(startCursorString !=null && !startCursorString.equals("")) {
			startCursor = Cursor.fromUrlSafe(startCursorString);
		}
		
		
		
		LOG.warning("Doing list created activities");
		
		try {
			
			
			
			pageSize = 6;

			
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("CreatedActivityBy")
					.setFilter(
//							CompositeFilter.and(
//									)
//							)
							PropertyFilter.hasAncestor(datastore.newKeyFactory().setKind("User").newKey(username))
//							PropertyFilter.eq("created_by", username)
							)
					.setLimit(pageSize)
					.setStartCursor(startCursor)
					.build();
			
			
			QueryResults<Entity> createdBy = datastore.run(query);
			List<ActivitiesData> activities = new ArrayList<>();
			
			createdBy.forEachRemaining(activity ->{
//				activities.add(activity.getString("activity_ID"));
//				activities.add(activity.getString("activity_title"));
				ActivitiesData newAct = new ActivitiesData();
				newAct.setID(activity.getString("activity_ID"));
				newAct.setTitle(activity.getString("activity_title"));
				
				activities.add(newAct);
			});
			
			
			Cursor cursor = createdBy.getCursorAfter();
			
			String cursorString = null;
			
			if(cursor!=null) {
				cursorString = cursor.toUrlSafe();
			}
////			
			
			RequestData data = new RequestData(activities, cursorString);
			
			
			txn.commit();
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
	@Path("/listPastActivities")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetPastActivities(@QueryParam("username") String username, String startCursorString, @Context HttpHeaders header) {
		
//		String username = getUsername(header); 
		
		Transaction txn = datastore.newTransaction();
		
		// for private/public profile see
		
		String self = getUsername(header);
				
				
		if(!self.equals(username)) {
			Key userkey = database.getUserKey(username);
			Entity userEntity = txn.get(userkey);
					
			if(userEntity == null) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
					
			if(userEntity.getString("user_profile").equals(Profile.PRIVATE.toString())) {
				txn.rollback();
				LOG.warning("Private User");
				return Response.ok(" {} ").build(); 
			}
		}
				
				//added for private / public profile see		
		
		
		
		int pageSize;
		
		Cursor startCursor = null;
		
		if(startCursorString !=null && !startCursorString.equals("")) {
			startCursor = Cursor.fromUrlSafe(startCursorString);
		}
		
		
		
		LOG.warning("Doing list past activities");
//		LOG.warning(toTimestamp("2010-10-01T22:22:00Z").toString());
		
		try {

			pageSize = 6;
			
			Calendar cal = Calendar.getInstance();
			
			Timestamp today = Timestamp.of(cal.getTime());

			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("UserJoinedActivity")
					.setFilter(
							CompositeFilter.and(PropertyFilter.eq("user", username), 
									PropertyFilter.le	("activity_date", today))
							
							)
					.setLimit(pageSize)
					.setStartCursor(startCursor)
					.build();

			
			
			QueryResults<Entity> createdQuery = datastore.run(query);
			
			List<ActivitiesData> activities = new ArrayList<>();
			
			createdQuery.forEachRemaining(activity -> {
				ActivitiesData newAct = new ActivitiesData();
				newAct.setID(activity.getString("activity_ID"));
				newAct.setTitle(activity.getString("activity_title"));
				newAct.setActivityOwner(activity.getString("owner"));
				newAct.setActivityTime(activity.getLong("activity_time"));
				
				activities.add(newAct);
			});
			
			Cursor cursor = createdQuery.getCursorAfter();
			
			String cursorString = null;
			
			if(cursor!=null) {
				cursorString = cursor.toUrlSafe();
			}
////			
			
			RequestData data = new RequestData(activities, cursorString);
			
			
			txn.commit();
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
	@Path("/listJoinedActivities")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetJoinedActivities(@QueryParam("username") String username, String startCursorString, @Context HttpHeaders header) {
		
//		String username = getUsername(header); 
		
		Transaction txn = datastore.newTransaction();
		
		// for private/public profile see
		
		String self = getUsername(header);
				
				
		if(!self.equals(username)) {
			Key userkey = database.getUserKey(username);
			Entity userEntity = txn.get(userkey);
					
			if(userEntity == null) {
				txn.rollback();
				LOG.warning("No such user");
				return Response.status(Status.FORBIDDEN).build();
			}
					
			if(userEntity.getString("user_profile").equals(Profile.PRIVATE.toString())) {
				txn.rollback();
				LOG.warning("Private User");
				return Response.ok(" {} ").build(); 
			}
		}
				
				//added for private / public profile see

		
		
		
		int pageSize;
		Cursor startCursor = null;
		
		if(startCursorString !=null && !startCursorString.equals("")) {
			startCursor = Cursor.fromUrlSafe(startCursorString);
		}
		
		
		LOG.warning("Doing list joined activities");
		
		try {

			pageSize = 6;

			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("UserJoinedActivity")
					.setFilter(
							PropertyFilter.eq("user", username))
					.setLimit(pageSize)
					.setStartCursor(startCursor)
					.build();

			
			QueryResults<Entity> createdQuery = datastore.run(query);
			
			List<ActivitiesData> activities = new ArrayList<>();
			
			createdQuery.forEachRemaining(activity -> {
				ActivitiesData newAct = new ActivitiesData();
				newAct.setID(activity.getString("activity_ID"));
				newAct.setTitle(activity.getString("activity_title"));
				newAct.setActivityOwner(activity.getString("owner"));
				newAct.setActivityTime(activity.getLong("activity_time"));
				newAct.setDate(activity.getTimestamp("activity_date").toString()); //added
				
				activities.add(newAct);
			});
			
			Cursor cursor = createdQuery.getCursorAfter();
			
			String cursorString = null;
			
			if(cursor!=null) {
				cursorString = cursor.toUrlSafe();
			}
			
			RequestData data = new RequestData(activities, cursorString);
			
			txn.commit();
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
	
	
	
	
	
//	@Authorize
//	@GET
//	@Path("/listCreatedActivities")
////	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response doGetCreatedActivities(@Context HttpHeaders header) {
//		
//		String username = getUsername(header);
//		
//		Transaction txn = datastore.newTransaction();
//		
//
//		
//		
//		try {
//
//			
//
//			Query<Entity> query = Query.newEntityQueryBuilder()
//					.setKind("CreatedActivityBy")
//					.setFilter(
//							PropertyFilter.eq("created_by", username))
//					.build();
//
//			
//			QueryResults<Entity> createdQuery = datastore.run(query);
//			
//			List<ActivitiesData> activities = new ArrayList<>();
//			
//			createdQuery.forEachRemaining(activity -> {
//				ActivitiesData newAct = new ActivitiesData();
//				newAct.setID(activity.getString("activity_ID"));
//				newAct.setTitle(activity.getString("activity_title"));
//				
//				activities.add(newAct);
//			});
//			
//			
//			
//			
//			txn.commit();
//			return Response.status(Status.OK).entity(g.toJson(activities)).build();
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

	
	
	
//	@Authorize
//	@GET
//	@Path("/listJoinedActivities")
////	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response doGetJoinedActivities(@Context HttpHeaders header) {
//		
//		String username = getUsername(header); 
//		
//		Transaction txn = datastore.newTransaction();
//		
//
//		
//		try {
//
//			
//
//			Query<Entity> query = Query.newEntityQueryBuilder()
//					.setKind("UserJoinedActivity")
//					.setFilter(
//							PropertyFilter.eq("user", username))
//					.build();
//
//			
//			QueryResults<Entity> createdQuery = datastore.run(query);
//			
//			List<ActivitiesData> activities = new ArrayList<>();
//			
//			createdQuery.forEachRemaining(activity -> {
//				ActivitiesData newAct = new ActivitiesData();
//				newAct.setID(activity.getString("activity_ID"));
//				newAct.setTitle(activity.getString("activity_title"));
//				newAct.setActivityOwner(activity.getString("owner"));
//				newAct.setActivityTime(activity.getLong("activity_time"));
//				newAct.setDate(activity.getTimestamp("activity_date").toString()); //added
//				
//				activities.add(newAct);
//			});
//			
//			
//			
//			
//			txn.commit();
//			return Response.status(Status.OK).entity(g.toJson(activities)).build();
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
//		
//	}
	
	
	@Authorize
	@GET
	@Path("/listCertificateActivities")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetCertificateActivities(@Context HttpHeaders header) {
		
		String username = getUsername(header); 
		
		Transaction txn = datastore.newTransaction();
		

		
		try {

			Calendar cal = Calendar.getInstance();
			
			Timestamp today = Timestamp.of(cal.getTime());

			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("UserJoinedActivity")
					.setFilter(
							CompositeFilter.and(
									PropertyFilter.eq("user", username), 
									PropertyFilter.le("activity_date",today),
									PropertyFilter.eq("is_org", true) 
									)
							)
					.build();

			
			QueryResults<Entity> createdQuery = datastore.run(query);
			
			List<ActivitiesData> activities = new ArrayList<>();
			
			createdQuery.forEachRemaining(activity -> {
				ActivitiesData newAct = new ActivitiesData();
				newAct.setID(activity.getString("activity_ID"));
				newAct.setTitle(activity.getString("activity_title"));
				newAct.setActivityOwner(activity.getString("owner"));
				newAct.setActivityTime(activity.getLong("activity_time"));
				
				activities.add(newAct);
			});
			
			
			
			
			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(activities)).build();
			
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
