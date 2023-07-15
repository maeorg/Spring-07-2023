package ee.katrina.webshop.controller;

import ee.katrina.webshop.dto.Phone;
import ee.katrina.webshop.dto.Photo;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class PhoneController {

    @GetMapping("phones")
    public List<Phone> getPhones() {
        // TO DO
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Phone> response = restTemplate.exchange("https://dummyjson.com/products",
//                HttpMethod.GET, null, Phone.class);
//        System.out.println(response);
//        return Arrays.asList(response.getBody());
    }
}
