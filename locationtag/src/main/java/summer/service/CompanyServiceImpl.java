package summer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.db.client.McategoryMapper;
import summer.db.client.McompanyMapper;
import summer.db.client.MfloorMapper;
import summer.db.entity.Mcategory;
import summer.db.entity.McategoryExample;
import summer.db.entity.Mcompany;
import summer.db.entity.McompanyExample;
import summer.db.entity.Mfloor;
import summer.db.entity.MfloorExample;
import summer.db.entity.Muser;

@Service
public class CompanyServiceImpl implements ICompanyService{
	@Autowired
	private McompanyMapper mapper;

	@Override
	public List<Mcompany> getAllCompany() {
		McompanyExample query = new McompanyExample();
		query.createCriteria();
		
		return mapper.selectByExample(query);
	}

	
}
