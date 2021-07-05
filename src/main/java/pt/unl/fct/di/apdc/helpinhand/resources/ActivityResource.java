package pt.unl.fct.di.apdc.helpinhand.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.repackaged.com.google.common.collect.Iterators;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
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

import pt.unl.fct.di.apdc.helpinhand.api.ActivitiesData;
import pt.unl.fct.di.apdc.helpinhand.api.AuthToken;
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
//			Key activityKey = database.getActivityKey(request.getActivityData());
			ActivitiesData act = new ActivitiesData(
					request.getActivityData().getTitle(), 
					request.getActivityData().getDescription(),
					request.getActivityData().getDate(), 
					request.getActivityData().getLocation(),
					request.getActivityData().getTotalParticipants(),
					request.getToken().getUsername(),
					request.getActivityData().getCategory(),
					request.getActivityData().getLat(),
					request.getActivityData().getLon(),
					request.getActivityData().getStartHour(), //added
					request.getActivityData().getEndHour(), 
					request.getActivityData().getKeywords()
					);
			
			
			
					
			Key activityKey = factory
					.addAncestor(PathElement.of("User", act.getActivityOwner()))
					.setKind("Activity")
					.newKey(act.getID());
			
			Key userKey = database.getUserKey(request.getToken().getUsername());
			
			Entity userEntity = txn.get(userKey);
			
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
						.set("activity_owner", request.getToken().getUsername())
						.set("activity_lat", request.getActivityData().getLat())
						.set("activity_lon", request.getActivityData().getLon())
						.set("activity_startHour", request.getActivityData().getStartHour())
						.set("activity_endHour", request.getActivityData().getEndHour())
						
						//.set("activity_participants", ListValue.newBuilder().build())
//						.set("activity_keywords", ListValue.of(request.getActivityData().getKeywords()))
//						.set("activity_keywords", ListValue.newBuilder().set(request.getActivityData().getKeywords()).build())
						.set("activity_keywords", convertToValueList(request.getActivityData().getKeywords()))
						.build();
				
				createdActivityEntity = Entity.newBuilder(createdKey)
						.set("created_by", request.getToken().getUsername())
						.set("activity_title", request.getActivityData().getTitle())
						.build();
				
				txn.add(activityEntity, createdActivityEntity);
				
				long createdActivities = userEntity.getLong("created_activities")+1;
				
				Entity newUser = Entity.newBuilder(userEntity)
						.set("created_activities", createdActivities)
						.build();
				txn.update(newUser);
				
//				userEntity = Entity.newBuilder(userKey)
//						.set("created_activities", createdActivities)
//						.build();
//				txn.update(userEntity);
				
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

	
	
	
	@POST
	@Path("/createdBy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doCreatedBy(AuthToken token, @QueryParam("username") String username) {
		Transaction txn = datastore.newTransaction();
		
		Key tokenKey = database.getTokenKey(token);
		
		Entity tokenEntity = txn.get(tokenKey);
		
		try {
			
			
			if(tokenEntity == null) {
				txn.rollback();
				LOG.warning("Token Authentication Failed");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("CreatedActivityBy")
					.setFilter(
//							CompositeFilter.and(
//									)
//							)
							PropertyFilter.hasAncestor(datastore.newKeyFactory().setKind("User").newKey(username))
//							PropertyFilter.eq("created_by", username)
							)
					.build();
			
			
			QueryResults<Entity> createdBy = datastore.run(query);
			List<String> activities = new ArrayList<>();
			
			createdBy.forEachRemaining(activity ->{
				activities.add(activity.getString("activity_title"));
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
	
	

//join activity
	
	@POST
	@Path("/join/{activityID}/{activityOwner}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doJoin(AuthToken token, @PathParam("activityID") String activityID, @PathParam("activityOwner") String activityOwner) {
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
		
		
			Key joinKey = datastore.newKeyFactory()
					.addAncestors(PathElement.of("User", token.getUsername()), PathElement.of("Activity", activityID))
					.setKind("UserJoinedActivity")
					.newKey(token.getUsername());
			
			Key activityKey = datastore.newKeyFactory()
					.addAncestor(PathElement.of("User", activityOwner))
					.setKind("Activity")
					.newKey(activityID);
			
			Entity joinedEntity = txn.get(joinKey);
			
			if(joinedEntity != null) {
				txn.rollback();
				LOG.warning("This follow already exists");
				return Response.status(Status.BAD_REQUEST).entity("Follow already exists.").build();
			}
			
			Entity activityEntity = txn.get(activityKey);
			
			
			long participants = activityEntity.getLong("activity_participants");
			long total = activityEntity.getLong("activity_total_participants");
//			String [] numbers = participants.split("/",2);
//			int part = Integer.valueOf(numbers[0]);
//			int total = Integer.valueOf(numbers[1]);
//			part++;
			
			if(participants>total) {
				txn.rollback();
				LOG.warning("This activity is full :(");
				return Response.status(Status.BAD_REQUEST).entity("Activity is full :(").build();
			}
			activityEntity = Entity.newBuilder(datastore.get(activityKey))
					.set("activity_participants", participants)
					.build();
			
			joinedEntity = Entity.newBuilder(joinKey)
					.set("activity_ID", activityID)
					.set("activity_title", activityEntity.getString("activity_title"))
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

	
//add hours
	
		
//	
	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doSearch(AuthToken token, @QueryParam("keyword") String keyword) {
		
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
				ActivitiesData newAct = new ActivitiesData();
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
				//newAct.setParticipants(convertToList(activity.getList("activity_participants")));
				
				activities.add(newAct);
				
				
			});
			
			//int count = Iterators.size(datastore.run(query));
			//LOG.warning("size: " + count);
			
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
				ActivitiesData newAct = new ActivitiesData();
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
				//newAct.setParticipants(convertToList(activity.getList("activity_participants")));
				
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
//	

	//LISTA TODAS - ADICIONAR O FILTRO PARA LISTAR SO AS DO USER
	@POST
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doListUsers(AuthToken token) {
		
		Transaction txn = datastore.newTransaction();
		
		Key tokenKey = database.getTokenKey(token);
		
		Entity tokenEntity = txn.get(tokenKey);
		
		
		try {
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
			
//			Calendar cal = Calendar.getInstance();
//			Timestamp today = Timestamp.of(cal.getTime());
			
			
//			Query<Entity> query = Query.newEntityQueryBuilder()
//									.setKind("Activity")
//									.setFilter(
//											CompositeFilter.and(
//													PropertyFilter.hasAncestor(
//															datastore.newKeyFactory().setKind("User").newKey(token.getUsername())),
//													PropertyFilter.ge("activity_date", today)
//													)
//											)
//									.setOrderBy(OrderBy.desc("activity_date"))
//									.setLimit(10)
//									.build();
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Activity")
//					.setFilter(
//									PropertyFilter.hasAncestor(
//											datastore.newKeyFactory().setKind("User").newKey(token.getUsername()))
//							)
					.setOrderBy(OrderBy.desc("activity_title"))
					.setLimit(10)
					.build();
										
						
			QueryResults<Entity> titlesQuery = datastore.run(query);
			
			List<ActivitiesData> activities = new ArrayList<>();
			
			titlesQuery.forEachRemaining(activity -> {
				ActivitiesData nextActivity = new ActivitiesData();
//				nextActivity.s(activity.getKey().getId())
//				nextActivity.setID(activity.getKey().getName());
				nextActivity.setID(activity.getKey().getName());
				nextActivity.setTitle(activity.getString("activity_title"));
				nextActivity.setDescription(activity.getString("activity_description"));
				nextActivity.setCategory(activity.getString("activity_category"));
				nextActivity.setLocation(activity.getString("activity_location"));
				nextActivity.setParticipants(activity.getLong("activity_participants"));
				nextActivity.setTotalParticipants(activity.getLong("activity_total_participants"));
				nextActivity.setDate(activity.getString("activity_date"));
				nextActivity.setActivityOwner(activity.getString("activity_owner"));
				
				activities.add(nextActivity);
			});
			
			
			txn.commit();
//			return Response.ok(" {} ").build();
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
	
	
	@POST
	@Path("/get/{activityID}/{activityOwner}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetActivity(AuthToken token, @PathParam("activityID") String activityID, @PathParam("activityOwner") String activityOwner) {
		Transaction txn = datastore.newTransaction();
		
		Key tokenKey = database.getTokenKey(token);
		
		Entity tokenEntity = txn.get(tokenKey);
		
		Key activityKey = datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", activityOwner))
				.setKind("Activity")
				.newKey(activityID);
		
		try {
			
			Entity activityEntity = txn.get(activityKey);
			
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
		
			if(activityEntity == null) {
				txn.rollback();
				LOG.warning("No such activity");
				return Response.status(Status.FORBIDDEN).build();
			}
			
			ActivitiesData newActivity = new ActivitiesData();
			
			newActivity.setID(activityEntity.getKey().getName());
			newActivity.setTitle(activityEntity.getString("activity_title"));
			newActivity.setDescription(activityEntity.getString("activity_description"));
			newActivity.setDate(activityEntity.getString("activity_date"));
			newActivity.setLocation(activityEntity.getString("activity_location"));
			newActivity.setParticipants(activityEntity.getLong("activity_participants"));
			newActivity.setTotalParticipants(activityEntity.getLong("activity_total_participants"));
			newActivity.setCategory(activityEntity.getString("activity_category"));
			newActivity.setActivityOwner(activityOwner);
			newActivity.setLat(activityEntity.getString("activity_lat"));
			newActivity.setLon(activityEntity.getString("activity_lon"));
			newActivity.setStartHour(activityEntity.getString("activity_startHour"));
			newActivity.setEndHour(activityEntity.getString("activity_endHour"));
			//newActivity.setParticipants(activityEntity.getList("activity_participants"));
			newActivity.setKeywords(convertToList(activityEntity.getList("activity_keywords")));
			
			
			txn.commit();
			return Response.status(Status.OK).entity(g.toJson(newActivity)).build();
			


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
	@Path("/get/{username}/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response goGetActivity(@PathParam ("username") String username, @PathParam("title") String title) {
		
		Transaction txn = datastore.newTransaction();
		
				
		
		
		try {
			
			
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Activity")
					.setFilter(
									PropertyFilter.hasAncestor(
											datastore.newKeyFactory().setKind("User").newKey(username))
							)
//					.setOrderBy(OrderBy.desc("activity_title"))
					.setFilter(
							StructuredQuery.PropertyFilter.eq("activity_title", title))
					.build();
										
						
			QueryResults<Entity> titlesQuery = datastore.run(query);

//			titlesQuery.g
			
//			List<ActivitiesData> activities = new ArrayList<>();
			ActivitiesData nextActivity = new ActivitiesData();
			
			titlesQuery.forEachRemaining(activity -> {
//				ActivitiesData nextActivity = new ActivitiesData();
//				nextActivity.s(activity.getKey().getId())
//				nextActivity.setID(activity.getKey().getId());
				nextActivity.setTitle(activity.getString("activity_title"));
				nextActivity.setDescription(activity.getString("activity_description"));
				nextActivity.setCategory(activity.getString("activity_category"));
				nextActivity.setLocation(activity.getString("activity_location"));
				nextActivity.setParticipants(activity.getLong("activity_participants"));
				nextActivity.setTotalParticipants(activity.getLong("activity_total_participants"));
				nextActivity.setDate(activity.getString("activity_date"));
				nextActivity.setActivityOwner(activity.getString("activity_owner"));
				
//				activities.add(nextActivity);
			});
			
			
			txn.commit();
//			return Response.ok(" {} ").build();
			return Response.status(Status.OK).entity(g.toJson(nextActivity)).build();
//			
//			ActivitiesData newActivity = new ActivitiesData();
//			
//			newActivity.setTitle(activityEntity.getString("activity_title"));
//			newActivity.setDescription(activityEntity.getString("activity_description"));
//			newActivity.setDate(activityEntity.getString("activity_date"));
//			newActivity.setLocation(activityEntity.getString("activity_location"));
//			newActivity.setTotalParticipants(activityEntity.getLong("activity_total_participants"));
//			newActivity.setActivityOwner(activityEntity.getString("activity_owner"));
//			newActivity.setCategory(activityEntity.getString("activity_category"));
//				
//			txn.commit();
////			return Response.ok(" {} ").build();
//			return Response.status(Status.OK).entity(g.toJson(newActivity)).build();
			
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
	@Path("/get/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetActivityByTitle( @PathParam("title") String title) {
		Transaction txn = datastore.newTransaction();
			
		try {
 
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Activity")
//					.setFilter(
//									PropertyFilter.hasAncestor(
//											datastore.newKeyFactory().setKind("User").newKey(username))
//							)
//					.setOrderBy(OrderBy.desc("activity_title"))
					.setFilter(
//							StructuredQuery.PropertyFilter.
							StructuredQuery.PropertyFilter.eq("activity_title", title))
					.build();
										
						
			QueryResults<Entity> titlesQuery = datastore.run(query);

//			titlesQuery.g
			
//			List<ActivitiesData> activities = new ArrayList<>();
			ActivitiesData nextActivity = new ActivitiesData();
			
			titlesQuery.forEachRemaining(activity -> {
//				ActivitiesData nextActivity = new ActivitiesData();
//				nextActivity.s(activity.getKey().getId())
//				nextActivity.setID(activity.getKey().getId());
				nextActivity.setTitle(activity.getString("activity_title"));
				nextActivity.setDescription(activity.getString("activity_description"));
				nextActivity.setCategory(activity.getString("activity_category"));
				nextActivity.setLocation(activity.getString("activity_location"));
				nextActivity.setParticipants(activity.getLong("activity_participants"));
				nextActivity.setTotalParticipants(activity.getLong("activity_total_participants"));
				nextActivity.setDate(activity.getString("activity_date"));
				nextActivity.setActivityOwner(activity.getString("activity_owner"));
				
//				activities.add(nextActivity);
			});
			
			
			txn.commit();
//			return Response.ok(" {} ").build();
			return Response.status(Status.OK).entity(g.toJson(nextActivity)).build();
//			
//			ActivitiesData newActivity = new ActivitiesData();
//			
//			newActivity.setTitle(activityEntity.getString("activity_title"));
//			newActivity.setDescription(activityEntity.getString("activity_description"));
//			newActivity.setDate(activityEntity.getString("activity_date"));
//			newActivity.setLocation(activityEntity.getString("activity_location"));
//			newActivity.setTotalParticipants(activityEntity.getLong("activity_total_participants"));
//			newActivity.setActivityOwner(activityEntity.getString("activity_owner"));
//			newActivity.setCategory(activityEntity.getString("activity_category"));
//				
//			txn.commit();
////			return Response.ok(" {} ").build();
//			return Response.status(Status.OK).entity(g.toJson(newActivity)).build();
			
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
	
	
	
	@DELETE
	@Path("/delete/{title}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doDelete(AuthToken token, @PathParam("title") String title) {
		
		Transaction txn = datastore.newTransaction();
		
		Key tokenKey = database.getTokenKey(token);
		
		Entity tokenEntity = txn.get(tokenKey);
		
		try {
			
			if(tokenEntity == null || System.currentTimeMillis()>token.getExpirationData()) {
				txn.rollback();
				LOG.warning("Token Authentication Failed");
				return Response.status(Status.FORBIDDEN).build();
			}
			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Activity")
					.setFilter(
									PropertyFilter.hasAncestor(
											datastore.newKeyFactory().setKind("User").newKey(token.getUsername()))
							)
//					.setOrderBy(OrderBy.desc("activity_title"))
					.setFilter(PropertyFilter.eq("activity_title", title))
					.setLimit(1)
					.build();
			
			QueryResults<Entity> titlesQuery = datastore.run(query);
			
			
			
			titlesQuery.forEachRemaining(activity -> {
//				Entity activityEntity= txn.get(activity.getKey());
//				Entity deletedEntity = Entity.newBuilder(activityEntity).build();
//				txn.put(deletedEntity);
				Entity deleted = txn.get(activity.getKey());
				if(deleted.equals(null)) {
					txn.rollback();
					LOG.warning("No such activity");
				}
				txn.delete(activity.getKey());
			});
			
			txn.commit();
			return Response.ok(" {} ").build(); 
			
//			if(activityKey.equals(null)) {
//				txn.rollback();
//				LOG.warning("No such activity");
//				return Response.status(Status.BAD_REQUEST).entity("No such activity.").build();
//			}
			
			
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
