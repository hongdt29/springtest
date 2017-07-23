package summer.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.db.client.MUserMapper;
import summer.db.entity.MUser;
import summer.formmodel.LoginForm;

@Service
public class LoginServiceImpl implements ILoginService {
	@Autowired
	private MUserMapper userMapper;

	@Override
	public MUser getUserByLoginForm(LoginForm loginInfo) {
//		MUser user = new MUser();
//		user.setUserId(username);
//		user.setPassWord(password);
//		user.setAuth_code("10");
//		user.setLastlogintime(new Date(2016, 10, 10, 10, 10));
//		
//		return user;
		
		System.out.println("[DBG] LoginService getUserByLoginForm called ");
		MUser user = userMapper.getUserByLoginForm(loginInfo);
	
		return user;
	}

	@Override
	public MUser getUserByUserNameAndPassWord(String username, String password) {
		MUser user = userMapper.getUserByUserNameAndPassWord(username, password);
		return user;
	}

	@Override
	public void updateLastLoginDateTime(Date lastLogin, String userId) {
		userMapper.updateLastLoginDateTime(lastLogin, userId);
		
	
		
	}

}
