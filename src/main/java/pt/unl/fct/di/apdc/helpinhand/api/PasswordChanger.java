package pt.unl.fct.di.apdc.helpinhand.api;

public class PasswordChanger {
	
	private String password;
	private String confirmation;
	
	public PasswordChanger() {
		
	}
	
	public PasswordChanger(String password, String confirmation) {
		this.password = password;
		this.confirmation = confirmation;
	}

}
