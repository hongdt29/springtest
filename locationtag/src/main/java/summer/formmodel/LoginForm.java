package summer.formmodel;

public class LoginForm {
	private String username;
	private String password;
	private boolean rememberme;
	private String NotFoundInDB;
	
	public String getNotFoundInDB() {
		return NotFoundInDB;
	}
	public void setNotFoundInDB(String notFoundInDB) {
		NotFoundInDB = notFoundInDB;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRememberme() {
		return rememberme;
	}
	public void setRememberme(boolean rememberme) {
		this.rememberme = rememberme;
	}

}

