package vjp.tag.controller;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import vjp.tag.domain.LoginInfo;
import vjp.tag.domain.vjpUser;
import vjp.tag.service.TagService;
import vjp.tag.service.UserService;

@Controller
public class LoginController {
	protected final Log logger = LogFactory.getLog(getClass());
	
	public static String COOKIE_USR = "COOKIES_LOGGED_USER";
	public static String COOKIE_PWD = "COOKIES_LOGGED_PWD";
	public static String SESSION_LOGGED_USR = "LOGGED_USER";
	
	@Autowired
	private UserService userService;
	
	public static String HashSHA256(String input) {
		String result = "";
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			result = DatatypeConverter.printHexBinary(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}
	/* If the user login on form, it will go to this action */
	@PostMapping("login")
	 public ModelAndView loginProcess(HttpServletRequest request, 
			  HttpServletResponse response, HttpSession session,
			  @ModelAttribute("login") LoginInfo login) {
		
		ModelAndView mav = null;
		String usrName = login.getUsername();
		String pwd = login.getPassword();
		String hashPwd = HashSHA256(pwd);
		logger.info("usr1:" + usrName);
		logger.info("pwd1:" + pwd);
		logger.info("Hash1:" + hashPwd);
		logger.info("username Text Box:" + request.getParameter("username"));
		logger.info("pwd Text Box:" + request.getParameter("password"));

		if (usrName.length() < 4 || pwd.length() < 4) {
			/* if validation of user name and pwd fail, back to view and
			 * set error message */
			mav = new ModelAndView("login");
			mav.addObject("abc", "Username or Password incorrect");
		} else {
			/* Validate with DB */

			vjpUser user = userService.getUserByNameAndPasswordString(usrName, hashPwd);
			if (user != null) {
				logger.info("Found user " + usrName);
				/* Goto Action main */
				mav = new ModelAndView("redirect:/mainpage");
				
		        String uuid = UUID.randomUUID().toString();
		        //rememberDAO.save(uuid, user);
		        Cookie userCookie = new Cookie(COOKIE_USR, usrName);
		        userCookie.setMaxAge(30 * 24 * 60 * 60); // one month
		        Cookie pwdCookie = new Cookie(COOKIE_PWD, hashPwd);
		        pwdCookie.setMaxAge(30 * 24 * 60 * 60); // one month
		        response.addCookie(userCookie);
		        response.addCookie(pwdCookie);
		        logger.info("Added User cookie with value " + usrName + "---" + hashPwd);

		        user.setLastlogin(new Date());
		        userService.updateLastLogin(user);
		        
		        /* Set current user to Session */
				session.setAttribute(SESSION_LOGGED_USR,user);

			} else {
				mav = new ModelAndView("login");
				mav.addObject("abc", "Not found Username with Password");
			}
			
	    }
           /*"loginedUser"???*/
	    return mav;    
	  }
	
	@GetMapping("login")
	 public ModelAndView login(
			 HttpServletRequest request, 
			 HttpServletResponse response,
			 HttpSession session,
			 @ModelAttribute("login") LoginInfo login) {

		logger.info("Login GetMapping GET enter...");
		// Step 1: Get info from cookie
		//	If existed, go to main page.
		//  If Not existed, User need to Login, continue
		Cookie userCookie = WebUtils.getCookie(request, COOKIE_USR);
		Cookie pwdCookie = WebUtils.getCookie(request, COOKIE_PWD);
		
		if (userCookie != null && pwdCookie != null) {
			// This case, there is cookie, Update Cookie 1 month
			logger.info("Already have User Cookie, val " + userCookie.getValue());
			
			//Step2: Get cookie value
			String username = userCookie.getValue();
			String pwd = pwdCookie.getValue();
			
			//2017-07-19 22:35:36
			vjpUser userDB = userService.getUserByNameAndPasswordString(
					username,
					pwd);
			if (userDB != null) {
				/* This case, user already found in DB */
				logger.info("Founded User");
				userCookie.setMaxAge(30 * 24 * 60 * 60); // 1 month
				
				// Update Last Login
				Date currentTime = new Date();
				userDB.setLastlogin(currentTime);
		        userService.updateLastLogin(userDB);
				
		        // Store in session
				session.setAttribute(SESSION_LOGGED_USR, userDB);
				
				// Go to Main Page
				ModelAndView mav = new ModelAndView("redirect:/mainpage");
				return mav;
			} else {
				logger.info("Not Founded User, load Login view");
				ModelAndView mav = new ModelAndView("login");
				return mav;  
			}
			
		} else {
			// This case, there is no cookie or only 1 cookie, continue and show the Form
			logger.info("Not have User Cookie...");
		}
		ModelAndView mav = new ModelAndView("login");
		return mav;  
	}
}