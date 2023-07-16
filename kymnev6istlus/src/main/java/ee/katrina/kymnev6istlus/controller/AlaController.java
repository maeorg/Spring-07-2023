package ee.katrina.kymnev6istlus.controller;

import ee.katrina.kymnev6istlus.entity.Ala;
import ee.katrina.kymnev6istlus.repository.AlaRepository;
import ee.katrina.kymnev6istlus.service.AlaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlaController {

    @Autowired
    AlaRepository alaRepository;
    @Autowired
    AlaService alaService;

    @GetMapping("createAlad")
    public List<Ala> createAlad() {
        alaService.createAlad();
        return alaRepository.findAll();
    }

}
