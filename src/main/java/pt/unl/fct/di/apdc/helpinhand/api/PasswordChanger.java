package pt.unl.fct.di.apdc.helpinhand.api;

public class PasswordChanger {
	
	private String password;
	private String confirmation;
	
	public PasswordChanger() {
		
	}
	
	public PasswordChanger(String password, String confirmation) {
		this.setPassword(password);
		this.setConfirmation(confirmation);
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

}
