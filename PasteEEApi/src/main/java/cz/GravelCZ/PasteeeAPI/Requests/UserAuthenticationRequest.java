package cz.GravelCZ.PasteeeAPI.Requests;

public class UserAuthenticationRequest {

	public String username, password;

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String name) {
		this.username = name;
	}
	
}
