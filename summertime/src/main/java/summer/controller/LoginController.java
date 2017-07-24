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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import summer.db.entity.Testuser;
import summer.formmodel.LoginForm;
import summer.service.ILoginService;
import summer.service.LoginServiceImpl;
import summer.util.HashUtil;



@Controller
public class LoginController {
	// class nay de support viec Log
	protected final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(getClass());

	@Autowired
	private ILoginService loginService;
	
	@GetMapping("/")
	//@ResponseBody
	public String home(Model model, HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] home called");
		// Dung ham nhu nay de in ra Log
		logger.info("[INFO] home called");

//		model.addAttribute("message1", "Sunday");
//		model.addAttribute("message2", 1992);
		//B1. Check cookie, cookie nam trong request, Se
		Cookie cookieUser = WebUtils.getCookie(request, "c_user");
		Cookie cookiePwd = WebUtils.getCookie(request, "c_pwd");
		if (cookieUser!= null && cookiePwd !=null) {
			String username = cookieUser.getValue();
			String password = cookiePwd.getValue();
			System.out.println("[DBG] cookie user" + username);
			System.out.println("[DBG] cookie pwd" + password);
			
			//Doi chieu username va password trong DB 
			
			Testuser user = loginService.getUserByUserNameAndPassWord(username, password);
			if (user!=null) {
				cookieUser.setMaxAge(30*24*60*60);
				cookiePwd.setMaxAge(30*24*60*60);
				session.setAttribute("s_userId", user.getUserid());
				session.setAttribute("s_pwd", user.getPassword());
				session.setAttribute("s_auth", user.getAuthCode());
				session.setAttribute("s_lastlogin_datetime", user.getLastlogintime());
				
				// update last login
				Date currentTime = new Date();
				user.setLastlogintime(currentTime);
				loginService.updateLastLoginDateTime(user);
				
				return "redirect:/mainpage";
				
			}else {
				model.addAttribute("errormsg", "Not found user name");
				return "login";
			}
		
		}else
		{
			System.out.println("[DBG] NOt found cookie");
			return "login";
		}
		
	}
	//tao ham de xu li action login
	@PostMapping("/login")
	public String login(Model model, @ModelAttribute LoginForm logindata, HttpServletResponse response,
			HttpSession session) {
		logger.debug("[DBG] login called");
		logger.info("[DBG] usr: " + logindata.getUsername());
		
		System.out.println("[DBG] login called");
		System.out.println("[DBG] usr: " + logindata.getUsername());
		System.out.println("[DBG] pass: " + logindata.getPassword());
		System.out.println("[DBG] remember: " + logindata.isRememberme());
		
		String hashedPwd = HashUtil.HashSHA256(logindata.getPassword());
		logindata.setPassword(hashedPwd);
		System.out.println("[DBG] Hashed pass: " + logindata.getPassword());
		
		
		//B2. Doi chieu DB 
		Testuser user = loginService.getUserByLoginForm(logindata);
		if(user!= null) {
			System.out.println("[DBG] Login form. Found user, will set to cookie and session");
			//tao cookie moi 
			Cookie userCookie = new Cookie("c_user", user.getUserid());
			Cookie passCookie = new Cookie("c_pwd", user.getPassword());
			//set the exprixy of cookie
			userCookie.setMaxAge(30*24*60*60);
			passCookie.setMaxAge(30*24*60*60);
			response.addCookie(userCookie);
			response.addCookie(passCookie);
			// save the info to sesstion to use for the next controller 
			session.setAttribute("s_userId", user.getUserid());
			session.setAttribute("s_pwd", user.getPassword());
			session.setAttribute("s_auth", user.getAuthCode());
			session.setAttribute("s_lastlogin_datetime", user.getLastlogintime());
			
			// update last login
			Date currentTime = new Date();
			user.setLastlogintime(currentTime);
			loginService.updateLastLoginDateTime(user);
			
			return "redirect:/mainpage";
		}else {
			System.out.println("[DBG] Login form. case not found user");
			model.addAttribute("errormsg", "Not found user name");
			return "login";
		}
	}
	

}
