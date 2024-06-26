package pt.unl.fct.di.apdc.helpinhand.util;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.helpinhand.api.UsersData;
import pt.unl.fct.di.apdc.helpinhand.data.Database;

public class Verification {

	private static final Logger LOG = Logger.getLogger(Verification.class.getName());
	private final Gson g = new Gson();
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	Database database = new Database();
	
	public Verification() {
		
	}
	

	
	public boolean validatePassword(String password) {

		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
		//pelo menos 1 minuscula, pelo menos 1 maiuscula, pelo menos 8 chars, pelo menos 1 digito e sem espacos brancos

		return isValid(password) && password.matches(pattern);

	}
	
	public boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

		return isValid(email) && pattern.matcher(email).matches();
	}
	
	
	private boolean isValid(String text) {
		return text != null && !text.isEmpty();
	}
	
	public boolean validRegistration(UsersData user) {
		return this.isValid(user.getUsername())
				&& this.validatePassword(user.getPassword())
				&& user.getPassword().equals(user.getConfirmation())
				&& this.validateEmail(user.getEmail());

	}
	
	public boolean existingEmail(String email) {
		Query<Entity> query = Query.newEntityQueryBuilder()
				.setKind("User")
				.setFilter(
						StructuredQuery.PropertyFilter.eq("user_email", email))
				.build();
		
		QueryResults<Entity> emailQuery = datastore.run(query);
	
		if(emailQuery.hasNext())
			return true;

		return false;
	}
	
	public boolean existingUsername(String username) {
		Key userKey = database.getUserKey(username);
		
		Query<Entity> query = Query.newEntityQueryBuilder()
				.setKind("User")
				.setFilter(
						StructuredQuery.PropertyFilter.eq("__key__", userKey))
				.build();
		QueryResults<Entity> userQuery = datastore.run(query);
		
		if(userQuery.hasNext())
			return true;
		
		return false;
	}
	
	
//	public boolean existingOrgEmail(String email) {
//		Query<Entity> query = Query.newEntityQueryBuilder()
//				.setKind("Org")
//				.setFilter(
//						StructuredQuery.PropertyFilter.eq("org_email", email))
//				.build();
//		
//		QueryResults<Entity> emailQuery = datastore.run(query);
//	
//		if(emailQuery.hasNext())
//			return true;
//
//		return false;
//	}
}
