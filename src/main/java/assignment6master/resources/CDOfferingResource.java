package assignment6master.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import assignment6master.models.CDOffering;
import assignment6master.repositories.CDOfferingRepo;

@RestController
@RequestMapping(value = "/CDOffering")
public class CDOfferingResource {
	
	@Autowired
	CDOfferingRepo cdofferingRepo;
	
	@GetMapping(value = "/")
	public List<CDOffering> getAll(){
		return cdofferingRepo.findAll();
	}
	
	@PostMapping(value = "/")
	public CDOffering addCDOffering(@RequestBody CDOffering offer){
		return cdofferingRepo.save(offer);
	}
}
