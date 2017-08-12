package summer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.db.client.McategoryMapper;
import summer.db.client.MfloorMapper;
import summer.db.client.MtagMapper;
import summer.db.entity.Mcategory;
import summer.db.entity.McategoryExample;
import summer.db.entity.Mfloor;
import summer.db.entity.MfloorExample;
import summer.db.entity.Mtag;
import summer.db.entity.MtagExample;
import summer.db.entity.Muser;

@Service
public class TagServiceImpl implements ITagService {
	@Autowired
	private MtagMapper mapper;

	@Override
	public List<Mtag> getAllTags() {
		MtagExample query = new MtagExample();
		query.createCriteria();
		
		return mapper.selectByExample(query);
	}

	
}
