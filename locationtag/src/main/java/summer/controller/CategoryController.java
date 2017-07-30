package summer.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import summer.db.entity.Mcategory;
import summer.formmodel.CategorySearchForm;
import summer.service.ICategoryService;

@Controller
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("/categorylist")
	public String CategoryList(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession sesstion){
		System.out.println("[DBG] CategoryList called");
		
		List<Mcategory> cates = categoryService.getAllCategoryNotDeleted();
		model.addAttribute("categorydatalist", cates);
		
		return "category";
	}
}
