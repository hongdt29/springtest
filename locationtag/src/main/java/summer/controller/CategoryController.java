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
	public static String S_SEARCH_ID = "s_searchid";
	public static String S_SEARCH_NAME = "s_searchname";
	public static String S_SORT_COLUMN = "s_sortcol";
	public static String S_SORT_ORDER = "s_sortorder";
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("/categorylist")
	public String CategoryList(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession session,
			@PageableDefault(size  = PageWrapper.MAX_PAGE_ITEM_DISPLAY, page = 0)Pageable pageable){
		System.out.println("[DBG] CategoryList called");
		
		// Lay cac gia tri tu DB ra de tim ra order by clause
		String dbOrderBy = "";
		String sortOrder = (String) session.getAttribute(S_SORT_ORDER);
		String sortColumn = (String) session.getAttribute(S_SORT_COLUMN);
		if (sortOrder == null && sortColumn == null) {
			sortOrder = "ASC";
			sortColumn = "id";
		}
		dbOrderBy = sortColumn + " " + sortOrder;
		
		String s_searchId= (String) session.getAttribute(S_SEARCH_ID);
		String s_searchName = (String) session.getAttribute(S_SEARCH_NAME);
		if(s_searchId !=null && s_searchName != null ) {
			searchData.setSearchID(s_searchId);
			searchData.setSearchName(s_searchName);
			
			if (s_searchId.equals("")&& s_searchName.equals("")) {
				List<Mcategory> cates = categoryService.getAllCategoryNotDeleted(dbOrderBy);
				model.addAttribute("categorydatalist", cates);
			}else if(s_searchId.equals("") == false && s_searchName.equals("") == true) {
				// ID not empty, nameis empty
				List<Mcategory> cates = categoryService.getAllCategoryByID(s_searchId, dbOrderBy);
				model.addAttribute("categorydatalist", cates);
			} else if(s_searchId.equals("") == true && s_searchName.equals("") == false) {
				// Name not empty, ID is  empty
				List<Mcategory> cates = categoryService.getAllCategoryByName(s_searchName, dbOrderBy);
				model.addAttribute("categorydatalist", cates);
			} else if(s_searchId.equals("") == false && s_searchName.equals("") == false) {
				List<Mcategory> cates = categoryService.getAllCategoryByNameAndId(s_searchId, s_searchName, dbOrderBy);
				model.addAttribute("categorydatalist", cates);
			}
			
		}else {
			List<Mcategory> cates = categoryService.getAllCategoryNotDeleted(dbOrderBy);
			// We not use this because now use Page
			model.addAttribute("categorydatalist", cates);
		}
		
//		We do the Conversion to Page display
//		Pageable newPage = new PageRequest(pageable.getPageNumber(), 3);
		
		
		
		return "category";
	}
	@PostMapping(path="/categorylist")
	public String CategorySearch(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] CategorySearch called");
		System.out.println("[DBG] searchData: " + searchData.getSearchID() + searchData.getSearchName());
		String dbOrderBy = "";
		String sortOrder = (String) session.getAttribute(S_SORT_ORDER);
		String sortColumn = (String) session.getAttribute(S_SORT_COLUMN);
		if (sortOrder == null && sortColumn == null) {
			sortOrder = "ASC";
			sortColumn = "id";
		}
		dbOrderBy = sortColumn + " " + sortOrder;
		
//
//		List<Mcategory> cates = categoryService.getAllCategoryNotDeleted();
//		model.addAttribute("categorydatalist", cates);
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
		
		session.setAttribute(S_SEARCH_NAME, categoryName);
		session.setAttribute(S_SEARCH_ID, categoryId);
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
		model.addAttribute("infomessage", "Deleted item " + searchData.getDeleteList().size());
		// Chuyen den action categorylist de no reload lai cac Row cho minh
		return "category";
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
		String randomID = UUID.randomUUID().toString().subSequence(0, 13).toString();
		
		Mcategory cate = new Mcategory();
		cate.setDeleteFlag(false);
		cate.setId(randomID);
		cate.setName(categorynewdata.getName());
	
		categoryService.insertCategory(cate);
	
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
		cate.setDeleteFlag(false);
		
		categoryService.updateCategoryByID(cate);
	
		return "redirect:/categorylist";
	}
	
	@GetMapping("/categorylist/sortid")
	public String CategorySortID(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] CategorySortID called");

		// VI la click vao cot ID, nen tieu chi sort se la cot ID
		session.setAttribute(S_SORT_COLUMN, "id");
		String sortOrder = (String) session.getAttribute(S_SORT_ORDER);
		if (sortOrder != null) {
			// Thay doi cot Sort, neu la tang dan thi xet la giam dan
			if(sortOrder.equals("ASC")) {
				sortOrder = "DESC";
			} else if(sortOrder.equals("DESC")) {
				sortOrder = "ASC";
			}
			// ta luu tieu chi Sort vao session
			session.setAttribute(S_SORT_ORDER, sortOrder);
		} else {
			// Mac dinh la Tang dan neu ko chua tung co session
			session.setAttribute(S_SORT_ORDER, "ASC");
		}
		// Bay gio chuyen sang search controller voi nhung gia tri minh luu vao session roi
		return "redirect:/categorylist";
	}
	
	@GetMapping("/categorylist/sortname")
	public String CategorySortName(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] CategorySortID called");
		
		session.setAttribute(S_SORT_COLUMN, "name");
		String sortOrder = (String) session.getAttribute(S_SORT_ORDER);
		if (sortOrder != null) {
			if(sortOrder.equals("ASC")) {
				sortOrder = "DESC";
			} else if(sortOrder.equals("DESC")) {
				sortOrder = "ASC";
			}
			session.setAttribute(S_SORT_ORDER, sortOrder);
		} else {
			session.setAttribute(S_SORT_ORDER, "ASC");
		}
		return "redirect:/categorylist";
	}
}
