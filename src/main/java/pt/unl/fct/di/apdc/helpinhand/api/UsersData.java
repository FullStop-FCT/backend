package pt.unl.fct.di.apdc.helpinhand.api;

import java.text.DateFormat;
import java.util.regex.Pattern;


import pt.unl.fct.di.apdc.helpinhand.util.Profile;
import pt.unl.fct.di.apdc.helpinhand.util.State;
import pt.unl.fct.di.apdc.helpinhand.util.Kinds;

public class UsersData {

	private String username;

	private String name;
	private String email;
	private String password;
	private String confirmation;
	private String profile;
	private String phoneNumber;
	private String mobileNumber;
	private String address;
	private String extraAddress;
	private String location;
	private String postalCode;
	private String birthday;
	private String gender;

	private String role;
	private String kind;
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
		this.email = email;
		this.password = password;
		this.confirmation = confirmation;
//		this.phoneNumber = "";
//		this.mobileNumber = "";
//		this.address = "";
//		this.extraAddress = "";
//		this.location = "";
//		this.profile = state.toString();
//		this.state = State.ENABLED.toString();
//		this.kind = "";
		
	}
	
	public UsersData(String profile, String phoneNumber, String mobileNumber, 
			String address, String location, String postalCode,
			String birthday, String gender, String kind) {
//		User user = new User(this.username, this.name, this.email, this.password, this.confirmation);
//		
//		if(profile !=null)
//			user.setProfile(profile);
//		if(phoneNumber!=null)
//			user.setPhoneNumber(phoneNumber);
//		if(phoneNumber!=null)
//			user.setMobileNumber(mobileNumber);
//		if(address!=null)
//			user.setAddress(address);
//		if(extraAddress!=null)
//			user.setExtraAddress(extraAddress);
//		if(location!=null)
//			user.setLocation(location);
//		if(postalCode!=null)
//			user.setPostalCode(postalCode);
//		if(birthday!=null)
//			user.setBirthday(birthday);
//		if(gender!=null)
//			user.setGender(gender);
//		if(kind!=null)
//			user.setKind(kind);
		this.profile=profile;
		this.phoneNumber=phoneNumber;
		this.mobileNumber=mobileNumber;
		this.address=address;
//		this.extraAddress=extraAddress;
		this.location=location;
		this.postalCode=postalCode;
		this.birthday=birthday;
		this.gender=gender;
		this.kind=kind;
		
		
	}
	
	
//	public User(String username, String email, String password, 
//			String confirmation, String profile, String phoneNumber, 
//			String mobileNumber, String address, String extraAddress,
//			String location) {
//
//		this.username = username;
//		this.email = email;
//		this.password = password;
//		this.confirmation = confirmation;
//		this.profile = profile;
//		this.phoneNumber = phoneNumber;
//		this.mobileNumber = mobileNumber;
//		this.address = address;
//		this.extraAddress = extraAddress;
//		this.location = location;
//		this.role = "USER";
//		this.state = "ENABLED";
//	}

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

//	private boolean isValid(String text) {
//		return text != null && !text.isEmpty();
//	}
//
//	public boolean validRegistration() {
//		return this.isValid(this.getUsername())
//				&& this.validatePassword()
//				&& this.getPassword().equals(this.getConfirmation())
//				&& this.validateEmail();
//
//	}
//
//	public boolean validatePassword() {
//
//		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
//		//pelo menos 1 minuscula, pelo menos 1 maiuscula, pelo menos 8 chars, pelo menos 1 digito e sem espacos brancos
//
//		return isValid(password) && password.matches(pattern);
//
//	}
//
//
//	public boolean validateEmail() {
//		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
//
//		return isValid(email) && pattern.matcher(email).matches();
//	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getExtraAddress() {
		return extraAddress;
	}

	public void setExtraAddress(String extraAddress) {
		this.extraAddress = extraAddress;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

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

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}


}
