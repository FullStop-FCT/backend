package pt.unl.fct.di.apdc.helpinhand.api;

public class Request {

	
	private AuthToken token;
	private UsersData userData;
	private ActivitiesData activityData;
	private RoutesData routeData;
	
	private String userToDelete;
	private String userToChange;
	private String attribute;
	
	private String username;


	private String password;
	
	public Request() {
		
	}
	
	public Request(AuthToken token, String userToDelete) {
		this.token = token;
		this.userToDelete = userToDelete;
		//delete
	}
	
	public Request(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Request(AuthToken token, UsersData userData) {
		this.token = token;
		this.userData = userData;
		//update
	}
	
	
	public Request(AuthToken token, ActivitiesData activityData) {
		this.token=token;
		this.activityData = activityData;
	}
	
	public Request(AuthToken token, RoutesData routeData) {
		this.token=token;
		this.setRouteData(routeData);
	}
	
	public Request(AuthToken token, String userToChange, String attribute) {
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
}
