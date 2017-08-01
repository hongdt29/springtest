package summer.controller;

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

import summer.db.entity.Mcategory;
import summer.formmodel.CategoryCreateForm;
import summer.formmodel.CategoryEditForm;
import summer.formmodel.CategorySearchForm;
import summer.service.ICategoryService;
import summer.util.PageWrapper;

@Controller
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("/categorylist")
	public String CategoryList(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession sesstion,
			@PageableDefault(size  = PageWrapper.MAX_PAGE_ITEM_DISPLAY, page = 0)Pageable pageable){
		System.out.println("[DBG] CategoryList called");

		// We do the Conversion to Page display
		Pageable newPage = new PageRequest(pageable.getPageNumber(), 3);
		
		List<Mcategory> cates = categoryService.getAllCategoryNotDeleted();
		// We not use this because now use Page
		model.addAttribute("categorydatalist", cates);
		// Convert to Page
		// Tam thoi comment cho nay, ko can Paging
//		int start = newPage.getOffset();
//		int end = start + newPage.getPageSize();
//		if (start + newPage.getPageSize() > cates.size()) {
//			end = cates.size();
//		}
//		Page<Mcategory> pageData =  new PageImpl<>(cates.subList(start, end), newPage, cates.size());
//		// Parameter thu 2 chinh la ten cua Action nay trong PostMapping
//		PageWrapper<Mcategory> pageWrapper = new PageWrapper<>(pageData, "/categorylist");
//
//		// Then set data to view
//		model.addAttribute("categorydatalist", pageWrapper.getContent());
//		model.addAttribute("page", pageWrapper);
		
		return "category";
	}
	@PostMapping(path="/categorylist")
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
	
	@PostMapping(path="/categorylist", params= {"delete"})
	public String CategoryDelete(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession sesstion){
		System.out.println("[DBG] CategoryDelete called");
		System.out.println("[DBG] searchData: " + searchData.getDeleteList());

		for (String categoryID : searchData.getDeleteList()) {
			// Call Service function to Update delete_flag for categoryID
			categoryService.updateDeleteFlag(categoryID);
		}
		
		// Chuyen den action categorylist de no reload lai cac Row cho minh
		return "redirect:/categorylist";
	}
	
	
	@GetMapping(path="GoodsCreateNew.html")
	public String CategoryCreateGet(@ModelAttribute("categorynewdata") CategoryCreateForm createCate,
		Model model, HttpSession session) {
		System.out.println("[DBG] CategoryCreate GET jump in:" + createCate.getName() + " "
					+ createCate.getId());
	
			return "categorycreate";
	}
	@PostMapping(path="/categorylist/create")
	public String CategoryCreate(@ModelAttribute("categorynewdata") CategoryCreateForm createCate,
		Model model, HttpSession session) {
		
		System.out.println("[DBG] CategoryCreate POST jump in:" + createCate.getName() + " "
							+ createCate.getId());
	
		/* O day ID cua new Category se khong co, ta phai tu tao moi hoac set AutoIncrement, need Help */
		// Tam thoi tu tao moi
		String randomID = UUID.randomUUID().toString().subSequence(0, 13).toString();
		Mcategory cate = new Mcategory();
		cate.setDeleteFlag(false);
		cate.setId(randomID);
		cate.setName(createCate.getName());
	
		categoryService.insertCategory(cate);
	
		return "redirect:/categorylist";
	}


	@GetMapping(path="GoodsEdit.html")
	public String CategoryUpdateGetMapping(@ModelAttribute("categoryeditdata") CategoryEditForm updateCate,
				Model model, HttpSession session, @RequestParam("id") String id) {
		System.out.println("[DBG] CategoryUpdateGet GET jump in:" + id);
	
		List<Mcategory> cate = categoryService.getAllCategoryByID(id);
		if (cate.size() > 0) {
			Mcategory record = cate.get(0);
			updateCate.setId(record.getId());
			updateCate.setName(record.getName());
		}
		return "categoryedit";
	}
	@PostMapping(path="/categorylist/update")
	public String CategoryUpdate(@ModelAttribute("categoryeditdata") CategoryEditForm updateCate,
				Model model, HttpSession session) {
		System.out.println("[DBG] CategoryUpdate POST jump in:" + updateCate.getName() + " "
						+ updateCate.getId());
	
		Mcategory cate = new Mcategory();
		cate.setId(updateCate.getId());
		cate.setName(updateCate.getName());
		cate.setDeleteFlag(false);
		categoryService.updateCategoryByID(cate);
	
		return "redirect:/categorylist";
	}
}
