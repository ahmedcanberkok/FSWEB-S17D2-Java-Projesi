package rest;

import Validation.DeveloperValidation;
import jakarta.annotation.PostConstruct;
import mapping.DeveloperResponse;
import model.Developer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import tax.Taxable;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
    private Map<Integer, Developer> developers ;
    private Taxable taxable;

    @PostConstruct
    private void init() {
        developers = new HashMap<>();
    }

    @Autowired
    public DeveloperController(@Qualifier("developerTax") Taxable taxable) {
        this.taxable = taxable;
    }
    @GetMapping("/")
    public List<Developer> get(){
        if (developers.isEmpty()) {
            System.out.println("There are no developers");
        }
        return  developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public DeveloperResponse getById (@PathVariable int id) {
        if (!DeveloperValidation.isIdValid(id)) {
            return  new DeveloperResponse(
                    null,
                    "Id is not valid",
                    400);
        }if (!developers.containsKey(id)) {
            return new DeveloperResponse(
                    null,
                    "Developer with given id is not exist: ",
                    400);
        }
        return  new DeveloperResponse(
                developers.get(id),
                "Success",
                200);
    }

    @PostMapping("/")
    public Developer createDeveloper (@RequestBody Developer developer ) {
        if (developer.getExperience() == Developer.experience.JUNIOR) {
            developer.setSalary((int) (developer.getSalary()- taxable.getSimpleTaxRate())); ;
        } else if (developer.getExperience() == Developer.experience.MID) {
            developer.setSalary((int)(developer.getSalary()- taxable.getMiddleTaxRate()));
        } else if (developer.getExperience() == Developer.experience.SENIOR) {
            developer.setSalary((int)(developer.getSalary()-taxable.getUpperTaxRate()));
        }
        developers.put(developer.getId(),developer);
        return developer;
    }
    @PutMapping("/{id}")
    public Developer updateDeveloper(@PathVariable int id, @RequestBody Developer updatedDeveloper) {
        if (developers.containsKey(id)) {
            developers.put(id, updatedDeveloper);
            return updatedDeveloper;
        } else {
            throw new RuntimeException("Developer not found with ID: " + id);
        }
    }
    @DeleteMapping("/{id}")
    public void deleteDeveloper(@PathVariable int id) {
        developers.remove(id);
    }

}
