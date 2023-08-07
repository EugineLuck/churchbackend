package co.ke.emtechhouse.es.Auth.Requests;

public class LoginRequest {

	@NotBlank
	private String userName;

	@NotBlank
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}


}
