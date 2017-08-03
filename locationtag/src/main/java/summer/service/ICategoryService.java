package summer.service;

import java.util.List;

import summer.db.entity.Mcategory;

public interface ICategoryService {
	public List<Mcategory> getAllCategoryNotDeleted(String orderBy);
	public List<Mcategory> getAllCategoryByID(String ID, String orderBy);
	public List<Mcategory> getAllCategoryByName(String name, String orderBy);
	public List<Mcategory> getAllCategoryByNameAndId(String ID, String name, String orderBy);
	
	public boolean updateDeleteFlag(String Id);
	
	public void insertCategory(Mcategory cate);

	public void updateCategoryByID(Mcategory cate);
}
