package summer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.db.client.McategoryMapper;
import summer.db.client.MfloorMapper;
import summer.db.entity.Mcategory;
import summer.db.entity.McategoryExample;
import summer.db.entity.Mfloor;
import summer.db.entity.MfloorExample;
import summer.db.entity.Muser;

@Service
public class FloorServiceImpl implements IFloorService{
	@Autowired
	private MfloorMapper mapper;

	@Override
	public List<Mfloor> getAllFloors() {
		MfloorExample query = new MfloorExample();
		query.createCriteria();
		
		return mapper.selectByExample(query);
	}
	
}
