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
        tulemusRepository.save(tulemus);
    }
}
