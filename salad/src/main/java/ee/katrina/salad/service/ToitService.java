package ee.katrina.salad.service;

import ee.katrina.salad.entity.Toidukomponent;
import ee.katrina.salad.entity.Toit;
import ee.katrina.salad.repository.ToidukomponentRepository;
import ee.katrina.salad.repository.ToitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToitService {

    @Autowired
    ToitRepository toitRepository;
    @Autowired
    ToidukomponentRepository toidukomponentRepository;
    @Autowired
    ToidukomponentService toidukomponentService;

    public String addToidukomponentToToit(Long toidu_id, Long toidukomponendi_id) {
        Toidukomponent toidukomponent = toidukomponentRepository.findById(toidukomponendi_id).get();
        Toit toit = toitRepository.findById(toidu_id).get();
        List<Toidukomponent> toidukomponentList = toit.getToidukomponentList();
        toidukomponentList.add(toidukomponent);
        toit.setToidukomponentList(toidukomponentList);
        toitRepository.save(toit);
        return "Toidukomponent toitu edukalt lisatud!";
    }

    public double calculateFatPercentage(Long id) {
        Toit toit = toitRepository.findById(id).get();
        double fatQuantity = 0.0;
        double totalQuantity = 0.0;
        List<Toidukomponent> toidukomponentList = toit.getToidukomponentList();
        for (Toidukomponent t : toidukomponentList) {
            fatQuantity += toidukomponentService.calculateFatQuantity(t.getId());
            totalQuantity += t.getQuantity();
        }
        return fatQuantity / totalQuantity * 100;
    }
}
