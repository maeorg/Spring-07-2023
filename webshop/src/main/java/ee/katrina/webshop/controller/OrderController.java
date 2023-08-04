package ee.katrina.webshop.controller;

import ee.katrina.webshop.entity.Order;
import ee.katrina.webshop.entity.OrderRow;
import ee.katrina.webshop.exception.NotEnoughInStockException;
import ee.katrina.webshop.repository.OrderRepository;
import ee.katrina.webshop.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class OrderController {

    // KODUS: Kõikide võtmine, Lisamine, Kustutamine, Ühe võtmine, Muutmine
    //                          korraga tuleb lisada OrderRow

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @GetMapping("orders")
    public ResponseEntity<List<Order>> getOrders() {

        return ResponseEntity.ok(orderRepository.findAll());
//        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

//    @PostMapping("orders")
//    public ResponseEntity<List<Order>> addOrder(@RequestBody Order order) {
//        double totalSum = 0;
//        for (OrderRow o : order.getOrderRow()) {
//            totalSum += o.getQuantity() * productRepository.findById(o.getProduct().getId()).get().getPrice();
//        }
//        order.setCreationDate(new Date());
//        order.setTotalSum(totalSum);
//        orderRepository.save(order);
////        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.findAll());
//        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.CREATED);
//    }

    @PostMapping("orders/{personId}")
    public ResponseEntity<String> addOrder(@RequestBody List<OrderRow> orderRows, @PathVariable Long personId) throws NotEnoughInStockException, ExecutionException {
        // hiljem võtame tokeni küljest sisu
        double totalSum = orderService.getTotalSum(orderRows);
        Long id = orderService.saveOrderToDb(totalSum, orderRows, personId);
        String paymentUrl = orderService.makePayment(totalSum, id);
        return new ResponseEntity<>(paymentUrl, HttpStatus.CREATED);
    }

    @DeleteMapping("orders/{id}")
    public ResponseEntity<List<Order>> deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return new ResponseEntity<>(orderRepository.findById(id).get(), HttpStatus.OK);
    }

    @PutMapping("orders/{id}")
    public ResponseEntity<List<Order>> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        if (orderRepository.existsById(id)) {
            order.setId(orderRepository.findById(id).get().getId());
            orderRepository.save(order);
        }
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("check-payment/{paymentReference}")
    public ResponseEntity<Boolean> checkPayment(@PathVariable("paymentReference") String paymentReference) {
        return new ResponseEntity<>(orderService.checkPayment(paymentReference), HttpStatus.OK);
    }

}
