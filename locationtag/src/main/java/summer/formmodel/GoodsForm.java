package summer.formmodel;

import java.util.List;

import summer.db.entity.Mcategory;

public class GoodsForm {
	private String categoryId;
	private List<String> deleteList;
	public List<String> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<String> deleteList) {
		this.deleteList = deleteList;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
