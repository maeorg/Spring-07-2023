package ee.katrina.webshop.controller;

import ee.katrina.webshop.dto.Nordpool;
import ee.katrina.webshop.dto.Photo;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class PhotoController {

    @GetMapping("photos")
    public List<Photo> getPhotos() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Photo[]> response = restTemplate.exchange("https://jsonplaceholder.typicode.com/photos",
                HttpMethod.GET, null, Photo[].class);
        return Arrays.asList(response.getBody());
    }
}
