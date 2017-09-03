package summer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.db.client.McategoryMapper;
import summer.db.client.TgoodsMapper;
import summer.db.entity.CompositeTGoods;
import summer.db.entity.CompositeTGoodsResult;
import summer.db.entity.Mcategory;
import summer.db.entity.McategoryExample;
import summer.db.entity.Muser;
import summer.db.entity.Tgoods;
import summer.db.entity.TgoodsExample;
import summer.formmodel.SearchGoodsDTO;

@Service
public class GoodsServiceImpl implements IGoodsService {
	@Autowired
	private TgoodsMapper mapperGoods;

	@Override
	public List<CompositeTGoodsResult> getAllGoods(CompositeTGoods condition) {
		return mapperGoods.getAllGoods(condition);
	}

	@Override
	public int updateDeleteFlagToTrueByID(String id) {
		return mapperGoods.updateDeleteFlagToTrueByID(id);
	}

	@Override
	public int insertNewGoods(Tgoods goods) {
		return mapperGoods.insert(goods);
		
	}

	@Override
	public SearchGoodsDTO searchGoods(SearchGoodsDTO dto) {
		CompositeTGoods condition = new CompositeTGoods();
		condition.setId(dto.getId());
		condition.setIdcategory(dto.getCategoryId());
		condition.setIdcompany(dto.getCompanyId());
		condition.setIdfloor(dto.getFloorId());
		condition.setIdtag(dto.getTagId());
		condition.setRemark(dto.getRemark());
		condition.setName(dto.getName());
		condition.setOrderBy(dto.getOrderCause());
		condition.setDeleteFlag(false);
		
		dto.setResults(mapperGoods.getAllGoods(condition));
		
		return dto;
	}
	

}
