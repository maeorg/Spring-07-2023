package ee.katrina.salad.service;

import ee.katrina.salad.entity.Toiduaine;
import ee.katrina.salad.repository.ToiduaineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToiduaineService {

    @Autowired
    ToiduaineRepository toiduaineRepository;

    // Protsent kokku ei saa ületada 100
    public boolean checkPercentageSum(Toiduaine toiduaine) {
        return !(toiduaine.getValguProtsent() + toiduaine.getRasvaProtsent() + toiduaine.getSusivesikuteProtsent() > 100);
    }

    public String addToiduaine(Toiduaine toiduaine) {
        if (!checkPercentageSum(toiduaine)) return "Viga! Protsent on üle 100.";
        toiduaineRepository.save(toiduaine);
        return "Toiduaine edukalt lisatud!";
    }
}
