package vjp.tag.mapper;

import org.apache.ibatis.annotations.Param;

import vjp.tag.domain.LoginInfo;
import vjp.tag.domain.vjpTag;
import vjp.tag.domain.vjpUser;

public interface UserMapper {
	vjpUser getUserByNameAndPassword(LoginInfo info);
	vjpUser getUserByCookie(String uuid);
	
	void updateCookie(LoginInfo info);
	
	vjpUser getUserByNameAndPasswordString(@Param("username") String username, 
			@Param("password") String password);
	
	void updateLastLogin(vjpUser user);
}
