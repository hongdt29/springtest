package summer.db.entity;

public class CompositeTGoods {
   private String id;
   private String name;
   private String remark;
   private String idfloor;
   private String idtag;
   private String idcompany;
   private String idcategory;
   private Boolean deleteFlag;

   private String floorname;
   private String categoryname;
   private String companyname;
   
   public CompositeTGoods() {
	   id = "%";
	   name = "%";
	   remark = "%";
	   idfloor = "%";
	   idtag = "%";
	   idcompany = "%";
	   idcategory = "%";
	   floorname = "%";
	   categoryname = "%";
	   companyname = "%";
	   deleteFlag = false;
   }
   
   	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIdfloor() {
		return idfloor;
	}
	public void setIdfloor(String idfloor) {
		this.idfloor = idfloor;
	}
	public String getIdtag() {
		return idtag;
	}
	public void setIdtag(String idtag) {
		this.idtag = idtag;
	}
	public String getIdcompany() {
		return idcompany;
	}
	public void setIdcompany(String idcompany) {
		this.idcompany = idcompany;
	}
	public String getIdcategory() {
		return idcategory;
	}
	public void setIdcategory(String idcategory) {
		this.idcategory = idcategory;
	}
	public String getFloorname() {
		return floorname;
	}
	public void setFloorname(String floorname) {
		this.floorname = floorname;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
}
