package summer.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import summer.db.client.MuserMapper;
import summer.db.entity.Muser;
import summer.db.entity.MuserExample;
import summer.formmodel.LoginForm;

@Service
public class LoginServiceImpl implements ILoginService {
	@Autowired
	private MuserMapper userMapper;

	@Override
	public Muser getUserByUserNameAndPassWord(String username, String password) {
		//Testuser user = userMapper.getUserByUserNameAndPassWord(username, password);
		MuserExample query = new MuserExample();
		query.createCriteria().andUseridEqualTo(username).
			andPasswordEqualTo(password);

		List<Muser> users = userMapper.selectByExample(query);
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public boolean authenticateUserNameAndPassWord(String username, String password)
	{
		MuserExample query = new MuserExample();
		query.createCriteria().andUseridEqualTo(username).
			andPasswordEqualTo(password);

		List<Muser> users = userMapper.selectByExample(query);
		if (users.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public void updateLastLoginDateTime(Muser newUser) {
		//userMapper.updateLastLoginDateTime(lastLogin, userId);
		MuserExample query = new MuserExample();
		query.createCriteria().andUseridEqualTo(newUser.getUserid()).
			andPasswordEqualTo(newUser.getPassword());
		
		userMapper.updateByExample(newUser, query);
	}
	@Override
	public void ValidateUserNameAndPassword(BindingResult bindingResult,LoginForm logindata) {
		if ((logindata.getUsername().length()) == 0){
			bindingResult.rejectValue("username", "", "Input the username");
		}
		if ((logindata.getPassword().length()) == 0){
			bindingResult.rejectValue("password", "E121", "Input the password");
		}
	}
}
