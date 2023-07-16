package ee.katrina.kymnev6istlus.service;

import ee.katrina.kymnev6istlus.entity.Ala;
import ee.katrina.kymnev6istlus.repository.AlaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlaService {

    @Autowired
    AlaRepository alaRepository;

    public void createAlad() {
        List<String> alad = new ArrayList<>(
                List.of("100 m",
                        "Long jump",
                        "Shot put",
                        "High jump",
                        "400 m",
                        "110 m hurdles",
                        "Discus throw",
                        "Pole vault",
                        "Javelin throw",
                        "1500 m"
                        ));
        for (String e : alad) {
            Ala ala = new Ala();
            ala.setNimi(e);
            alaRepository.save(ala);
        }
    }
}
