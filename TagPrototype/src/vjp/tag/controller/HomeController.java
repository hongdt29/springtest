package vjp.tag.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vjp.tag.domain.LoginInfo;
import vjp.tag.domain.vjpUser;

@Controller
public class HomeController {

	/*This is the first action when open our websites, it will load the login.jsp view*/
	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public ModelAndView welcomePage(HttpSession session) {
		/* CHeck if there is session for User, if yes go to Main Page, othersise go to Login */
		
		ModelAndView mav = new ModelAndView("redirect:/login");
		
		return mav;
//		
//		try {
//			vjpUser loggedUser = (vjpUser)session.getAttribute("loginedUser");
//			if (loggedUser == null) {
//				ModelAndView mav = new ModelAndView("login");
//				System.out.println("Go To Login Action run 1");
//			    mav.addObject("login", new LoginInfo());
//			    return mav;
//			} else {
//				/* Go to Main Page */
//				System.out.println("Found session, go to Main Page");
//				ModelAndView mav = new ModelAndView("redirect:/mainpage");
//				
//				return mav;
//			}
//			
//		} catch (Exception ex) {
//			ModelAndView mav = new ModelAndView("login");
//		    System.out.println("Go To Login Action run ");
//		    mav.addObject("login", new LoginInfo());
//		    return mav;
//		}
	}
}