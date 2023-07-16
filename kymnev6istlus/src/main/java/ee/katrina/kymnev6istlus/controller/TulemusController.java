package ee.katrina.kymnev6istlus.controller;

import ee.katrina.kymnev6istlus.entity.Tulemus;
import ee.katrina.kymnev6istlus.repository.TulemusRepository;
import ee.katrina.kymnev6istlus.service.TulemusService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TulemusController {

    @Autowired
    TulemusRepository tulemusRepository;
    @Autowired
    TulemusService tulemusService;

    @GetMapping("tulemused")
    public List<Tulemus> getTulemused(){
        return tulemusRepository.findAll();
    }

    @PostMapping("tulemus")
    public List<Tulemus> addTulemus(@RequestBody Tulemus tulemus) {
        tulemusService.addTulemus(tulemus);
        return tulemusRepository.findAll();
    }

    @GetMapping("tulemused/{sportlase_id}")
    public List<Tulemus> getAllTulemusedForSportlane(@PathVariable Long sportlase_id) {
        return tulemusRepository.findAllBySportlaneId(sportlase_id);
    }
}
