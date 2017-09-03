package summer.service;

import summer.db.entity.Mvitalset;

public interface VitalSetService {
	public Mvitalset getVitalData(int managerNo);
	public int updateVitalData(Mvitalset vital);

}
