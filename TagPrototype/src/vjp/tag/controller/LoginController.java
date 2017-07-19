package vjp.tag.controller;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	@Autowired
	private UserService userService;
	
	/* If the user login on form, it will go to this action */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	 public ModelAndView loginProcess(HttpServletRequest request, 
			  HttpServletResponse response, HttpSession session,
			  @ModelAttribute("login") LoginInfo login) {
		
		ModelAndView mav = null;
		String usrName = login.getUsername();
		String pwd = login.getPassword();
		System.out.println("usr:" + usrName);
		System.out.println("pwd:" + pwd);
		
		System.out.println("username Text Box:" + request.getParameter("username"));
		System.out.println("pwd Text Box:" + request.getParameter("password"));
		
		
		if (usrName.length() < 4 || pwd.length() < 4) {
			/* if validation of user name and pwd fail, back to view and
			 * set error message */
			mav = new ModelAndView("login");
			mav.addObject("abc", "Username or Password incorrect");
		} else {
			/* Validate with DB */
			LoginInfo info = new LoginInfo();
			info.setUsername(usrName);
			info.setPassword(pwd);
			vjpUser user = userService.getUserByNameAndPassword(info);
			if (user != null) {
				System.out.println("Found user " + usrName);
				/* Goto Action main */
				mav = new ModelAndView("redirect:/mainpage");
				/* Set current user to Session */
				session.setAttribute("loginedUser",user);
				if (true) {
			        String uuid = UUID.randomUUID().toString();
			        //rememberDAO.save(uuid, user);
			        Cookie userCookie = new Cookie("COOKIES_LOGGED_USER", uuid);
			        userCookie.setMaxAge(30 * 24 * 60 * 60); // one month
			        response.addCookie(userCookie);
			        System.out.println("Added User cookie with value " + uuid);
			        
			        info.setCookie(uuid);
			        userService.updateCookie(info);
			        
			        info.setLastlogin(new Date());
			        userService.updateLastLogin(info);
			    } else {
			        //rememberDAO.delete(user);
			    	Cookie userCookie = new Cookie("COOKIES_LOGGED_USER", null);
			        userCookie.setMaxAge(0); // one month
			        response.addCookie(userCookie);
			        System.out.println("Removed User Cookie");
			    }
			} else {
				mav = new ModelAndView("login");
				mav.addObject("abc", "Not found Username with Password");
			}
			
	    }
           /*"loginedUser"???*/
	    return mav;    
	  }
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	 public ModelAndView login(HttpServletRequest request, @ModelAttribute("login") LoginInfo login) {
		System.out.println("Login GET enter...");
		logger.info("Login GET enter...");
		// Step 1: Get info from cookie
		//	If existed, go to main page.
		//  If Not existed, User need to Login, continue
		Cookie userCookie = WebUtils.getCookie(request, "COOKIES_LOGGED_USER");
		if (userCookie != null ) {
			// This case, there is cookie, Update Cookie 1 month
			System.out.println("Already have User Cookie, extend time, val " + userCookie.getValue());
			userCookie.setMaxAge(30 * 24 * 60 * 60); // 1 month
			
			//2017-07-19 22:35:36
		} else {
			// This case, there is no cookie, continue
			System.out.println("Not have User Cookie...");
		}
		ModelAndView mav = new ModelAndView("login");
		return mav;    
	}
}