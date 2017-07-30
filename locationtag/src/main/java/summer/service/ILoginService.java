package summer.service;

import java.util.Date;

import org.springframework.validation.BindingResult;

import summer.db.entity.Muser;
import summer.formmodel.LoginForm;

public interface ILoginService {
	public Muser getUserByUserNameAndPassWord(String username, String password);
	public boolean authenticateUserNameAndPassWord(String username, String password);
	public void updateLastLoginDateTime(Muser user);
	
	public void ValidateUserNameAndPassword(BindingResult bindingResult, LoginForm logindata);
}



