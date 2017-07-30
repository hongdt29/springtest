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
import org.springframework.web.bind.annotation.PostMapping;
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
	@PostMapping("/categorylist")
	public String CategorySearch(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession sesstion){
		System.out.println("[DBG] CategorySearch called");
		System.out.println("[DBG] searchData: " + searchData.getSearchID() + searchData.getSearchName());
//
//		List<Mcategory> cates = categoryService.getAllCategoryNotDeleted();
//		model.addAttribute("categorydatalist", cates);
		String categoryId = searchData.getSearchID();
		String categoryName = searchData.getSearchName();
		if (categoryId.equals("")&& categoryName.equals("")) {
			List<Mcategory> cates = categoryService.getAllCategoryNotDeleted();
			model.addAttribute("categorydatalist", cates);
		}else if(categoryId.equals("") == false && categoryName.equals("") == true) {
			// ID not empty, nameis empty
			List<Mcategory> cates = categoryService.getAllCategoryByID(categoryId);
			model.addAttribute("categorydatalist", cates);
		} else if(categoryId.equals("") == true && categoryName.equals("") == false) {
			// Name not empty, ID is  empty
			List<Mcategory> cates = categoryService.getAllCategoryByName(categoryName);
			model.addAttribute("categorydatalist", cates);
		} else if(categoryId.equals("") == false && categoryName.equals("") == false) {
			List<Mcategory> cates = categoryService.getAllCategoryByNameAndId(categoryId, categoryName);
			model.addAttribute("categorydatalist", cates);
		}
		
		
		
		return "category";
	}
}
