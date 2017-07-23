package summer.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainPageController {
	@GetMapping("/mainpage")
	@ResponseBody
	public String homemain(Model model, HttpServletRequest request, HttpSession sesstion){
		String userName = (String)sesstion.getAttribute("s_userId");
		String passWord = (String)sesstion.getAttribute("s_pwd");
		String authCode = (String)sesstion.getAttribute("s_auth");
		Date loginDateTime = (Date) sesstion.getAttribute("s_lastlogin_datetime");
		
		System.out.println("[DBG] mainpage called");
		return "mainpage" + userName + passWord + authCode + loginDateTime;
		}

}
