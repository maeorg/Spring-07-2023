package ee.katrina.webshop.service;

import ee.katrina.webshop.dto.everypay.EverypayData;
import ee.katrina.webshop.dto.everypay.EverypayResponse;
import ee.katrina.webshop.entity.Order;
import ee.katrina.webshop.entity.OrderRow;
import ee.katrina.webshop.entity.Person;
import ee.katrina.webshop.entity.Product;
import ee.katrina.webshop.repository.OrderRepository;
import ee.katrina.webshop.repository.PersonRepository;
import ee.katrina.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;

    public Long saveOrderToDb(double totalSum, List<OrderRow> orderRows, Long personId) {
        Person person = personRepository.findById(personId).get();
        Order order = new Order();
        order.setPaymentState("Initial");
        order.setPerson(person);
        order.setOrderRow(orderRows);
        order.setCreationDate(new Date());
        order.setTotalSum(totalSum);
        Order newOrder = orderRepository.save(order);
        return newOrder.getId();
    }

    public double getTotalSum(List<OrderRow> orderRows) throws Exception {
        double totalSum = 0;
        for (OrderRow o : orderRows) {
            Product product = productRepository.findById(o.getProduct().getId()).get();
            if (product.getStock() < o.getQuantity()) {
                // Enda exceptioni peaks pigem tegema, järgmine kord teeme
                throw new Exception("Not enough in stock: " + product.getName() + ", id: " + product.getId());
            }
            totalSum += o.getQuantity() * product.getPrice();
        }
        return totalSum;
    }

    public String makePayment(double totalSum, Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://igw-demo.every-pay.com/api/v4/payments/oneoff";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,
                "Basic ZTM2ZWI0MGY1ZWM4N2ZhMjo3YjkxYTNiOWUxYjc0NTI0YzJlOWZjMjgyZjhhYzhjZA==");
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        EverypayData body = new EverypayData();
        body.setApi_username("e36eb40f5ec87fa2");
        body.setAccount_name("EUR3D1");
        body.setAmount(totalSum);
        body.setOrder_reference(id.toString());
        body.setNonce("uf0d0308632nvnv23" + ZonedDateTime.now() + Math.random());
        body.setTimestamp(ZonedDateTime.now().toString());
        body.setCustomer_url("https://maksmine.web.app/makse");

        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                EverypayResponse.class);
        return response.getBody().getPayment_link();
    }
}
