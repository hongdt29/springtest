package vjp.tag.mapper;

import vjp.tag.domain.LoginInfo;
import vjp.tag.domain.vjpTag;
import vjp.tag.domain.vjpUser;

public interface UserMapper {
	vjpUser getUserByNameAndPassword(LoginInfo info);
	vjpUser getUserByCookie(String uuid);
	void updateLastLogin(LoginInfo info);
	void updateCookie(LoginInfo info);
}
