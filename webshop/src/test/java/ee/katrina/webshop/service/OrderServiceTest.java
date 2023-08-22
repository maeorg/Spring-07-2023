package ee.katrina.webshop.service;

import ee.katrina.webshop.cache.ProductCache;
import ee.katrina.webshop.dto.everypay.EverypayResponse;
import ee.katrina.webshop.dto.everypay.PaymentCheck;
import ee.katrina.webshop.entity.Order;
import ee.katrina.webshop.entity.OrderRow;
import ee.katrina.webshop.entity.Person;
import ee.katrina.webshop.entity.Product;
import ee.katrina.webshop.exception.NotEnoughInStockException;
import ee.katrina.webshop.repository.OrderRepository;
import ee.katrina.webshop.repository.PersonRepository;
import ee.katrina.webshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    PersonRepository personRepository;

    @Mock
    ProductCache productCache;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save order to database")
    void saveOrderToDb() {
        Person person = new Person();
        when(personRepository.findById(any())).thenReturn(Optional.of(person));
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.save(any())).thenReturn(order);

        Long orderId = orderService.saveOrderToDb(100, Collections.emptyList(), 1L);

        assertEquals(1L, orderId);
    }

    @Test
    @DisplayName("Save order to database throws exception if person is empty")
    void saveOrderToDbThrowsExceptionIfPersonEmpty() {
        assertThrows(NoSuchElementException.class, () ->
                orderService.saveOrderToDb(100, Collections.emptyList(), 1L));
    }

    @Test
    @DisplayName("Get total sum")
    void getTotalSum() throws NotEnoughInStockException, ExecutionException {
        Product product = new Product();
        product.setPrice(15.42);
        product.setId(10L);
        product.setStock(10);

        OrderRow orderRow = new OrderRow();
        orderRow.setQuantity(2);
        orderRow.setProduct(product);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(productCache.getProduct(10L)).thenReturn(product);

        double totalSum = orderService.getTotalSum(Collections.singletonList(orderRow));
        assertEquals(30.84, totalSum, 0.01);
    }

    @Test
    @DisplayName("Get total sum throws exception if product not found")
    void getTotalSumThrowsExceptionIfProductNotFound() {
        Product product = new Product();
        OrderRow orderRow = new OrderRow();
        orderRow.setProduct(product);
        assertThrows(NoSuchElementException.class, () ->
                orderService.getTotalSum(Collections.singletonList(orderRow)));
    }

    @Test
    @DisplayName("Get total sum throws exception if not enough in stock")
    void getTotalSumThrowsException_ifNotEnoughInStock() throws ExecutionException {
        Product product = new Product();
        product.setPrice(15.42);
        product.setId(10L);
        product.setStock(10);

        OrderRow orderRow = new OrderRow();
        orderRow.setQuantity(15);
        orderRow.setProduct(product);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(productCache.getProduct(10L)).thenReturn(product);

        assertThrows(NotEnoughInStockException.class, () ->
                orderService.getTotalSum(Collections.singletonList(orderRow)));
    }

    @Test
    @DisplayName("Decrease product stock")
    void decreaseStock() throws NotEnoughInStockException, ExecutionException {
        Product product = new Product();
        product.setStock(10);
        OrderRow orderRow = new OrderRow();
        orderRow.setProduct(product);
        orderRow.setQuantity(3);
        orderService.decreaseStock(orderRow, product);
        assertEquals( 7, product.getStock());
    }

    @Test
    void makePayment() {
        EverypayResponse response = new EverypayResponse();
        response.setPayment_link("LINK_THAT_EVERYPAY_RETURNS");

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(EverypayResponse.class)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        String everypayLink = orderService.makePayment(100, 1L);

        assertEquals("LINK_THAT_EVERYPAY_RETURNS", everypayLink);
    }

    @Test
    @DisplayName("Check that is true if payment is settled")
    void checkPaymentTrueIfSettled() {
        PaymentCheck response = new PaymentCheck();
        response.setPayment_state("settled");
        response.setOrder_reference("1");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(new Order()));

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(PaymentCheck.class)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        boolean isPaid = orderService.checkPayment("PAYMENT_REFERENCE");

        assertTrue(isPaid);
    }

    @Test
    @DisplayName("Check that is false if payment is failed")
    void checkPaymentFalse_IfFailed() {
        PaymentCheck response = new PaymentCheck();
        response.setPayment_state("failed");
        response.setOrder_reference("1");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(new Order()));

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(PaymentCheck.class)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        boolean isPaid = orderService.checkPayment("PAYMENT_REFERENCE");

        assertFalse(isPaid);
    }
}