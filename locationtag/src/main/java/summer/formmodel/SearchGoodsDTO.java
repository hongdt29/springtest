package summer.formmodel;

import java.util.ArrayList;
import java.util.List;

import summer.db.entity.CompositeTGoodsResult;

public class SearchGoodsDTO {
	private String id;
	private String name;
	private String remark;
	
	private List<String> categoryId;
	private List<String> floorId;
	private List<String> companyId;
	private String tagId;
	private List<CompositeTGoodsResult> results;
	
	private String orderCause;
	private int offset;
	private int limit;
	private int totalCount;
	public SearchGoodsDTO()
	{
		id = "";
		name = "";
		remark = "";
		categoryId = new ArrayList<String>();
		floorId = new ArrayList<String>();
		companyId = new ArrayList<String>();
		results = new ArrayList<CompositeTGoodsResult>();
		tagId = "";
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getOrderCause() {
		return orderCause;
	}

	public void setOrderCause(String orderCause) {
		this.orderCause = orderCause;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<CompositeTGoodsResult> getResults() {
		return results;
	}

	public void setResults(List<CompositeTGoodsResult> results) {
		this.results = results;
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
	public List<String> getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(List<String> categoryId) {
		this.categoryId = categoryId;
	}
	public List<String> getFloorId() {
		return floorId;
	}
	public void setFloorId(List<String> floorId) {
		this.floorId = floorId;
	}
	public List<String> getCompanyId() {
		return companyId;
	}
	public void setCompanyId(List<String> companyId) {
		this.companyId = companyId;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
}
