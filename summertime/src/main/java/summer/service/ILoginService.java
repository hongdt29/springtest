package summer.service;

import java.util.Date;

import summer.db.entity.MUser;
import summer.formmodel.LoginForm;

public interface ILoginService {
	public MUser getUserByLoginForm(LoginForm loginInfo);
	public MUser getUserByUserNameAndPassWord(String username, String password);
	public void updateLastLoginDateTime(Date lastLogin, String user);
	

}



