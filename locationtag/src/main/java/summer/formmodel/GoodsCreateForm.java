package summer.formmodel;

import java.util.ArrayList;
import java.util.List;

import summer.db.entity.Mcategory;

public class GoodsCreateForm {
	private String id;
	private String name;
	private String remark;
	
	private String categoryId;
	private String floorId;
	private String companyId;
	private String tagId;

	public GoodsCreateForm()
	{
		id = "";
		name = "";
		remark = "";
		categoryId = "";
		floorId = "";
		companyId = "";
		tagId = "";
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
}
