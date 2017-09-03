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
	public Mvitalset getVitalData(int managerNo) {
		MvitalsetExample query = new MvitalsetExample();
		query.createCriteria().andDeleteFlagEqualTo(false).andManagerNoEqualTo(managerNo);
		List<Mvitalset> vitalData = mapper.selectByExample(query);
		if (vitalData != null && !vitalData.isEmpty()) {
			return mapper.selectByExample(query).get(0);
		}
		return null;
	}
	public int updateVitalData(Mvitalset vital) {
		MvitalsetExample query = new MvitalsetExample();
		query.createCriteria().andDeleteFlagEqualTo(false).andManagerNoEqualTo(vital.getManagerNo());
		int currentVersion = vital.getVersion();
		vital.setVersion(currentVersion +1);
		return mapper.updateByExampleSelective(vital, query);
	}
	
}
