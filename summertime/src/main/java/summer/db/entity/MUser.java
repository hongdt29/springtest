package summer.db.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class MUser {
	//Muser giong voi user table trong DB, viet giong voi ten cot trong table
	private String userId;
	private String passWord;
	private String auth_code;
	private String past_w1;
	private String past_w2;
	private Date lastlogintime;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getPast_w1() {
		return past_w1;
	}
	public void setPast_w1(String past_w1) {
		this.past_w1 = past_w1;
	}
	public String getPast_w2() {
		return past_w2;
	}
	public void setPast_w2(String past_w2) {
		this.past_w2 = past_w2;
	}
	public Date getLastlogintime() {
		return lastlogintime;
	}
	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
	

}
