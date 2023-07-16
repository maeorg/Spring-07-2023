package ee.katrina.kymnev6istlus.service;

import ee.katrina.kymnev6istlus.entity.Tulemus;
import ee.katrina.kymnev6istlus.repository.TulemusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TulemusService {

    @Autowired
    TulemusRepository tulemusRepository;

    public void addTulemus(Tulemus tulemus) {
        tulemus.setPunktid(calculatePoints(tulemus));
        tulemusRepository.save(tulemus);
    }

    private int calculatePoints(Tulemus tulemus) {
        String ala_id = tulemus.getAla().getId().toString();
        int punktid;
        switch (ala_id) {
            default -> punktid = 0;
            // 100 m: tulemus sekundites
            case "1" -> punktid = (int) Math.floor(Math.pow(18 - tulemus.getTulemus(), 1.81) * 25.4347);
            // Long jump: tulemus sentimeetrites
            case "2" -> punktid = (int) Math.floor(Math.pow(tulemus.getTulemus() - 220, 1.4) * 0.14354);
            // Shot put: tulemus meetrites
            case "3" -> punktid = (int) Math.floor(Math.pow(tulemus.getTulemus() - 1.5, 1.05) * 51.39);
            // High jump: tulemus sentimeetrites
            case "4" -> punktid = (int) Math.floor(Math.pow(tulemus.getTulemus() - 75, 1.42) * 0.8465);
            // 400 m: tulemus sekundites
            case "5" -> punktid = (int) Math.floor(Math.pow(82 - tulemus.getTulemus(), 1.81) * 1.53775);
            // 110 m hurdles: tulemus sekundites
            case "6" -> punktid = (int) Math.floor(Math.pow(28.5 - tulemus.getTulemus(), 1.92) * 5.74352);
            // Discus throw: tulemus meetrites
            case "7" -> punktid = (int) Math.floor(Math.pow(tulemus.getTulemus() - 4, 1.1) * 12.91);
            // Pole vault: tulemus sentimeetrites
            case "8" -> punktid = (int) Math.floor(Math.pow(tulemus.getTulemus() - 100, 1.35) * 0.2797);
            // Javelin throw: tulemus meetrites
            case "9" -> punktid = (int) Math.floor(Math.pow(tulemus.getTulemus() - 7, 1.08) * 10.14);
            // 1500 m: tulemus sekundites
            case "10" -> punktid = (int) Math.floor(Math.pow(480 - tulemus.getTulemus(), 1.85) * 0.03768);
        }
        return punktid;
    }
}
