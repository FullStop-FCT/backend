package pt.unl.fct.di.apdc.helpinhand.api;

public class UserOrg extends UsersData{

	private String nif;
	
	
	public UserOrg(String entityName, String name, String email, String password, 
			String confirmation, String nif) {
		super(entityName, name, email, password, confirmation);
		this.setNif(nif);
	}


	public String getNif() {
		return nif;
	}


	public void setNif(String nif) {
		this.nif = nif;
	}
	
}
