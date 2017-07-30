package summer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.db.client.McategoryMapper;
import summer.db.entity.Mcategory;
import summer.db.entity.McategoryExample;
import summer.db.entity.Muser;

@Service
public class CategoryServiceImpl implements ICategoryService{
	@Autowired
	private McategoryMapper categoryMapper;
	
	@Override
	public List<Mcategory> getAllCategoryNotDeleted() {
		McategoryExample query = new McategoryExample();
		query.createCriteria().andDeleteFlagNotEqualTo(true);
		List<Mcategory> category = categoryMapper.selectByExample(query);
		return category;	
	}

	@Override
	public List<Mcategory> getAllCategoryByID(String Id) {
		McategoryExample query = new McategoryExample();
		query.createCriteria().andIdEqualTo(Id).andDeleteFlagNotEqualTo(true);

		List<Mcategory> category = categoryMapper.selectByExample(query);
		return category;	
	}


	@Override
	public List<Mcategory> getAllCategoryByName(String name) {
		McategoryExample query = new McategoryExample();
		query.createCriteria().andNameEqualTo(name).andDeleteFlagNotEqualTo(true);

		List<Mcategory> category = categoryMapper.selectByExample(query);
		return category;	
		
	}

	@Override
	public List<Mcategory> getAllCategoryByNameAndId(String ID, String name) {
		McategoryExample query = new McategoryExample();
		query.createCriteria().andIdEqualTo(ID).andNameEqualTo(name).andDeleteFlagNotEqualTo(true);

		List<Mcategory> category = categoryMapper.selectByExample(query);
		return category;	
	}

}
