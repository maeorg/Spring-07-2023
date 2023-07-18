package ee.katrina.webshop.controller;

import ee.katrina.webshop.dto.everypay.EverypayData;
import ee.katrina.webshop.dto.everypay.EverypayResponse;
import ee.katrina.webshop.entity.Order;
import ee.katrina.webshop.entity.OrderRow;
import ee.katrina.webshop.repository.OrderRepository;
import ee.katrina.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class OrderController {

    // KODUS: Kõikide võtmine, Lisamine, Kustutamine, Ühe võtmine, Muutmine
    //                          korraga tuleb lisada OrderRow

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @PostMapping("orders")
    public List<Order> addOrder(@RequestBody Order order) {
        double totalSum = 0;
        for (OrderRow o : order.getOrderRow()) {
            totalSum += o.getQuantity() * productRepository.findById(o.getProduct().getId()).get().getPrice();
        }
        order.setTotalSum(totalSum);
        orderRepository.save(order);
        return orderRepository.findAll();
    }

    @DeleteMapping("orders/{id}")
    public List<Order> deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return orderRepository.findAll();
    }

    @GetMapping("orders/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderRepository.findById(id).get();
    }

    @PutMapping("orders/{id}")
    public List<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        if (orderRepository.existsById(id)) {
            order.setId(orderRepository.findById(id).get().getId());
            orderRepository.save(order);
        }
        return orderRepository.findAll();
    }

    @GetMapping("payment/{sum}")
    public String pay(@PathVariable double sum) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://igw-demo.every-pay.com/api/v4/payments/oneoff";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,
                "Basic ZTM2ZWI0MGY1ZWM4N2ZhMjo3YjkxYTNiOWUxYjc0NTI0YzJlOWZjMjgyZjhhYzhjZA==");
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        EverypayData body = new EverypayData();
        body.setApi_username("e36eb40f5ec87fa2");
        body.setAccount_name("EUR3D1");
        body.setAmount(sum);
        body.setOrder_reference("ka75" + String.valueOf(Math.random() * 9999999)); // TODO järgmine kord
        body.setNonce("uf0d0308632nvnv23" + ZonedDateTime.now() + Math.random());
        body.setTimestamp(ZonedDateTime.now().toString());
        body.setCustomer_url("https://maksmine.web.app/makse");

        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                EverypayResponse.class);
        return response.getBody().getPayment_link();
    }
}
