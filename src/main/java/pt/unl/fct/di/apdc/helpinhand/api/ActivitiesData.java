package pt.unl.fct.di.apdc.helpinhand.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivitiesData {

	private String title;
	private String description;
	private String date;
	private String location;
	private int totalParticipants;
	private String activityOwner;
	private List<String> participants;
	
	
	public ActivitiesData() {
		
	}
	
	public ActivitiesData(String title, String description, String date, String location, int totalParticipants, String activityOwner) {
		this.title=title;
		this.description=description;
		this.date=date;
		this.location=location;
		this.totalParticipants=totalParticipants;		
//		this.participants = new ArrayList<>(totalParticipants);
		this.activityOwner=activityOwner;
		
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


	public int getTotalParticipants() {
		return totalParticipants;
	}


	public void setTotalParticipants(int peopleNumber) {
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
}
