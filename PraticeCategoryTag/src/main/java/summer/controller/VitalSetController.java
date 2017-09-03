package summer.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
	public String showPage(@ModelAttribute("vitalForm") VitalForm vitalForm,
			Model model) {
		System.out.println("Vitalset Get");
		try {
			Mvitalset vitalData = vitalService.getVitalData(1);
			
			vitalForm.setHeartrateMax(vitalData.getHeartrateMax());
			vitalForm.setHeartrateMin(vitalData.getHeartrateMin());
			vitalForm.setTemperatureMax(vitalData.getTemperatureMax());
			vitalForm.setTemperatureMin(vitalData.getTemperatureMin());
			vitalForm.setVersion(vitalData.getVersion());
			vitalForm.setManagerNo(vitalData.getManagerNo());
		} catch (Exception e) {
			model.addAttribute("infoMsg", "get fail, msg:" + e.getMessage());
		}
		return "vitalset";		
		
	}
	
	@PostMapping(value = "/vitalset", params = "check")
	public String vitalSetSave(@ModelAttribute("vitalForm") @Valid VitalForm vitalForm, 
			BindingResult bindingResult,
			Model model) {
		System.out.println("Vitalset Post check");
		if (bindingResult.hasErrors()) {
			return "vitalset";
		}
		
		
		if (vitalForm.getHeartrateMax() > 999) {
			bindingResult.rejectValue("heartrateMax", "", "HR need smaller 3 charatter");
			return "vitalset";
		}
		
		
		
	    int compare1 = vitalForm.getTemperatureMax().compareTo(vitalForm.getTemperatureMin());
	    if (compare1 == -1) {
	    	model.addAttribute("errorMsg", "Input the temperatureMin smaller temparaturemax ");
	    	return "vitalset";
	    }
	    if (vitalForm.getHeartrateMax()< vitalForm.getHeartrateMin()) {
	    	model.addAttribute("errorMsg", "Input the HeartrateMin smaller HeartrateMax ");
	    	return "vitalset";
	    }
	    model.addAttribute("confirmMsg", "Do u want to update Data");
	    
		return "vitalset";
	}
	
	@PostMapping(value = "/vitalset" , params ="save")
	public String vitalSetToSave(@ModelAttribute("vitalForm") VitalForm vitalForm,
			 Model model) {
		System.out.println("Vitalset Post save");
		try {
			Mvitalset vitalData = vitalService.getVitalData(1);
			// compare version trong DB voi version trong FOrm
			if (vitalData.getVersion() != vitalForm.getVersion()) {
				model.addAttribute("infoMsg", "Data is updating by another user");
				return "vitalset";
			}else {
				vitalData.setTemperatureMax(vitalForm.getTemperatureMax());
				vitalData.setTemperatureMin(vitalForm.getTemperatureMin());
				vitalData.setHeartrateMax(vitalForm.getHeartrateMax());
				vitalData.setHeartrateMin(vitalForm.getHeartrateMin());
				vitalData.setVersion(vitalForm.getVersion());
				vitalData.setManagerNo(1);
				
					int updateVital = vitalService.updateVitalData(vitalData);
					if (updateVital == 0) {
						model.addAttribute("infoMsg", "update fail");
					}else {
						model.addAttribute("infoMsg", "update successful");
					};
				return "vitalset";
			}
		} catch (Exception e) {
			model.addAttribute("infoMsg", "update fail, msg:" + e.getMessage());
			return "vitalset";
		}
	}

}
