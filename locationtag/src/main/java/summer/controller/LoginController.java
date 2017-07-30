package summer.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import summer.db.entity.Muser;
import summer.formmodel.LoginForm;
import summer.service.ILoginService;
import summer.util.Encryption;



@Controller
public class LoginController {
	// class nay de support viec Log
	protected final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(getClass());

	public static String COOKIE_USERID = "c_user";
	public static String COOKIE_PWD = "c_pwd";
	public static String SESSION_USERID = "s_userId";
	public static String SESSION_PWD = "s_pwd";
	public static String SESSION_AUTH_CODE = "s_auth";
	public static String SESSION_LASTLOGIN = "s_lastlogin_datetime";
	
	@Autowired
	private ILoginService loginService;
	
	@GetMapping("/")
	//@ResponseBody
	public String home(Model model, @ModelAttribute("logindata") LoginForm logindata,
			HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] home called");
		Cookie c_userName = WebUtils.getCookie(request, COOKIE_USERID);
		Cookie c_password = WebUtils.getCookie(request, COOKIE_PWD);
		if(c_userName == null ||c_password == null) {
			return "login";
		}else { 
			System.out.println("[DBG] USR:" + c_userName.getValue());
			System.out.println("[DBG] PWD:" + c_password.getValue());
			boolean isInDB =  loginService.authenticateUserNameAndPassWord(c_userName.getValue(),(c_password.getValue()));
			if(isInDB == true) {
				Muser muser = loginService.getUserByUserNameAndPassWord(c_userName.getValue(), (c_password.getValue()));
				c_userName.setMaxAge(30*24*60*60);
				c_password.setMaxAge(30*24*60*60);
				
				session.setAttribute(SESSION_USERID, c_userName.getValue());
				session.setAttribute(SESSION_USERID, (c_password.getValue()));
				session.setAttribute(SESSION_AUTH_CODE, muser.getAuthcode());
				session.setAttribute(SESSION_LASTLOGIN, muser.getLastlogindatetime());
				
				Date currentDate = new Date();
				muser.setLastlogindatetime(currentDate);
				loginService.updateLastLoginDateTime(muser);
				return "redirect:/categorylist";
				
			}else {
				
				return "login";
			}
		}
		
		
	}
	//tao ham de xu li action login
	@PostMapping("/login")
	public String login(Model model, @ModelAttribute("logindata") LoginForm logindata, 
			BindingResult bindingResult,
			HttpServletResponse response,
			HttpSession session) {
		System.out.println("[DBG] login called");
		System.out.println("[DBG] usr: " + logindata.getUsername());
		System.out.println("[DBG] pass: " + logindata.getPassword());
		System.out.println("[DBG] remember: " + logindata.isRememberme());
		System.out.println("[DBG] HASHED: " + Encryption.EncryptPassword(logindata.getPassword()));
		
		loginService.ValidateUserNameAndPassword(bindingResult, logindata);
		if (bindingResult.hasErrors()) {
			return "login";
		}
		//check DB 
		String hashPassword = Encryption.EncryptPassword(logindata.getPassword());
		boolean isInDB =  loginService.authenticateUserNameAndPassWord(logindata.getUsername(),hashPassword);
				
		if (isInDB == true) {
			
			Cookie userName = new Cookie(COOKIE_USERID, logindata.getUsername());
			Cookie password = new Cookie(COOKIE_PWD, hashPassword);
			userName.setMaxAge(30*24*60*60);
			password.setMaxAge(30*24*60*60);
			response.addCookie(userName);
			response.addCookie(password);
			
			Muser mUser = loginService.getUserByUserNameAndPassWord(logindata.getUsername(), hashPassword);
			session.setAttribute(SESSION_USERID, logindata.getUsername());
			session.setAttribute(SESSION_PWD,hashPassword);
			session.setAttribute(SESSION_AUTH_CODE,mUser.getAuthcode());
			session.setAttribute(SESSION_LASTLOGIN,mUser.getLastlogindatetime());
			
			Date currentDate = new Date();
			mUser.setLastlogindatetime(currentDate);
			loginService.updateLastLoginDateTime(mUser);
			
			return "redirect:/categorylist";
			
		}else {
			bindingResult.rejectValue("NotFoundInDB", " ", "User not found");
			
		}
		return "login";
		
	}
	

}
