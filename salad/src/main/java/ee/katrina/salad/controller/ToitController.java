package ee.katrina.salad.controller;

import ee.katrina.salad.entity.Toit;
import ee.katrina.salad.repository.ToitRepository;
import ee.katrina.salad.service.ToitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ToitController {

    @Autowired
    ToitRepository toitRepository;

    @Autowired
    ToitService toitService;

    @PostMapping("toit/newToit") // localhost:8080/toit/newToit?nimetus=kartulisalat
    public List<Toit> createNewToit(@RequestParam String nimetus) {
        Toit toit = new Toit();
        toit.setNimetus(nimetus);
        toitRepository.save(toit);
        return toitRepository.findAll();
    }

    @PostMapping("toit") // localhost:8080/toit?toidu_id=1&toidukomponendi_id=1
    public List<Toit> addToidukomponentToToit(@RequestParam Long toidu_id, @RequestParam Long toidukomponendi_id) {
        toitService.addToidukomponentToToit(toidu_id, toidukomponendi_id);
        return toitRepository.findAll();
    }

    @GetMapping("toit/{nimetus}") // localhost:8080/toit/kartulisalat
    public Toit findToitByNimetus(@PathVariable String nimetus) {
        return toitRepository.findByNimetus(nimetus);
    }

    @GetMapping("toit") // localhost:8080/toit?min=10&max=90
    public List<Toit> getAllToitsInBetweenFatPercentages(@RequestParam double min, @RequestParam double max) {
        List<Toit> toitList = toitRepository.findAll();
        List<Toit> resultList = new ArrayList<>();
        for (Toit t : toitList) {
            double fatPercentage = toitService.calculateFatPercentage(t.getId());
            if (fatPercentage >= min && fatPercentage <= max) {
                resultList.add(t);
            }
        }
        return resultList;
    }

}
