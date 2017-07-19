package vjp.tag.domain;

import java.util.Date;

public class LoginInfo {
	private String username;
	private String password;
	private String cookie;
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	private Date lastlogin;
	public Date getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(java.util.Date date) {
		this.lastlogin = date;
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
	
}
