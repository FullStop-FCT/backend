package pt.unl.fct.di.apdc.helpinhand.api;

import java.util.List;

import com.google.cloud.datastore.Cursor;

public class RequestData {

	
	private AuthToken token;
	private UsersData userData;
	private ActivitiesData activityData;
	private RoutesData routeData;
	
	private String userToDelete;
	private String userToChange;
	private String attribute;
	
	private String username;


	private String password;
	
	private List<?> results;
//	private int pageSize;
	private Cursor cursor;
	
	private String cursorString;
	
	public RequestData() {
		
	}
	
	
	public RequestData(List<?> results ,Cursor cursor) {
		this.results=results;
//		this.pageSize=pageSize;
		this.setCursor(cursor);
	}
	
	public RequestData(List<?> results ,String cursorString) {
		this.results=results;
//		this.pageSize=pageSize;
		this.cursorString=cursorString;
	}
	
	public RequestData(AuthToken token, String userToDelete) {
		this.token = token;
		this.userToDelete = userToDelete;
		//delete
	}
	
	public RequestData(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public RequestData(AuthToken token, UsersData userData) {
		this.token = token;
		this.userData = userData;
		//update
	}
	
	
	public RequestData(AuthToken token, ActivitiesData activityData) {
		this.token=token;
		this.activityData = activityData;
	}
	
	public RequestData(AuthToken token, RoutesData routeData) {
		this.token=token;
		this.setRouteData(routeData);
	}
	
	public RequestData(AuthToken token, String userToChange, String attribute) {
		this.token = token;
		this.userToChange = userToChange;
		this.attribute = attribute;
		//role and state
	}

	
	public String getUserToChange() {
		return userToChange;
	}

	public void setUserToChange(String userToChange) {
		this.userToChange = userToChange;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String roleToChange) {
		this.attribute = roleToChange;
	}

	public AuthToken getToken() {
		return token;
	}

	public void setToken(AuthToken token) {
		this.token = token;
	}

	public String getUserToDelete() {
		return userToDelete;
	}

	public void setUserToDelete(String userToDelete) {
		this.userToDelete = userToDelete;
	}

	public UsersData getUserData() {
		return userData;
	}

	public void setUserData(UsersData userData) {
		this.userData = userData;
	}
	
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public ActivitiesData getActivityData() {
		return activityData;
	}

	public void setActivityData(ActivitiesData activityData) {
		this.activityData = activityData;
	}

	public RoutesData getRouteData() {
		return routeData;
	}

	public void setRouteData(RoutesData routeData) {
		this.routeData = routeData;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Cursor getCursor() {
		return cursor;
	}


	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}


	public List<?> getResults() {
		return results;
	}


	public void setResults(List<?> results) {
		this.results = results;
	}
}
