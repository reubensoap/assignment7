package assignment6master.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import assignment6master.models.CDOffering;
import assignment6master.repositories.CDOfferingRepo;

@RestController
@RequestMapping(value = "/CDOffering")
public class CDOfferingResource {
	
	@Autowired
	CDOfferingRepo cdofferingRepo;
	
	@GetMapping(value = "/all")
	public List<CDOffering> getAll(){
		return cdofferingRepo.findAll();
	}
	
	@GetMapping(value = "/update/{term}/{interest}")
	public List<CDOffering> addCDOffering(@PathVariable("term") final int term, @PathVariable("interest") final double interestRate){
		CDOffering cdoffering = new CDOffering(term, interestRate);
		cdofferingRepo.save(cdoffering);
		return cdofferingRepo.findAll();
	}
}
