package pt.unl.fct.di.apdc.helpinhand.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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
	private List<String> participants;
	private String ID;
	
	
	public ActivitiesData() {
		
	}
	
	public ActivitiesData(String title, String description, String date, String location, 
			long totalParticipants, String activityOwner, String category, String lat, String lon ) {
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


	
	public void addParticipant(String username) {
		participants.add(username);
	}
	
	public List<String> getList() {
		return participants;
	}
	
	public void removeParticipant(String username) {
		Iterator it = participants.iterator();
		while(it.hasNext()) {
			int i = (int) it.next();
			if(participants.get(i).equals(username))
				participants.remove(i);
		}
		
	}


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

}
