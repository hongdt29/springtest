package summer.form;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class VitalForm {
	private int managerNo;

	@NotNull(message="Temp Max need not Null")
	@Digits(integer=2, fraction=2, message="Temp Max must be 2 interger 2 digit")
	private BigDecimal temperatureMax;
	
	@NotNull
	@Digits(integer=2, fraction=2, message="Temp Max must be 2 interger 2 digit")
	private BigDecimal temperatureMin;

	@NotNull
	private int heartrateMax;
	
	@NotNull
	private int heartrateMin;
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
	
}
