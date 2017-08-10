package summer.service;

import java.util.List;

import summer.db.entity.CompositeTGoods;
import summer.db.entity.Mcategory;

public interface IGoodsService {
	public List<CompositeTGoods> getAllGoods(CompositeTGoods condition);
}
