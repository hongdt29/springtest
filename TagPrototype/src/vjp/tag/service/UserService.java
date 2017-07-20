package vjp.tag.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vjp.tag.domain.LoginInfo;
import vjp.tag.domain.vjpTag;
import vjp.tag.domain.vjpUser;
import vjp.tag.mapper.TagMapper;
import vjp.tag.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public vjpUser getUserByNameAndPassword(LoginInfo info)
	{
		return userMapper.getUserByNameAndPassword(info);
	}
	public vjpUser getUserByCookie(String uuid)
	{
		return userMapper.getUserByCookie(uuid);
	}
	
	
	public void updateLastLogin(vjpUser info)
	{
		userMapper.updateLastLogin(info);
	}
	
	
	
	public void updateCookie(LoginInfo info)
	{
		userMapper.updateCookie(info);
	}
	public vjpUser getUserByNameAndPasswordString(String username, String password)
	{
		return userMapper.getUserByNameAndPasswordString(username, password);
	}
}