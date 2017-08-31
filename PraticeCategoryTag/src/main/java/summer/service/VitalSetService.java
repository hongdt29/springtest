package summer.service;

import summer.db.entity.Mvitalset;

public interface VitalSetService {
	public Mvitalset getVital(int managerNo);
	public int saveVital(Mvitalset vital);
}
