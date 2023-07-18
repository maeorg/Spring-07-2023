package ee.katrina.webshop.controller;

import ee.katrina.webshop.dto.nordpool.Nordpool;
import ee.katrina.webshop.dto.nordpool.TimestampPrice;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NordpoolController {

    @GetMapping("nordpool") // localhost:8080/nordpool
    public Nordpool getAll() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Nordpool> response = restTemplate.exchange("https://dashboard.elering.ee/api/nps/price",
                HttpMethod.GET, null, Nordpool.class);
        return response.getBody();
    }

    @GetMapping("nordpool/{country}") // ee, lt, lv, fi - localhost:8080/nordpool/Fi
    public List<TimestampPrice> getByCountry(@PathVariable String country) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Nordpool> response = restTemplate.exchange("https://dashboard.elering.ee/api/nps/price",
                HttpMethod.GET, null, Nordpool.class);
        // KODUS: Tehke ühe riigi kaupa tagastamine
        return switch(country.toLowerCase()) {
            case "ee" -> response.getBody().getData().getEe();
            case "lv" -> response.getBody().getData().getLv();
            case "lt" -> response.getBody().getData().getLt();
            case "fi" -> response.getBody().getData().getFi();
            default -> new ArrayList<>();
        };
    }

    // This method gets NordPool day-ahead prices for selected time period (maximum allowed time period per request is one year)
    @GetMapping("nordpool/getTimeperiod") // localhost:8080/nordpool/getTimeperiod?startTime=2023-05-20&endTime=2023-05-24
    public Nordpool getNordpoolPrices(@RequestParam String startTime,
                                      @RequestParam String endTime) {
        // https://dashboard.elering.ee/api/nps/price?start=2023-05-20T12%3A59%3A59.999Z&end=2023-05-24T20%3A59%3A59.999Z
        // https://dashboard.elering.ee/api/nps/price?start=2023-05-20T12:59:59.999Z&end=2023-05-24T20:59:59.999Z
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://dashboard.elering.ee/api/nps/price?start=" + startTime + "T00:00Z&end=" + endTime + "T23:59:59.999Z";
        ResponseEntity<Nordpool> response = restTemplate.exchange(url, HttpMethod.GET, null, Nordpool.class);
        return response.getBody();
    }

}
