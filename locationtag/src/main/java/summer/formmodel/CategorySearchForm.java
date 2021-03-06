package summer.formmodel;

import java.util.List;

public class CategorySearchForm {
	private String searchID;
	private String searchName;
	private List<String> deleteList;
	private String orderColumn;
	private String orderSort;
	
	public String getOrderColumn() {
		return orderColumn;
	}
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
	public String getOrderSort() {
		return orderSort;
	}
	public void setOrderSort(String orderSort) {
		this.orderSort = orderSort;
	}
	public String getSearchID() {
		return searchID;
	}
	public List<String> getDeleteList() {
		return deleteList;
	}
	public void setDeleteList(List<String> deleteList) {
		this.deleteList = deleteList;
	}
	public void setSearchID(String searchID) {
		this.searchID = searchID;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
}
