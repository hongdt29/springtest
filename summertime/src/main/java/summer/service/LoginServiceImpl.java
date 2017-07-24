package summer.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.db.client.TestuserMapper;
import summer.db.entity.Testuser;
import summer.db.entity.TestuserExample;
import summer.formmodel.LoginForm;

@Service
public class LoginServiceImpl implements ILoginService {
	@Autowired
	private TestuserMapper userMapper;

	@Override
	public Testuser getUserByLoginForm(LoginForm loginInfo) {
//		MUser user = new MUser();
//		user.setUserId(username);
//		user.setPassWord(password);
//		user.setAuth_code("10");
//		user.setLastlogintime(new Date(2016, 10, 10, 10, 10));
//		
//		return user;
		
		System.out.println("[DBG] LoginService getUserByLoginForm called ");
		//Testuser user = userMapper.getUserByLoginForm(loginInfo);
		// vidu loginInfo truyen vao co username la admin va password la 123456
		// select * from testuser       Userid = admin AND Password = 123456
		TestuserExample query = new TestuserExample();
		query.createCriteria().andUseridEqualTo(loginInfo.getUsername()).
			andPasswordEqualTo(loginInfo.getPassword());

		List<Testuser> users = userMapper.selectByExample(query);
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Testuser getUserByUserNameAndPassWord(String username, String password) {
		//Testuser user = userMapper.getUserByUserNameAndPassWord(username, password);
		TestuserExample query = new TestuserExample();
		query.createCriteria().andUseridEqualTo(username).
			andPasswordEqualTo(password);

		List<Testuser> users = userMapper.selectByExample(query);
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void updateLastLoginDateTime(Testuser newUser) {
		//userMapper.updateLastLoginDateTime(lastLogin, userId);
		TestuserExample query = new TestuserExample();
		query.createCriteria().andUseridEqualTo(newUser.getUserid()).
			andPasswordEqualTo(newUser.getPassword());
		
		userMapper.updateByExample(newUser, query);
	
		
	}

}
