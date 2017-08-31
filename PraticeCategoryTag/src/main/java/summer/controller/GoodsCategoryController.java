package summer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import summer.form.GoodsCategoryForm;
@Controller
@SessionAttributes(value = {"categoryForm"}, types= {GoodsCategoryForm.class})
public class GoodsCategoryController {
	@ModelAttribute("categoryForm")
	public GoodsCategoryForm getGoodsCategoryForm() {
		System.out.println("Unit model Attribute");
		GoodsCategoryForm goodsCategory = new GoodsCategoryForm();
		goodsCategory.setCategoryId("1234");
		goodsCategory.setCategoryName("ABCD");
		
		return goodsCategory;
	}
	@GetMapping("/categorySearch")
	public String showPage(@ModelAttribute("categoryForm") GoodsCategoryForm goodsCategoryForm,
			Model model) {
		System.out.println("Get categoryID:" + goodsCategoryForm.getCategoryId());
		System.out.println("Get categoryName:" + goodsCategoryForm.getCategoryName());
				return "goodsCategory";
				
		
	}
	@PostMapping("/categorySearch")
	public String searchForm(@ModelAttribute("categoryForm") GoodsCategoryForm goodsCategoryForm,
			Model model) {
		System.out.println("Post categoryID:" + goodsCategoryForm.getCategoryId());
		System.out.println("Post categoryName:" + goodsCategoryForm.getCategoryName());
			return "goodsCategory";
		
	}

}
