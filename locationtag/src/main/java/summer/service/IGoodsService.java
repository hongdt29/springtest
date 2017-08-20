package summer.service;

import java.util.List;

import summer.db.entity.CompositeTGoods;
import summer.db.entity.CompositeTGoodsResult;
import summer.db.entity.Mcategory;
import summer.db.entity.Tgoods;

public interface IGoodsService {
	public List<CompositeTGoodsResult> getAllGoods(CompositeTGoods condition);
	
	public int updateDeleteFlagToTrueByID(String id);
	
	public int insertNewGoods(Tgoods goods);
}
