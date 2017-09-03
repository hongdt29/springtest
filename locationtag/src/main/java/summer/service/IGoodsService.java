package summer.service;

import java.util.List;

import summer.db.entity.CompositeTGoods;
import summer.db.entity.CompositeTGoodsResult;
import summer.db.entity.Mcategory;
import summer.db.entity.Tgoods;
import summer.formmodel.SearchGoodsDTO;

public interface IGoodsService {
	public SearchGoodsDTO searchGoods(SearchGoodsDTO dto);
	
	public List<CompositeTGoodsResult> getAllGoods(CompositeTGoods condition);
	
	public int updateDeleteFlagToTrueByID(String id);
	
	public int insertNewGoods(Tgoods goods);
}
