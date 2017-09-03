package summer.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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

import summer.db.entity.CompositeTGoods;
import summer.db.entity.CompositeTGoodsResult;
import summer.db.entity.Mcategory;
import summer.db.entity.Mcompany;
import summer.db.entity.Mfloor;
import summer.db.entity.Mtag;
import summer.db.entity.Tgoods;
import summer.formmodel.CategoryCreateForm;
import summer.formmodel.CategoryEditForm;
import summer.formmodel.CategorySearchForm;
import summer.formmodel.GoodsCreateForm;
import summer.formmodel.GoodsForm;
import summer.formmodel.SearchGoodsDTO;
import summer.service.ICategoryService;
import summer.service.ICompanyService;
import summer.service.IFloorService;
import summer.service.IGoodsService;
import summer.service.ITagService;
import summer.util.PageWrapper;

@Controller
//@SessionAttributes(value = {"goodsform"}, types = {GoodsForm.class})
public class GoodsController {
	public static String SESSION_FLOOR = "s_goods_floor";
	public static String SESSION_GOODSID = "s_goods_goodsid";
	public static String SESSION_GOODSNAME = "s_goods_goodsname";
	public static String SESSION_CATEGORY = "s_goods_category";
	public static String SESSION_COMPANY = "s_goods_company";
	public static String SESSION_TAG = "s_goods_tag";
	public static String SESSION_REMARK = "s_goods_remark";
	
	public static String SESSION_ORDERBY = "s_goods_orderby";
	
	public static String SESSION_EXPORTMSG = "s_exportmsg"; // DONE when export OK, Error...
	//when not OK
	public static String SESSION_EXPORTDATA = "s_exportdata";
	
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IFloorService floorService;
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private ITagService tagService;
	@Autowired
	private IGoodsService goodsService;
	
	@Autowired
	private HttpSession session;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@ModelAttribute("goodsform")
	public GoodsForm getSearchForm()
	{
		GoodsForm result = new GoodsForm();
	
		return result;
	}
	
	@GetMapping("/goodslist")
	public String GoodsListGet(@ModelAttribute("goodsform") GoodsForm goodsform,
			Model model){
		System.out.println("[DBG] GoodsList called");
		
		// 1. Load data tu DB len de dua vao dropdown list
		//   Tach thanh 1 ham rieng de cho de quan ly, xem ham nay phia ben duoi
		loadDataForDropdown(model, true);
		loadFromSession(goodsform);
		
		// 2. Load cac data cua Search FOrm cu tren session vao bien "goodsform"
		//		nho do ma cac Text se lai duoc hien thi tren Text box
		loadFromSession(goodsform);
		
		String sessionOrderBy = (String) session.getAttribute(SESSION_ORDERBY);
		if (sessionOrderBy == null) {
			sessionOrderBy = "id ASC";
			session.setAttribute(SESSION_ORDERBY, sessionOrderBy);
		}
		// set thu tu Order by de hien thi icon tren HTML
		model.addAttribute("mOrderBy", sessionOrderBy);
		
		// 3. Thuc hien viec search trong DB
		SearchGoodsDTO searchGoodsDto = new SearchGoodsDTO();
		searchGoodsDto.setFloorId(goodsform.getFloorId());
		searchGoodsDto.setId(goodsform.getId());
		searchGoodsDto.setName(goodsform.getName());
		searchGoodsDto.setRemark(goodsform.getRemark());
		searchGoodsDto.setCategoryId(goodsform.getCategoryId());
		searchGoodsDto.setCompanyId(goodsform.getCompanyId());
		searchGoodsDto.setTagId(goodsform.getTagId());
		searchGoodsDto.setOrderCause(goodsform.getOrderCause());
		
		searchGoodsDto = goodsService.searchGoods(searchGoodsDto);
		
		goodsform.setResults(searchGoodsDto.getResults());
		
		return "goods";
	}
	
	@PostMapping(value="/goodslist", params="btnSearch")
	public String GoodsListSearch(@ModelAttribute("goodsform") GoodsForm goodsform,
			Model model,
			@RequestParam("btnSearch") String btnSearchOrderLogic){
		System.out.println("[DBG] GoodsListSearch called:" + goodsform.getCategoryId());
		goodsform.setDisplayFlag(1);
		
		// 1. Load data tu DB len de dua vao dropdown list
		//   Tach thanh 1 ham rieng de cho de quan ly, xem ham nay phia ben duoi
		loadDataForDropdown(model, true);
		
		// btnSearchOrderLogic the hien Order by information tren form. Neu chua co thi ta set mac dinh
		// id ASC, neu co roi thi ta luu vao session va search DB nhu order nay
		if (btnSearchOrderLogic == null) {
			btnSearchOrderLogic = "id ASC";
		}
		if (btnSearchOrderLogic.equals("Search")) {
			String sessionOrderBy = (String) session.getAttribute(SESSION_ORDERBY);
			if (sessionOrderBy == null) {
				sessionOrderBy = "id ASC";
				session.setAttribute(SESSION_ORDERBY, sessionOrderBy);
			}
			btnSearchOrderLogic = sessionOrderBy;
		} else {
			// Inverse the search order
			if (btnSearchOrderLogic.contains("ASC")) {
				btnSearchOrderLogic = btnSearchOrderLogic.replace("ASC", "DESC");
			} else {
				btnSearchOrderLogic = btnSearchOrderLogic.replace("DESC", "ASC");
			}
		}
			
		session.setAttribute(SESSION_ORDERBY, btnSearchOrderLogic);
		
		// set thu tu Order by de hien thi icon tren HTML
		model.addAttribute("mOrderBy", btnSearchOrderLogic);
		
		goodsform.setOrderCause(btnSearchOrderLogic);
		
		// 3. Thuc hien viec search trong DB
		SearchGoodsDTO searchGoodsDto = new SearchGoodsDTO();
		searchGoodsDto.setFloorId(goodsform.getFloorId());
		searchGoodsDto.setId(goodsform.getId());
		searchGoodsDto.setName(goodsform.getName());
		searchGoodsDto.setRemark(goodsform.getRemark());
		searchGoodsDto.setCategoryId(goodsform.getCategoryId());
		searchGoodsDto.setCompanyId(goodsform.getCompanyId());
		searchGoodsDto.setTagId(goodsform.getTagId());
		searchGoodsDto.setOrderCause(goodsform.getOrderCause());
		
		searchGoodsDto = goodsService.searchGoods(searchGoodsDto);
		List<CompositeTGoodsResult> lstResult = searchGoodsDto.getResults();
		goodsform.setResults(lstResult);
		if (lstResult.size() == 0) {
			// error  1
		} else if (lstResult.size() > 500) {
			// error 2
		} else {
			storeToSession(goodsform);
		}

		return "goods";
	}
	
	@PostMapping(value="/goodslist", params="btnDelete")
	public String GoodsListDelete(@ModelAttribute("goodsform") GoodsForm goodsform,
			Model model,
			@RequestParam("btnDelete") String btnDeleteOrderLogic,
			@RequestParam("force") String forceVal,
			@RequestParam("continue") String continueStatus
			){
		System.out.println("[DBG] GoodsListDelete called: order:" + btnDeleteOrderLogic 
				+ ",force:" + forceVal + ","+ goodsform.getDeleteList());
		
		// 1. Neu nguoi dung khong chon Checkbox nao khi delete, hien thi error message
		if (goodsform.getDeleteList() == null || goodsform.getDeleteList().isEmpty()) {
			// Error message here
			model.addAttribute("errorDelete", "Please select 1 item");
			
			// Sau do lai Search lai data 1 lan nua de hien thi
			loadDataForDropdown(model, false);
			loadFromSession(goodsform);
			
			String sessionOrderBy = (String) session.getAttribute(SESSION_ORDERBY);
			if (sessionOrderBy == null) {
				sessionOrderBy = "id ASC";
				session.setAttribute(SESSION_ORDERBY, sessionOrderBy);
			}
			model.addAttribute("mOrderBy", sessionOrderBy);
			List<CompositeTGoodsResult> goods = searchDataFromDB(goodsform, sessionOrderBy);
			model.addAttribute("goodslist", goods);

			return "goods";
		}
		
		// O day neu nguoi dung da chon, lan dau tien thi bien "force" se bang no,
		//  nen ham if nay se chay vao
		if (goodsform.getDeleteList().size() > 0 && 
				forceVal.equals("no")==true) {
			
			// Ta set bien "confirmDelMsg" de Message dialog co the hien thi
			model.addAttribute("confirmDelMsg", "Do you want to del " + 
					goodsform.getDeleteList().size() + " items ?");
			
			// Sau do van phai search lai lan nua
			loadDataForDropdown(model, false);
			loadFromSession(goodsform);
			String sessionOrderBy = (String) session.getAttribute(SESSION_ORDERBY);
			if (sessionOrderBy == null) {
				sessionOrderBy = "id ASC";
				session.setAttribute(SESSION_ORDERBY, sessionOrderBy);
			}
			model.addAttribute("mOrderBy", sessionOrderBy);
			List<CompositeTGoodsResult> goods = searchDataFromDB(goodsform, sessionOrderBy);
			model.addAttribute("goodslist", goods);
			
			return "goods";
		}
		
		if (goodsform.getDeleteList().size() > 0 && 
				forceVal.equals("yes")==true) {
			// Thuc hien viec Delete o day
			for (String delList : goodsform.getDeleteList()) {
				goodsService.updateDeleteFlagToTrueByID(delList);
			}
			
			// Delete xong thi lai search reload lai data
			loadDataForDropdown(model, false);
			loadFromSession(goodsform);
			String sessionOrderBy = (String) session.getAttribute(SESSION_ORDERBY);
			if (sessionOrderBy == null) {
				sessionOrderBy = "id ASC";
				session.setAttribute(SESSION_ORDERBY, sessionOrderBy);
			}
			model.addAttribute("mOrderBy", sessionOrderBy);
			List<CompositeTGoodsResult> goods = searchDataFromDB(goodsform, sessionOrderBy);
			model.addAttribute("goodslist", goods);
			
			return "goods";
		}
		return "goods";
	}
	
	
	@GetMapping("/goods/create")
	public String GoodsCreate(@ModelAttribute("goodscreate") GoodsCreateForm goodscreate,
			Model model){
		System.out.println("[DBG] GoodsCreate called");
		
		loadDataForDropdown(model, true);
		
		model.addAttribute("confirmMsg", "Do you want to create new ?");
		model.addAttribute("infoMsg", "Not Create ");
		return "goodscreate";
	}
	@PostMapping("/goods/create")
	public String GoodsCreatePost(@ModelAttribute("goodscreate") GoodsCreateForm goodscreate,
			Model model){
		System.out.println("[DBG] GoodsCreatePost called");
		
		loadDataForDropdown(model, true);
		
		Tgoods goods = new Tgoods();
		goods.setId(goodscreate.getId());
		goods.setName(goodscreate.getName());
		goods.setRemark(goodscreate.getRemark());
		goods.setIdcategory(goodscreate.getCategoryId());
		goods.setIdcompany(goodscreate.getCompanyId());
		goods.setIdtag(goodscreate.getTagId());
		goods.setIdfloor(goodscreate.getFloorId());
		
		goodsService.insertNewGoods(goods);
		
		model.addAttribute("confirmMsg", "Already created ?");
		model.addAttribute("infoMsg", "Create OK ");
		return "goodscreate";
	}
	@GetMapping("/goodsexportprepare")
	public String GoodsExportPrepare(){
		System.out.println("[DBG] GoodsExportPrepare called");
		
		// >>>>>> 1. Load cac data cua Search FOrm cu tren session vao bien "goodsform"
		GoodsForm goodsform = new GoodsForm();
		loadFromSession(goodsform);
		String sessionOrderBy = (String) session.getAttribute(SESSION_ORDERBY);
		if (sessionOrderBy == null) {
			sessionOrderBy = "id ASC";
			session.setAttribute(SESSION_ORDERBY, sessionOrderBy);
		}
		List<CompositeTGoodsResult> goods = searchDataFromDB(goodsform, sessionOrderBy);
		// <<<<<<< End 1.
		
		if (goods == null || goods.size() == 0) {
			// Ta set error message khi size = 0 here in E0303
			session.setAttribute(SESSION_EXPORTMSG, "Size of export list is 0!");
			// Ta tra lai 1 View, View nay se close ngay khi no load
			return "goodsexportprepare";
		} else {
			// Trong truong hop data OK, ta luu tam thoi vao session roi chuyen sang goodsexport
			session.setAttribute(SESSION_EXPORTDATA, goods);
			return "redirect:/goodsexport";
		}
	}
	
	// only have response body here to return content of CSV file
	@GetMapping("/goodsexport")
	@ResponseBody
	public void GoodsExport(){
		
		// -------------------------Do the query same as current query to get the data
		System.out.println("[DBG] GoodsExport called");
		
		// Ta lay data luu tu session tu action goodsexportprepare
		List<CompositeTGoodsResult> goods = (List<CompositeTGoodsResult>) session.getAttribute(SESSION_EXPORTDATA);
		
		// Convert to String CSV format
		StringBuilder sb = convertToCSVString(goods);
		
		// Remove export data to save memory
		session.removeAttribute(SESSION_EXPORTDATA);
		
		// --- Do the save csv file here to response
		String strExport = sb.toString();
		InputStream is = new ByteArrayInputStream(strExport.getBytes());
		
		try {
			IOUtils.copy(is, response.getOutputStream());
			
			// Calculate the file name
			DateFormat df = new SimpleDateFormat("YYYYMMdd");
			Date now = new Date();
			String fileName = "Goodslist_"+df.format(now)+".csv";
			
			response.setContentType("application/xls");
	        response.setHeader("Content-Disposition","attachment; filename="+fileName);
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
			// Message when save not OK E0009
			session.setAttribute(SESSION_EXPORTMSG, "Error save file!");
			return;
		}
		
		// Ta set export message to DONE de ma khi reload lai goodslist, ta co session value DONE
		//  de hien thi dialog message I0004
		session.setAttribute(SESSION_EXPORTMSG, "DONE");
		
		return;
	}
	
	private StringBuilder convertToCSVString(List<CompositeTGoodsResult> goods) {
		StringBuilder sb = new StringBuilder();
        sb.append("Goods ID,");
        sb.append("Goods Name,");
        sb.append("categoryname,");
        sb.append("companyname,");
        sb.append("remark,");
        sb.append("idtag,");
        sb.append("floorname");
        
        sb.append('\n');
        
        for (CompositeTGoodsResult i : goods) {
			sb.append(""+i.getId()+",");
			sb.append(""+i.getName()+",");
			sb.append(""+i.getCategoryname()+",");
			sb.append(""+i.getCompanyname()+",");
			sb.append(""+i.getRemark()+",");
			sb.append(""+i.getIdtag()+",");
			sb.append(""+i.getFloorname());
			
			sb.append('\n');
		}
        return sb;
	}
	
	
	private List<CompositeTGoodsResult> searchDataFromDB(GoodsForm goodsform, String orderBy)
	{
		// O day class CompositeTGoods the hien cac Dieu kien de search
		// Cac member cua class nay giong nhu tren Form
		// Neu member nao la % thi y nghia la search All
		CompositeTGoods condition = new CompositeTGoods();
		
		// Chi khi co gia tri tren Form thi moi set
		if (goodsform.getId().isEmpty() == false) {
			// Viec cho dau % vao Begin and End nghia la search String Contains
			condition.setId("%" + goodsform.getId() + "%");
		}
		if (goodsform.getName().isEmpty() == false) {
			condition.setName("%" + goodsform.getName()+ "%");
		}
		if (goodsform.getRemark().isEmpty() == false) {
			condition.setRemark("%" + goodsform.getRemark()+ "%");
		}
		if (goodsform.getCategoryId() == null || goodsform.getCategoryId().size() == 0) {
			condition.getIdcategory().add("%");
		} else {
			condition.setIdcategory(goodsform.getCategoryId());
		}
		if (goodsform.getFloorId() == null || goodsform.getFloorId().size() == 0) {
			condition.getIdfloor().add("%");
		} else {
			condition.setIdfloor(goodsform.getFloorId());
		}
		if (goodsform.getCompanyId() == null || goodsform.getCompanyId().size() == 0) {
			condition.getIdcompany().add("%");
		} else {
			condition.setIdcompany(goodsform.getCompanyId());
		}
		condition.setOrderBy(orderBy);
		
		// Sau khi co dieu kien search roi  thi thuc hien search by service
		return goodsService.getAllGoods(condition);
	}
	private void loadDataForDropdown(Model model, boolean isCreateFirstAllItem) {
		// Lay het cac List cho Dropdown thu DB
		List<Mcategory> allCates = categoryService.getAllCategoryNotDeleted("id ASC");
		List<Mfloor> floors = floorService.getAllFloors();
		List<Mcompany> companies = companyService.getAllCompany(); 
		
		if (isCreateFirstAllItem) {
			// Xu ly them truong hop Default, add them 1 element vao dau tien cua List
			
			// Truoc het tao 1 bien moi, set ID = % (nghia la search all), value "-"
			// nghia la tren cai dropdown se co cai - dau tien
			Mcompany defaultComp = new Mcompany();
			defaultComp.setName("all");
			defaultComp.setId("%");
			
			if (companies != null) {
				// Neu nhu list cac Company da co it nhat 1 thanh phan, add them vao dau
				// so 0 o day nghia la add vao index 0 - dau tien
				companies.add(0, defaultComp);
			} else {
				// Neu nhu chua co thanh phan nao, vi companies la null nen phai tao List truoc
				// khi add vao dau
				companies = new ArrayList<Mcompany>();
				companies.add(0, defaultComp);
			}
			// tuong tu cho cac dropdown list khac
			Mcategory defaultCate = new Mcategory();
			defaultCate.setName("all");
			defaultCate.setId("%");
			if (allCates != null) {
				allCates.add(0, defaultCate);
			} else {
				allCates = new ArrayList<Mcategory>();
				allCates.add(0, defaultCate);
			}
			Mfloor defaultFloor = new Mfloor();
			defaultFloor.setName("all");
			defaultFloor.setId("%");
			if (floors != null) {
				floors.add(0, defaultFloor);
			} else {
				floors = new ArrayList<Mfloor>();
				floors.add(0, defaultFloor);
			}
		}
		// Sau khi co cac list data thi tat set vao bien model de hien thi tren view
		model.addAttribute("dropFloor", floors);
		model.addAttribute("dropCategory", allCates);
		model.addAttribute("dropCompany", companies);
	}
	private void storeToSession(GoodsForm formData) {
		session.setAttribute(SESSION_GOODSID, formData.getId());
		session.setAttribute(SESSION_GOODSNAME, formData.getName());
		session.setAttribute(SESSION_REMARK, formData.getRemark());
		session.setAttribute(SESSION_TAG, formData.getTagId());
		session.setAttribute(SESSION_CATEGORY, formData.getCategoryId());
		session.setAttribute(SESSION_COMPANY, formData.getCompanyId());
		session.setAttribute(SESSION_FLOOR, formData.getFloorId());
	}
	
	// Load cac data tu Session vao bien formData
	private void loadFromSession(GoodsForm formData) {

		String s1 = (String) session.getAttribute(SESSION_GOODSID);
		// Neu session da co value thi add vao form data
		if (s1 != null) {
			formData.setId(s1);
		}
		
		String s2 = (String) session.getAttribute(SESSION_GOODSNAME);
		if (s2 != null) {
			formData.setName(s2);
		}
		
		String s3 = (String) session.getAttribute(SESSION_REMARK);
		if (s3 != null) {
			formData.setRemark(s3);
		}
		
		String s4 = (String) session.getAttribute(SESSION_TAG);
		if (s4 != null) {
			formData.setTagId(s4);
		}
		
		List<String> s5 = (List<String>) session.getAttribute(SESSION_FLOOR);
		if (s5 != null) {
			formData.setFloorId(s5);
		}
		
		List<String> s6 = (List<String>) session.getAttribute(SESSION_CATEGORY);
		if (s6 != null) {
			formData.setCategoryId(s6);
		}
		
		List<String> s7 = (List<String>) session.getAttribute(SESSION_COMPANY);
		if (s7 != null) {
			formData.setCompanyId(s7);
		}
	}
}
