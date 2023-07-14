package ee.katrina.salad.controller;

import ee.katrina.salad.entity.Toidukomponent;
import ee.katrina.salad.repository.ToidukomponentRepository;
import ee.katrina.salad.service.ToidukomponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToidukomponentController {

    @Autowired
    ToidukomponentService toidukomponentService;
    @Autowired
    ToidukomponentRepository toidukomponentRepository;

    @PostMapping("toidukomponent") // localhost:8080/toidukomponent?toiduaine_id=1&quantity=300
    public List<Toidukomponent> addToidukomponent(@RequestParam Long toiduaine_id, @RequestParam double quantity) {
        toidukomponentService.addToidukomponent(toiduaine_id, quantity);
        return toidukomponentRepository.findAll();
    }

    @GetMapping("toidukomponendid") // localhost:8080/toidukomponendid
    public List<Toidukomponent> viewToidukomponendid() {
        return toidukomponentRepository.findAll();
    }

    @GetMapping("toidukomponent/getFatPercentage/{id}") // localhost:8080/toidukomponent/getFatPercentage/1
    public double getFatPercentage(@PathVariable Long id) {
        return toidukomponentService.calculateFatQuantity(id);
    }

    @GetMapping("toidukomponent/{nimetus}") // localhost:8080/toidukomponent/banaan
    public List<Toidukomponent> findToidukomponentByNimetus(@PathVariable String nimetus) {
        return toidukomponentRepository.findToidukomponentsByToiduaineNimetus(nimetus);
    }
}
