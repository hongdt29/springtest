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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import summer.db.entity.Mcategory;
import summer.formmodel.CategoryCreateForm;
import summer.formmodel.CategoryEditForm;
import summer.formmodel.CategorySearchForm;
import summer.service.ICategoryService;
import summer.util.PageWrapper;

@Controller
@SessionAttributes(value = {"categorydata"}, types = {CategorySearchForm.class})
public class CategoryController {
	public static String S_SEARCH_ID = "s_searchid";
	public static String S_SEARCH_NAME = "s_searchname";
	public static String S_SORT_COLUMN = "s_sortcol";
	public static String S_SORT_ORDER = "s_sortorder";
	@ModelAttribute("categorydata")
	public CategorySearchForm getSearchForm()
	{
		CategorySearchForm result = new CategorySearchForm();
		result.setDeleteList(new ArrayList<String>());
		result.setSearchID("");
		result.setSearchName("");
		result.setOrderColumn("id");
		result.setOrderSort("DESC");
		return result;
	}
	// AUto wire is ...
	@Autowired
	private ICategoryService categoryService;
	@GetMapping("/categorylist")
	public String CategoryList(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] CategoryList called");
		List<Mcategory> cates = new ArrayList<Mcategory>();

		String dbOrderBy = "";
		
		String s_sortColum = searchData.getOrderColumn();
		String s_sortOrder = searchData.getOrderSort();
		
		dbOrderBy = s_sortColum + " " + s_sortOrder;
		model.addAttribute("mOrderBy", s_sortColum);
		model.addAttribute("mOrder", s_sortOrder);
		
		String s_searchId= searchData.getSearchID();
		String s_searchName = searchData.getSearchName();
		if (s_searchId.equals("")&& s_searchName.equals("")) {
			 cates = categoryService.getAllCategoryNotDeleted(dbOrderBy);
		}else if(s_searchId.equals("") == false && s_searchName.equals("") == true) {
			// ID not empty, nameis empty
			 cates = categoryService.getAllCategoryByID(s_searchId, dbOrderBy);
		} else if(s_searchId.equals("") == true && s_searchName.equals("") == false) {
			// Name not empty, ID is  empty
			 cates = categoryService.getAllCategoryByName(s_searchName, dbOrderBy);
		} else if(s_searchId.equals("") == false && s_searchName.equals("") == false) {
			 cates = categoryService.getAllCategoryByNameAndId(s_searchId, s_searchName, dbOrderBy);
		}
			
		if (cates.size() >5000) {
			model.addAttribute("errorSearch", "Size too much");
		} else if (cates.isEmpty()) {
			model.addAttribute("errorSearch", "Not found");
		} else {
			// search OK, no error
			model.addAttribute("categorydatalist", cates);
		}
		
		return "category";
	}
	@PostMapping(path="/categorylist")
	public String CategorySearch(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] CategorySearch called");
		System.out.println("[DBG] searchData: " + searchData.getSearchID() + searchData.getSearchName());
		// Lay cac gia tri tu DB ra de tim ra order by clause
		String dbOrderBy = "";
		
		String s_sortColum = searchData.getOrderColumn();
		String s_sortOrder = searchData.getOrderSort();
	
		dbOrderBy = s_sortColum + " " + s_sortOrder;
		
		model.addAttribute("mOrderBy", s_sortColum);
		model.addAttribute("mOrder", s_sortOrder);
		
		String categoryId = searchData.getSearchID();
		String categoryName = searchData.getSearchName();
		if (categoryId.equals("")&& categoryName.equals("")) {
			List<Mcategory> cates = categoryService.getAllCategoryNotDeleted(dbOrderBy);
			model.addAttribute("categorydatalist", cates);
		}else if(categoryId.equals("") == false && categoryName.equals("") == true) {
			// ID not empty, nameis empty
			List<Mcategory> cates = categoryService.getAllCategoryByID(categoryId, dbOrderBy);
			model.addAttribute("categorydatalist", cates);
		} else if(categoryId.equals("") == true && categoryName.equals("") == false) {
			// Name not empty, ID is  empty
			List<Mcategory> cates = categoryService.getAllCategoryByName(categoryName, dbOrderBy);
			model.addAttribute("categorydatalist", cates);
		} else if(categoryId.equals("") == false && categoryName.equals("") == false) {
			List<Mcategory> cates = categoryService.getAllCategoryByNameAndId(categoryId, categoryName, dbOrderBy);
			model.addAttribute("categorydatalist", cates);
		}
		
		return "category";
	}
	
	@PostMapping(path="/categorylist", params= {"delete"})
	public String CategoryDelete(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession sesstion,
			RedirectAttributes redirectAttributes){
		// 1. Chu y ta can phai add them RedirectAttributes nhu tren
		System.out.println("[DBG] CategoryDelete called");
		System.out.println("[DBG] searchData: " + searchData.getDeleteList());
		
		if (searchData.getDeleteList()== null || searchData.getDeleteList().isEmpty()) {
			model.addAttribute("errorDelete", "Please check the item ");
			// 2. Truyen message bang redirect
			redirectAttributes.addFlashAttribute("errorDelete", "Please check the item ");
		}else {

			for (String categoryID : searchData.getDeleteList()) {
				// Call Service function to Update delete_flag for categoryID
				categoryService.updateDeleteFlag(categoryID);
			}
			model.addAttribute("infomessage", "Deleted item " + searchData.getDeleteList().size());
			// Chuyen den action categorylist de no reload lai cac Row cho minh
		}
		return "redirect:/categorylist";
	}
	
	
	@GetMapping("/categorylist/create")
	public String CategoryCreateGet(@ModelAttribute("categorynewdata") CategoryCreateForm createCate,
		Model model, HttpSession session) {
		System.out.println("[DBG] CategoryCreate GET jump in:" + createCate.getName() + " "
					+ createCate.getId());
	
			return "categorycreate";
	}
	@PostMapping(path="/categorylist/create")
	public String CategoryCreate(@ModelAttribute("categorynewdata") CategoryCreateForm categorynewdata,
		Model model, HttpSession session) {
		
		System.out.println("[DBG] CategoryCreate POST jump in:" + categorynewdata.getName() + " "
							+ categorynewdata.getId());
	
		/* O day ID cua new Category se khong co, ta phai tu tao moi hoac set AutoIncrement, need Help */
		// Tam thoi tu tao moi
		//String randomID = UUID.randomUUID().toString().subSequence(0, 13).toString();
		String randomID = "00001";
		Mcategory cate = new Mcategory();
		cate.setDeleteflag(false);
		cate.setId(randomID);
		cate.setName(categorynewdata.getName());
	
		try {
			categoryService.insertCategory(cate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Xu ly error here
			model.addAttribute("errorMsg", "Content Message");
		}
		return "redirect:/categorylist";
	}


	@GetMapping(path="/categorylist/update")
	public String CategoryUpdateGetMapping(@ModelAttribute("categoryeditdata") CategoryEditForm categoryeditdata,
				Model model, HttpSession session, @RequestParam("id") String id) {
		System.out.println("[DBG] CategoryUpdateGet GET jump in:" + id);
		String dbOrderBy = "id " + "ASC";
		List<Mcategory> cate = categoryService.getAllCategoryByID(id, dbOrderBy);
		if (cate.size() > 0) {
			Mcategory record = cate.get(0);
			categoryeditdata.setId(record.getId());
			categoryeditdata.setName(record.getName());
		}
		return "categoryedit";
	}
	@PostMapping(path="/categorylist/update")
	public String CategoryUpdate(@ModelAttribute("categoryeditdata") CategoryEditForm categoryeditdata,
				Model model, HttpSession session) {
		System.out.println("[DBG] CategoryUpdate POST jump in:" + categoryeditdata.getName() + " "
						+ categoryeditdata.getId());
	
		Mcategory cate = new Mcategory();
		cate.setId(categoryeditdata.getId());
		cate.setName(categoryeditdata.getName());
		cate.setDeleteflag(false);
		
		categoryService.updateCategoryByID(cate);
	
		return "redirect:/categorylist";
	}
	
	@GetMapping("/categorylist/sortid")
	public String CategorySortID(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] CategorySortID called");
		
		searchData.setOrderColumn("id");
		String sortOrder = (String) searchData.getOrderSort();
		if( sortOrder.equals("ASC")== true) {
			searchData.setOrderSort("DESC");
		}else {
			searchData.setOrderSort("ASC");
		}
		// Bay gio chuyen sang search controller voi nhung gia tri minh luu vao session roi
		return "redirect:/categorylist";
	}
	
	@GetMapping("/categorylist/sortname")
	public String CategorySortName(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] CategorySortID called");
		searchData.setOrderColumn("name");
		String sortOrder = (String) searchData.getOrderSort();
		if( sortOrder.equals("ASC")== true) {
			searchData.setOrderSort("DESC");
		}else {
			searchData.setOrderSort("ASC");
		}
		return "redirect:/categorylist";
	}
}
