package summer.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.db.client.MvitalsetMapper;
import summer.db.entity.Mvitalset;
import summer.db.entity.MvitalsetExample;
import summer.service.VitalSetService;

@Service
public class VitalSetServiceImpl implements VitalSetService {
	@Autowired
	MvitalsetMapper mapper;
	
	@Override
	public Mvitalset getVital(int managerNo) {
		MvitalsetExample query = new MvitalsetExample();
		query.createCriteria().andManagerNoEqualTo(managerNo).andDeleteFlagNotEqualTo(true);
		List<Mvitalset> mvitalset =  mapper.selectByExample(query);
		if (mvitalset !=null && mvitalset.size()==1){
			return mvitalset.get(0);
		}else {
			return null;
		}
			
	}

	@Override
	public int saveVital(Mvitalset vital) {
		MvitalsetExample query = new MvitalsetExample();
		int currentVersion = vital.getVersion();
		vital.setVersion(currentVersion +1);
		query.createCriteria().andDeleteFlagNotEqualTo(true).andManagerNoEqualTo(vital.getManagerNo());
		return mapper.updateByExampleSelective(vital, query);
	}

}
