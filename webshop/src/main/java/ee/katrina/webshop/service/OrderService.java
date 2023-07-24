package ee.katrina.webshop.service;

import ee.katrina.webshop.dto.everypay.EverypayData;
import ee.katrina.webshop.dto.everypay.EverypayResponse;
import ee.katrina.webshop.entity.Order;
import ee.katrina.webshop.entity.OrderRow;
import ee.katrina.webshop.entity.Person;
import ee.katrina.webshop.entity.Product;
import ee.katrina.webshop.exception.NotEnoughInStockException;
import ee.katrina.webshop.repository.OrderRepository;
import ee.katrina.webshop.repository.PersonRepository;
import ee.katrina.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
//@AllArgsConstructor
public class OrderService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${everypay.url}")
    String url;

    @Value("${everypay.token}")
    String token;

    @Value("${everypay.username}")
    String username;

    @Value("${everypay.customer-url}")
    String customerUrl;

    @Value("${everypay.account-name}")
    String accountName;

    public Long saveOrderToDb(double totalSum, List<OrderRow> orderRows, Long personId) {
        if (personRepository.findById(personId).isEmpty()) {
            throw new NoSuchElementException("Person not found");
        }
        Person person = personRepository.findById(personId).get();

        Order order = Order.builder()
                .paymentState("Inital")
                .person(person)
                .orderRow(orderRows)
                .creationDate(new Date())
                .totalSum(totalSum)
                .build();

//        Order order = new Order();
//        order.setPaymentState("Initial");
//        order.setPerson(person);
//        order.setOrderRow(orderRows);
//        order.setCreationDate(new Date());
//        order.setTotalSum(totalSum);

        Order newOrder = orderRepository.save(order);
        return newOrder.getId();
    }

    public double getTotalSum(List<OrderRow> orderRows) throws NotEnoughInStockException {
        double totalSum = 0;
        for (OrderRow o : orderRows) {
            if (productRepository.findById(o.getProduct().getId()).isEmpty()) {
                throw new NoSuchElementException("Product not found");
            }
            Product product = productRepository.findById(o.getProduct().getId()).get();
            if (product.getStock() < o.getQuantity()) {
                throw new NotEnoughInStockException("Not enough in stock: " + product.getName() + ", id: " + product.getId());
            }
            totalSum += o.getQuantity() * product.getPrice();
        }
        return totalSum;
    }

    public String makePayment(double totalSum, Long id) {
//        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + token);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        EverypayData body = new EverypayData();
        body.setApi_username(username);
        body.setAccount_name(accountName);
        body.setAmount(totalSum);
        body.setOrder_reference(id.toString());
        body.setNonce("uf0d0308632nvnv23" + ZonedDateTime.now() + Math.random());
        body.setTimestamp(ZonedDateTime.now().toString());
        body.setCustomer_url(customerUrl);

        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                EverypayResponse.class);
        return response.getBody().getPayment_link();
    }
}
