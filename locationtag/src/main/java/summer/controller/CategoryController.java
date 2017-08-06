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
		// Bien session nay the hien dang sap xep theo column nao, id or name
		String s_sortColum = (String) session.getAttribute(S_SORT_COLUMN);
		// Bien nay the hien dang sap xep theo order nao, ASC or DESC
		String s_sortOrder = (String) session.getAttribute(S_SORT_ORDER);
		// Kiem tra xem 2 bien session Sap xep theo Cot va theo Thu tu duoc luu
		// trong session chua
		if (s_sortColum== null || s_sortOrder == null ) {
			// Truong hop nay chua duoc luu, ta set 1 gia tri default
			dbOrderBy = "id ASC";
			session.setAttribute(S_SORT_COLUMN, "id");
			session.setAttribute(S_SORT_ORDER, "ASC");
			
			// Set 2 bien nay vao model de hien thi icon sorting tren view
			model.addAttribute("isOrderById", true);
			model.addAttribute("isOrderAsc", true);
		}else {
			// bien OrderBy string de sap xep trong DB
			dbOrderBy = s_sortColum + " " + s_sortOrder;
			
			// COde nay de hien thi icon sap xep
			// Neu dang sap xep theo column name
			if (s_sortColum.equals("name") == true) {
				// vi la dang sap xep theo column name nen bien
				// isOrderById set to false
				model.addAttribute("isOrderById", false);
			} else {
				// Neu khong, nghia la no dang sap xep theo ID
				model.addAttribute("isOrderById", true);
			}
			
			if (s_sortOrder.equals("ASC") == true) {
				// Truong hop nay la dang sap xep tang dan
				model.addAttribute("isOrderAsc", true);
			} else {
				model.addAttribute("isOrderAsc", false);
			}
		}
		System.out.println("OrderBy : " + dbOrderBy);
		
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
		// Lay cac gia tri tu DB ra de tim ra order by clause
		String dbOrderBy = "";
		
		String s_sortColum = (String) session.getAttribute(S_SORT_COLUMN);
		String s_sortOrder = (String) session.getAttribute(S_SORT_ORDER);
		if (s_sortColum== null || s_sortOrder == null ) {
			dbOrderBy = "id ASC";
			session.setAttribute(S_SORT_COLUMN, "id");
			session.setAttribute(S_SORT_ORDER, "ASC");
			
			model.addAttribute("isOrderById", true);
			model.addAttribute("isOrderAsc", true);
		}else {
			dbOrderBy = s_sortColum + " " + s_sortOrder;
			if (s_sortColum.equals("name") == true) {
				model.addAttribute("isOrderById", false);
			} else {
				model.addAttribute("isOrderById", true);
			}
			
			if (s_sortOrder.equals("ASC") == true) {
				model.addAttribute("isOrderAsc", true);
			} else {
				model.addAttribute("isOrderAsc", false);
			}
		}
		System.out.println("OrderBy : " + dbOrderBy);
		
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
		
		// Co 2 bien Session, name S_SORT_COLUMN the hien dang sap xep theo cot nao,
		// S_SORT_ORDER the hien dang sap xep theo Order nao
		
		session.setAttribute(S_SORT_COLUMN, "id");
		String sortOrder = (String) session.getAttribute(S_SORT_ORDER);
		if (sortOrder == null) {
			// Check if session sort order exist or not, this is not exist, default to ASC
			session.setAttribute(S_SORT_ORDER, "ASC");
		}else {
			if( sortOrder.equals("ASC")== true) {
				// Neu dang la ASC thi set bien session to DESC (giam dan) 
				session.setAttribute(S_SORT_ORDER, "DESC");
			}else {
				session.setAttribute(S_SORT_ORDER, "ASC");
			}
		}
		
		// Bay gio chuyen sang search controller voi nhung gia tri minh luu vao session roi
		return "redirect:/categorylist";
	}
	
	@GetMapping("/categorylist/sortname")
	public String CategorySortName(@ModelAttribute("categorydata") CategorySearchForm searchData,
			Model model, HttpServletRequest request, HttpSession session){
		System.out.println("[DBG] CategorySortID called");
		
		// Co 2 bien Session, name S_SORT_COLUMN the hien dang sap xep theo cot nao,
		// S_SORT_ORDER the hien dang sap xep theo Order nao
		
		// O day, vi user click vao Category Name, nen action nay phai set sap xep theo column name
		session.setAttribute(S_SORT_COLUMN, "name");
		
		// Ve sort order, phai lay ra cai order hien tai tu session
		String sortOrder = (String) session.getAttribute(S_SORT_ORDER);
		if (sortOrder == null) {
			// Neu chua co trong session, luu gia tri ban dau la tang dan ASC
			session.setAttribute(S_SORT_ORDER, "ASC");
		}else {
			// Neu co roi, ta phai xet nguoc lai
			if( sortOrder.equals("ASC")== true) {
				session.setAttribute(S_SORT_ORDER, "DESC");
			}else {
				session.setAttribute(S_SORT_ORDER, "ASC");
			}
		}
		
		
		return "redirect:/categorylist";
	}
}
