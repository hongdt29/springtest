package summer.service;

import java.util.List;

import summer.db.entity.Mcategory;

public interface ICategoryService {
	public List<Mcategory> getAllCategoryNotDeleted();
	public List<Mcategory> getAllCategoryByID(String ID);
	public List<Mcategory> getAllCategoryByName(String name);
	public List<Mcategory> getAllCategoryByNameAndId(String ID, String name);
	
	public boolean updateDeleteFlag(String Id);
}
