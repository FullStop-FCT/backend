package pt.unl.fct.di.apdc.helpinhand.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import pt.unl.fct.di.apdc.helpinhand.api.Location;

import com.google.cloud.datastore.Value;

public class ActivitiesData {

	private String title;
	private String description;
	private String date;
	private String location;
	private String lat;
	private String lon;
	private long totalParticipants;
	private String activityOwner;
	private String category;
	private long participants;
	private String ID;
	//private long points;
	private String startHour;
	private String endHour;
	private List<String> keywords;
	private List<String> waypoints;
	private long activityTime;

	
	public ActivitiesData() {
		
	}
	
	public ActivitiesData(String title, String description, String date, String location, 
			long totalParticipants, String activityOwner, String category, String lat, String lon, 
			String startHour, String endHour, List<String> keywords, List<String> waypoints, long activityTime) {
//		this.ID = UUID.randomUUID().fromString(title).toString();
		this.ID = UUID.randomUUID().randomUUID().toString();
		this.title=title;
		this.description=description;
		this.date=date;
		this.location=location;
		this.totalParticipants=totalParticipants;
		this.category = category;
//		this.participants = new ArrayList<>(totalParticipants);
		this.activityOwner=activityOwner;
		this.setLat(lat);
		this.setLon(lon);
		this.setStartHour(startHour);
		this.setEndHour(endHour);
//		this.setPoints(100);
		this.keywords = keywords;
		this.participants =0;
		this.waypoints=waypoints;
		this.setActivityTime(activityTime);
		
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public long getTotalParticipants() {
		return totalParticipants;
	}


	public void setTotalParticipants(long peopleNumber) {
		this.totalParticipants = peopleNumber;
	}


	
//	public void addParticipant(String username) {
//		participants.add(username);
//	}
//	
//	public List<String> getList() {
//		return participants;
//	}
//	
//	public void removeParticipant(String username) {
//		Iterator it = participants.iterator();
//		while(it.hasNext()) {
//			int i = (int) it.next();
//			if(participants.get(i).equals(username))
//				participants.remove(i);
//		}
//		
//	}


	public String getActivityOwner() {
		return activityOwner;
	}


	public void setActivityOwner(String activityOwner) {
		this.activityOwner = activityOwner;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

//	public long getPoints() {
//		return points;
//	}
//
//	public void setPoints(long points) {
//		this.points = points;
//	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public long getParticipants() {
		return participants;
	}

	public void setParticipants(long participants) {
		this.participants = participants;
	}

	public List<String> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<String> waypoints) {
		this.waypoints = waypoints;
	}

	public long getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(long activityTime) {
		this.activityTime = activityTime;
	}


}
