package pt.unl.fct.di.apdc.helpinhand.api;

import pt.unl.fct.di.apdc.helpinhand.api.AuthToken;
import pt.unl.fct.di.apdc.helpinhand.api.User;

public class Request {

	
	public AuthToken token;
	public String userToDelete;
	public User userData;
	public String userToChange;
	public String attribute;
	
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
	
	public Request(AuthToken token, User userData) {
		this.token = token;
		this.userData = userData;
		//update
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

	public User getUserData() {
		return userData;
	}

	public void setUserData(User userData) {
		this.userData = userData;
	}
	
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
