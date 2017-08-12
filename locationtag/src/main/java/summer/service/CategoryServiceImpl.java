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
	public List<Mcategory> getAllCategoryNotDeleted(String orderBy) {
		McategoryExample query = new McategoryExample();
		query.createCriteria().andDeleteflagNotEqualTo(true);
		//set order by clause to all 
		query.setOrderByClause(orderBy);
		//query.setOrderByClause("id DESC");
		
		List<Mcategory> category = categoryMapper.selectByExample(query);
		return category;	
	}

	@Override
	public List<Mcategory> getAllCategoryByID(String Id, String orderBy) {
		McategoryExample query = new McategoryExample();
		query.createCriteria().andIdLike("%" + Id + "%").andDeleteflagNotEqualTo(true);
		query.setOrderByClause(orderBy);
		
		List<Mcategory> category = categoryMapper.selectByExample(query);
		return category;	
	}


	@Override
	public List<Mcategory> getAllCategoryByName(String name, String orderBy) {
		McategoryExample query = new McategoryExample();
		query.createCriteria().andNameLike("%" + name + "%").andDeleteflagNotEqualTo(true);
		query.setOrderByClause(orderBy);
		//andNameEqualTo(name)   -> andNameLike("%" + name + "%")
		List<Mcategory> category = categoryMapper.selectByExample(query);
		return category;	
		
	}

	@Override
	public List<Mcategory> getAllCategoryByNameAndId(String ID, String name, String orderBy) {
		McategoryExample query = new McategoryExample();
		query.createCriteria().andIdLike("%" + ID + "%").andNameLike("%" + name + "%").
		andDeleteflagNotEqualTo(true);
		query.setOrderByClause(orderBy);
		
		List<Mcategory> category = categoryMapper.selectByExample(query);
		return category;	
	}

	@Override
	public boolean updateDeleteFlag(String Id) {
		// Dau tien ta phai tim xem voi Id nay co Row nao khong
		McategoryExample query = new McategoryExample();
		query.createCriteria().andIdEqualTo(Id);

		List<Mcategory> category = categoryMapper.selectByExample(query);
		if (category.size() == 1) {
			// O day nghia la da tim duoc it nhat 1 row, set Delete Flag to true
			Mcategory currentRow = category.get(0);
			// int version = currentRow.getVersion();
			currentRow.setDeleteflag(true);
			
			// Now update DB
			categoryMapper.updateByExample(currentRow, query);
			return true;
		} else {
			// Return false if not found in DB
			return false;
		}
	}

	@Override
	public void insertCategory(Mcategory cate) {
		categoryMapper.insert(cate);
	}

	@Override
	public void updateCategoryByID(Mcategory cate) {
//		McategoryExample query = new McategoryExample();
//		query.createCriteria().andIdEqualTo(Id);
//		List<Mcategory> category = categoryMapper.selectByExample(query);
//		if (category.size() == 1) {
//			Mcategory currentRow = category.get(0);
//			int version = currentRow.getVersion();
//			currentRow.setVersion(version + 1);
//			categoryMapper.updateByExample(currentRow, query);
//		}
		
		categoryMapper.updateByPrimaryKey(cate);
	}

}
