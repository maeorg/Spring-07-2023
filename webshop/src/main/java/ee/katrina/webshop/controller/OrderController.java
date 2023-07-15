package ee.katrina.webshop.controller;

import ee.katrina.webshop.entity.Order;
import ee.katrina.webshop.entity.Person;
import ee.katrina.webshop.repository.OrderRepository;
import ee.katrina.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    // KODUS: Kõikide võtmine, Lisamine, Kustutamine, Ühe võtmine, Muutmine
    //                          korraga tuleb lisada OrderRow

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @PostMapping("orders")
    public List<Order> addOrder(@RequestBody Order order) {
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
}
