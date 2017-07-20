package vjp.tag.controller;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vjp.tag.domain.vjpTag;
import vjp.tag.domain.vjpUser;
import vjp.tag.service.TagService;

 
@Controller
public class MainPageController {
	String message = "Welcome to Spring MVC!";
 
	public static String SESSION_LOGGED_USR = "LOGGED_USER";
	@Autowired
	private TagService tagService;
	
	//@RequestMapping("/mainpage")
	@GetMapping("mainpage")
	public ModelAndView MainPage(HttpSession session) {
		System.out.println("MainPage controller");
		ModelAndView mv = new ModelAndView("MainPage");
		
		vjpUser loggedUser = (vjpUser)session.getAttribute(SESSION_LOGGED_USR);
		
		if (loggedUser != null) {
			System.out.println("Good, FOund user from session " + loggedUser.getUsername());
			vjpTag tag = tagService.getTagByID(loggedUser.getId());
			mv.addObject("message", "x:" + tag.getX() + ",y:" +
					tag.getY() + ",z:" + tag.getZ());
			mv.addObject("lastlogin", loggedUser.getLastlogin().toString());
		} else {
			System.out.println("WTF, no user in session");
		}
		
		return mv;
	}
}