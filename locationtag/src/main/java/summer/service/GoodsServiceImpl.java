package summer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.db.client.McategoryMapper;
import summer.db.client.TgoodsMapper;
import summer.db.entity.CompositeTGoods;
import summer.db.entity.Mcategory;
import summer.db.entity.McategoryExample;
import summer.db.entity.Muser;

@Service
public class GoodsServiceImpl implements IGoodsService {
	@Autowired
	private TgoodsMapper mapperGoods;

	@Override
	public List<CompositeTGoods> getAllGoods(CompositeTGoods condition) {
		return mapperGoods.getAllGoods(condition);
	}

	@Override
	public int updateDeleteFlagToTrueByID(String id) {
		return mapperGoods.updateDeleteFlagToTrueByID(id);
	}
	

}
