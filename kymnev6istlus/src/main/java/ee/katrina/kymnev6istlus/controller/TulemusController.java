package ee.katrina.kymnev6istlus.controller;

import ee.katrina.kymnev6istlus.entity.Tulemus;
import ee.katrina.kymnev6istlus.repository.TulemusRepository;
import ee.katrina.kymnev6istlus.service.TulemusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TulemusController {

    @Autowired
    TulemusRepository tulemusRepository;
    @Autowired
    TulemusService tulemusService;

    // -Järgmiseks tee 10 POST päringut Controlleris, mis võtavad vastu iga kergejõustiku ala tulemuse
    //      - tegin ühe päringuna, kus tuleb request body-s sisse anda Tulemus - sportlase id, ala id ja
    //      tulemus (vastavalt alale kas sekundites, sentimeetrites või meetrites)
    // Päring arvutab tulemuse punktideks ja lisab selle Tulemus entity-le ning salvestab andmebaasi
    @PostMapping("tulemus")
    public List<Tulemus> addTulemus(@RequestBody Tulemus tulemus) {
        tulemusService.addTulemus(tulemus);
        return tulemusRepository.findAll();
    }

    @GetMapping("tulemused")
    public List<Tulemus> getTulemused(){
        return tulemusRepository.findAll();
    }

    @GetMapping("tulemused/{sportlase_id}")
    public List<Tulemus> getAllTulemusedForSportlane(@PathVariable Long sportlase_id) {
        return tulemusRepository.findAllBySportlaneId(sportlase_id);
    }

    // -Võimalda saada sportlase kogusumma selleks hetkeks (kui on sisestatud 6 ala, siis 6 ala kogusumma)
    @GetMapping("tulemused/punktideKogusumma/{sportlase_id}")
    public int getTotalSumPunktidBySportlane(@PathVariable Long sportlase_id) {
        List<Tulemus> tulemused = tulemusRepository.findAllBySportlaneId(sportlase_id);
        int sum = 0;
        for (Tulemus t : tulemused) {
            sum += t.getPunktid();
        }
        return sum;
    }
}
