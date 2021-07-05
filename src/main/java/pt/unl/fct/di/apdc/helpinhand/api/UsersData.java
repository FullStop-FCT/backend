package pt.unl.fct.di.apdc.helpinhand.api;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.google.cloud.datastore.Value;

import pt.unl.fct.di.apdc.helpinhand.util.Profile;
import pt.unl.fct.di.apdc.helpinhand.util.State;
//import pt.unl.fct.di.apdc.helpinhand.util.Kinds;

public class UsersData {

	private String username;

	private String name;
	private String email;
	private String password;
	private String confirmation;
	private String profile;
	private String phoneNumber;
	private String mobileNumber;
//	private String address;
//	private String extraAddress;
	private String location;
//	private String postalCode;
	private String birthday;
	private String gender;
	//private long points;
	private String image;
	private long hoursDone;
	private long joinedActivities;
	private long followings;
	private long followers;
	private long createdActivities;
	private boolean isOrg;

	

	private String role;
	//private String kind;
	//		private static final String USER = "USER";
	//		private static final String GBO = "GBO"; //back office
	//		private static final String GA = "GA"; //back end
	//		private static final String SU = "SU"; // super user
	//		private static final String ENABLED = "ENABLED"; //enabled state
	//		private static final String DISABLED = "DISABLED"; // disabled state

	private String state;


//	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	public UsersData() {	
	}

	
	//username can also be NIF for orgs
	public UsersData(String username, String name, String email, String password, 
			String confirmation) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.confirmation = confirmation;
		this.hoursDone=0;
		this.isOrg = false;
		this.image =null;
		this.joinedActivities = 0;
		this.followings = 0;
		this.createdActivities = 0;
		
		
	}
	
	public UsersData(String username, String name, String email, String password, 
			String confirmation, boolean isOrg) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.confirmation = confirmation;
		this.hoursDone=0;
		this.isOrg = false;
		this.image =null;
//		this.joinedActivities = 0;
		this.followings = 0;
		this.createdActivities = 0;
		this.followers = 0;
		this.isOrg = isOrg;

	}
	
	public UsersData(String profile, String phoneNumber, String mobileNumber, String location,
			String birthday, String gender, String image) {

		this.profile=profile;
		this.phoneNumber=phoneNumber;
		this.mobileNumber=mobileNumber;
//		this.address=address;
//		this.extraAddress=extraAddress;
		this.location=location;
//		this.postalCode=postalCode;
		this.birthday=birthday;
		this.gender=gender;
		this.image=image;
		this.hoursDone = 0;
//		this.joinedActivities = new ArrayList<>();
//		this.followings = new ArrayList<>();
//		this.createdActivities = new ArrayList<>();
		
	}
	
	


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public String getExtraAddress() {
//		return extraAddress;
//	}
//
//	public void setExtraAddress(String extraAddress) {
//		this.extraAddress = extraAddress;
//	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

//	public String getPostalCode() {
//		return postalCode;
//	}
//
//	public void setPostalCode(String postalCode) {
//		this.postalCode = postalCode;
//	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

//	public String getKind() {
//		return kind;
//	}
//
//	public void setKind(String kind) {
//		this.kind = kind;
//	}


//	public long getPoints() {
//		return points;
//	}
//

//	public void setPoints(long points) {
//		this.points = points;
//	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public long getHoursDone() {
		return hoursDone;
	}


	public void setHoursDone(long hoursDone) {
		this.hoursDone = hoursDone;
	}


	public long getJoinedActivities() {
		return joinedActivities;
	}


	public void setJoinedActivities(long number) {
		this.joinedActivities = number;
	}


	public long getFollowings() {
		return followings;
	}


	public void setFollowings(long number) {
		this.followings = number;
	}


	public long getCreatedActivities() {
		return createdActivities;
	}


	public void setCreatedActivities(long number) {
		this.createdActivities = number;
	}


	public boolean isOrg() {
		return isOrg;
	}


	public void setOrg(boolean isOrg) {
		this.isOrg = isOrg;
	}


	public long getFollowers() {
		return followers;
	}


	public void setFollowers(long followers) {
		this.followers = followers;
	}


}
