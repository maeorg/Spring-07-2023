package ee.katrina.webshop.controller;

import ee.katrina.webshop.dto.phone.Phone;
import ee.katrina.webshop.dto.phone.PhoneResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PhoneController {

    @GetMapping("phones") // example: localhost:8080/phones?discountMin=12.5&ratingAtleast=4.5&priceMax=800
    public List<Phone> getPhones(@RequestParam(required = false) String title,
                                 @RequestParam(required = false) String brand,
                                 @RequestParam(required = false, defaultValue = "0") Integer priceMin,
                                 @RequestParam(required = false, defaultValue = "2147483647") Integer priceMax,
                                 @RequestParam(required = false, defaultValue = "0") Double discountMin,
                                 @RequestParam(required = false, defaultValue = "1.7976931348623157E308") Double discountMax,
                                 @RequestParam(required = false) String category,
                                 @RequestParam(required = false, defaultValue = "0") Double ratingAtleast) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PhoneResponse> response = restTemplate.exchange("https://dummyjson.com/products",
                HttpMethod.GET, null, PhoneResponse.class);

        List<Phone> phoneList = new ArrayList<>();

        for (Phone phone : response.getBody().getProducts()) {
            if (!phoneList.contains(phone)) {
                if ((title == null || phone.getTitle().equalsIgnoreCase(title)) && (brand == null || phone.getBrand().equalsIgnoreCase(brand)) &&
                        (category == null || phone.getCategory().equalsIgnoreCase(category)) && phone.getPrice() >= priceMin &&
                        phone.getPrice() <= priceMax && phone.getRating() >= ratingAtleast &&
                        phone.getDiscountPercentage() >= discountMin && phone.getDiscountPercentage() <= discountMax) {
                    phoneList.add(phone);
                }
            }
        }
        return phoneList;
    }
}
