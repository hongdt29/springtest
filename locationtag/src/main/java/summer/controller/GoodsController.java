package summer.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import summer.db.entity.CompositeTGoods;
import summer.db.entity.Mcategory;
import summer.formmodel.CategoryCreateForm;
import summer.formmodel.CategoryEditForm;
import summer.formmodel.CategorySearchForm;
import summer.formmodel.GoodsForm;
import summer.service.ICategoryService;
import summer.service.IGoodsService;
import summer.util.PageWrapper;

@Controller
public class GoodsController {
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IGoodsService goodsService;
	
	@GetMapping("/goodslist")
	public String GoodsListGet(@ModelAttribute("goodsform") GoodsForm goodsform,
			Model model, HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] GoodsList called");
		
		List<Mcategory> allCates = categoryService.getAllCategoryNotDeleted("id ASC");
		model.addAttribute("dropCategory", allCates);
		goodsform.setCategoryId(allCates.get(0).getId());
		
		CompositeTGoods condition = new CompositeTGoods();
		condition.setId("%");
		condition.setIdcategory("%");
		condition.setIdcompany("%");
		condition.setIdfloor("%");
		condition.setIdtag("%");
		
		List<CompositeTGoods> goods = goodsService.getAllGoods(condition);
		model.addAttribute("goodslist", goods);
		
		return "goods";
	}
	
	@PostMapping("/goodslist")
	public String GoodsListPost(@ModelAttribute("goodsform") GoodsForm goodsform,
			Model model, HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] GoodsListPost called:" + goodsform.getCategoryId());
		
		List<Mcategory> allCates = categoryService.getAllCategoryNotDeleted("id ASC");
		model.addAttribute("dropCategory", allCates);
		
		return "goods";
	}
}
