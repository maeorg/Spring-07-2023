package ee.katrina.kymnev6istlus.controller;

import ee.katrina.kymnev6istlus.entity.Sportlane;
import ee.katrina.kymnev6istlus.repository.SportlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SportlaneController {

    @Autowired
    SportlaneRepository sportlaneRepository;

    @GetMapping("sportlased")
    public List<Sportlane> getSportlased() {
        return sportlaneRepository.findAll();
    }

    // -Võimalda salvestada sportlane – nimi, riik, vanus.
    @PostMapping("sportlane")
    public List<Sportlane> addSportlane(@RequestBody Sportlane sportlane) {
        sportlaneRepository.save(sportlane);
        return sportlaneRepository.findAll();
    }
}
