package ee.katrina.salad.service;

import ee.katrina.salad.entity.Toiduaine;
import ee.katrina.salad.entity.Toidukomponent;
import ee.katrina.salad.repository.ToiduaineRepository;
import ee.katrina.salad.repository.ToidukomponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToidukomponentService {

    @Autowired
    ToidukomponentRepository toidukomponentRepository;
    @Autowired
    ToiduaineRepository toiduaineRepository;

    public String addToidukomponent(Long toiduaine_id, double quantity) {
        Toiduaine toiduaine = toiduaineRepository.findById(toiduaine_id).get();
        Toidukomponent toidukomponent = new Toidukomponent();
        toidukomponent.setQuantity(quantity);
        toidukomponent.setToiduaine(toiduaine);
        toidukomponentRepository.save(toidukomponent);
        return "Toidukomponent edukalt lisatud!";
    }

    public double calculateFatQuantity(Long id) {
        Toidukomponent toidukomponent = toidukomponentRepository.findById(id).get();
        return toidukomponent.getQuantity() / 100 * toidukomponent.getToiduaine().getRasvaProtsent();
    }
}
