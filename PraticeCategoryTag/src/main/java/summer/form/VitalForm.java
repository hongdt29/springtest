package summer.form;

import java.math.BigDecimal;

public class VitalForm {
	private int managerNo;
	private BigDecimal temperatureMax;
	private BigDecimal temperatureMin;
	private int version;
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getManagerNo() {
		return managerNo;
	}
	public void setManagerNo(int managerNo) {
		this.managerNo = managerNo;
	}
	public BigDecimal getTemperatureMax() {
		return temperatureMax;
	}
	public void setTemperatureMax(BigDecimal temperatureMax) {
		this.temperatureMax = temperatureMax;
	}
	public BigDecimal getTemperatureMin() {
		return temperatureMin;
	}
	public void setTemperatureMin(BigDecimal temperatureMin) {
		this.temperatureMin = temperatureMin;
	}
	public int getHeartrateMax() {
		return heartrateMax;
	}
	public void setHeartrateMax(int heartrateMax) {
		this.heartrateMax = heartrateMax;
	}
	public int getHeartrateMin() {
		return heartrateMin;
	}
	public void setHeartrateMin(int heartrateMin) {
		this.heartrateMin = heartrateMin;
	}
	private int heartrateMax;
	private int heartrateMin;
}
