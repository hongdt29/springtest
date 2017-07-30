package summer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.db.client.McategoryMapper;
import summer.db.entity.Mcategory;
import summer.db.entity.McategoryExample;

@Service
public class CategoryServiceImpl implements ICategoryService{
	@Autowired
	private McategoryMapper categoryMapper;
	
	@Override
	public List<Mcategory> getAllCategoryNotDeleted() {
		McategoryExample query = new McategoryExample();
		query.createCriteria();
		
		return categoryMapper.selectByExample(query);
	}

	@Override
	public List<Mcategory> getAllCategoryByID(String ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Mcategory> getAllCategoryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
