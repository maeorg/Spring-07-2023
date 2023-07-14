package ee.katrina.salad.controller;

import ee.katrina.salad.entity.Toiduaine;
import ee.katrina.salad.repository.ToiduaineRepository;
import ee.katrina.salad.service.ToiduaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToiduaineController {

    @Autowired
    ToiduaineRepository toiduaineRepository;
    @Autowired
    ToiduaineService toiduaineService;

    @PostMapping("toiduaine")
    public List<Toiduaine> addToiduaine(@RequestBody Toiduaine toiduaine) {
        toiduaineService.addToiduaine(toiduaine);
        return toiduaineRepository.findAll();
    }

    @GetMapping("toiduained")
    public List<Toiduaine> viewToiduained() {
        return toiduaineRepository.findAll();
    }

    @DeleteMapping("toiduaine/{id}")
    public List<Toiduaine> removeToiduaine(@PathVariable Long id) {
        toiduaineRepository.deleteById(id);
        return toiduaineRepository.findAll();
    }

    @GetMapping("toiduaine/{nimetus}") // localhost:8080/toiduaine/banaan
    public Toiduaine findToiduaineByNimetus(@PathVariable String nimetus) {
        return toiduaineRepository.findToiduaineByNimetus(nimetus);
    }
}
