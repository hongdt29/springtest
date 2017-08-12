package summer.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import summer.db.entity.Mcompany;
import summer.db.entity.Mfloor;
import summer.db.entity.Mtag;
import summer.formmodel.CategoryCreateForm;
import summer.formmodel.CategoryEditForm;
import summer.formmodel.CategorySearchForm;
import summer.formmodel.GoodsForm;
import summer.service.ICategoryService;
import summer.service.ICompanyService;
import summer.service.IFloorService;
import summer.service.IGoodsService;
import summer.service.ITagService;
import summer.util.PageWrapper;

@Controller
public class GoodsController {
	public static String SESSION_FLOOR = "s_goods_floor";
	public static String SESSION_GOODSID = "s_goods_goodsid";
	public static String SESSION_GOODSNAME = "s_goods_goodsname";
	public static String SESSION_CATEGORY = "s_goods_category";
	public static String SESSION_COMPANY = "s_goods_company";
	public static String SESSION_TAG = "s_goods_tag";
	public static String SESSION_REMARK = "s_goods_remark";
	
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
	
	@GetMapping("/goodslist")
	public String GoodsListGet(@ModelAttribute("goodsform") GoodsForm goodsform,
			Model model){
		System.out.println("[DBG] GoodsList called");
		
		// 1. Load data tu DB len de dua vao dropdown list
		//   Tach thanh 1 ham rieng de cho de quan ly, xem ham nay phia ben duoi
		loadDataForDropdown(model);
		
		// 2. Load cac data cua Search FOrm cu tren session vao bien "goodsform"
		//		nho do ma cac Text se lai duoc hien thi tren Text box
		loadFromSession(goodsform);
		
		// 3. Thuc hien viec search trong DB
		List<CompositeTGoods> goods = searchDataFromDB(goodsform);
		model.addAttribute("goodslist", goods);

		return "goods";
	}
	
	@PostMapping(value="/goodslist", params="btnSearch")
	public String GoodsListSearch(@ModelAttribute("goodsform") GoodsForm goodsform,
			Model model){
		System.out.println("[DBG] GoodsListSearch called:" + goodsform.getCategoryId());
		
		// 1. Load data tu DB len de dua vao dropdown list
		//   Tach thanh 1 ham rieng de cho de quan ly, xem ham nay phia ben duoi
		loadDataForDropdown(model);
		
		// 2. Thuc hien viec search trong DB
		List<CompositeTGoods> goods = searchDataFromDB(goodsform);
		model.addAttribute("goodslist", goods);

		// 3. store cac gia tri search vao session
		storeToSession(goodsform);
		
		
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
			loadDataForDropdown(model);
			loadFromSession(goodsform);
			List<CompositeTGoods> goods = searchDataFromDB(goodsform);
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
			loadDataForDropdown(model);
			loadFromSession(goodsform);
			List<CompositeTGoods> goods = searchDataFromDB(goodsform);
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
			loadDataForDropdown(model);
			loadFromSession(goodsform);
			List<CompositeTGoods> goods = searchDataFromDB(goodsform);
			model.addAttribute("goodslist", goods);
			
			return "goods";
		}
		return "goods";
	}
	
	private List<CompositeTGoods> searchDataFromDB(GoodsForm goodsform)
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
		
		if (goodsform.getCategoryId().isEmpty() == false) {
			condition.setIdcategory(goodsform.getCategoryId());
		}
		if (goodsform.getFloorId().isEmpty() == false) {
			condition.setIdfloor(goodsform.getFloorId());
		}
		if (goodsform.getCompanyId().isEmpty() == false) {
			condition.setIdcompany(goodsform.getCompanyId());
		}

		// Sau khi co dieu kien search roi  thi thuc hien search by service
		return goodsService.getAllGoods(condition);
	}
	private void loadDataForDropdown(Model model) {
		// Lay het cac List cho Dropdown thu DB
		List<Mcategory> allCates = categoryService.getAllCategoryNotDeleted("id ASC");
		List<Mfloor> floors = floorService.getAllFloors();
		List<Mcompany> companies = companyService.getAllCompany(); 
		List<Mtag> tags = tagService.getAllTags();
		
		// Xu ly them truong hop Default, add them 1 element vao dau tien cua List
		
		// Truoc het tao 1 bien moi, set ID = % (nghia la search all), value "-"
		// nghia la tren cai dropdown se co cai - dau tien
		Mcompany defaultComp = new Mcompany();
		defaultComp.setName("-");
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
		defaultCate.setName("-");
		defaultCate.setId("%");
		if (allCates != null) {
			allCates.add(0, defaultCate);
		} else {
			allCates = new ArrayList<Mcategory>();
			allCates.add(0, defaultCate);
		}
		Mfloor defaultFloor = new Mfloor();
		defaultFloor.setName("-");
		defaultFloor.setId("%");
		if (floors != null) {
			floors.add(0, defaultFloor);
		} else {
			floors = new ArrayList<Mfloor>();
			floors.add(0, defaultFloor);
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
		
		String s5 = (String) session.getAttribute(SESSION_FLOOR);
		if (s5 != null) {
			formData.setFloorId(s5);
		}
		
		String s6 = (String) session.getAttribute(SESSION_CATEGORY);
		if (s6 != null) {
			formData.setCategoryId(s6);
		}
		
		String s7 = (String) session.getAttribute(SESSION_COMPANY);
		if (s7 != null) {
			formData.setCompanyId(s7);
		}
	}
}
