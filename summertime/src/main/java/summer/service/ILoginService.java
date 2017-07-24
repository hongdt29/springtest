package summer.service;

import java.util.Date;

import summer.db.entity.Testuser;
import summer.formmodel.LoginForm;

public interface ILoginService {
	public Testuser getUserByLoginForm(LoginForm loginInfo);
	public Testuser getUserByUserNameAndPassWord(String username, String password);
	public void updateLastLoginDateTime(Testuser user);
	

}



