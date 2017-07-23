package summer.db.client;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import summer.db.entity.MUser;
import summer.formmodel.LoginForm;
@Component
@Mapper
public interface MUserMapper {
	
	@Select("SELECT * FROM testuser WHERE userId = #{username} AND passWord = #{password}")
	public MUser getUserByLoginForm(LoginForm loginInfo);

	@Select("SELECT * FROM testuser WHERE userId = #{username} AND passWord = #{password}")
	public MUser getUserByUserNameAndPassWord(@Param("username")String username, 
			@Param("password")String password);
//		
//		MUser user = new MUser();
//		user.setUserId(username);
//		user.setPassWord(password);
//		user.setAuth_code("10");
//		user.setLastlogintime(new Date(2016, 10, 10, 10, 10));
//		
//		return user;
//		
//	}
	@Update("UPDATE testuser SET lastlogintime =  #{lastLogin} WHERE userId = #{userId}")
	public void updateLastLoginDateTime(@Param("lastLogin") Date lastLogin, @Param("userId")String userId);

}
