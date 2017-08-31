package summer.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import summer.db.entity.Mvitalset;
import summer.form.GoodsCategoryForm;
import summer.form.VitalForm;
import summer.service.VitalSetService;
@Controller
public class VitalSetController {
	@Autowired
	private VitalSetService vitalService;
	
	@GetMapping("/vitalset")
	public String vitalSet(@ModelAttribute("vitalForm") VitalForm vitalForm,
			Model model) {
		System.out.println("Vitalset Get");
		Mvitalset vitals = vitalService.getVital(1);
		if (vitals!= null) {
			vitalForm.setHeartrateMax(vitals.getHeartrateMax());
			vitalForm.setHeartrateMin(vitals.getHeartrateMin());
			vitalForm.setTemperatureMax(vitals.getTemperatureMax());
			vitalForm.setTemperatureMin(vitals.getTemperatureMin());
			vitalForm.setManagerNo(vitals.getManagerNo());
			vitalForm.setVersion(vitals.getVersion());
		} else {
			model.addAttribute("errorMsg", "Not FOund Vital info");
		}
		return "vitalset";		
		
	}
	
	@PostMapping("/vitalset")
	public String vitalSetPost(@ModelAttribute("vitalForm") VitalForm vitalForm,
			Model model, @RequestParam("force") String force) {
		System.out.println("Vitalset Post");
		if(vitalForm.getHeartrateMin()> vitalForm.getHeartrateMax()) {
			model.addAttribute("errorMsg", "Input HeartrateMin small than HeartrateMax");
			return "vitalset";
		}
		if(vitalForm.getTemperatureMin().compareTo(vitalForm.getTemperatureMax()) > 0) {
			model.addAttribute("errorMsg", "Input TemperatureMin small than TemperatureMax");
		}
		Mvitalset vitals = vitalService.getVital(1);
		if(!vitals.getVersion().equals(vitalForm.getVersion()) && force.equals("no") == true){
			model.addAttribute("confirmEditMsg", "Another user change this, do you want to edit ?");
			return "vitalset";
		
		}else {
			vitals.setHeartrateMax(vitalForm.getHeartrateMax());
			vitals.setHeartrateMin(vitalForm.getHeartrateMin());
			vitals.setTemperatureMax(vitalForm.getTemperatureMax());
			vitals.setTemperatureMin(vitalForm.getTemperatureMin());
			vitals.setLastUpdateTime(new Date());
			vitals.setDeleteFlag(false);
			vitals.setManagerNo(1);
			
			
			vitalService.saveVital(vitals);
			
			model.addAttribute("infoMsg", "Save succes");
		}
		
		
		
		return "vitalset";
	}

}
