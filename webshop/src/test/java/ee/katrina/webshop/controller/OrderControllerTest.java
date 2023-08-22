package ee.katrina.webshop.controller;

import ee.katrina.webshop.entity.Order;
import ee.katrina.webshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mvc;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderController orderController;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void getOrders() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/orders")).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void addOrderThrowsBadRequest_ifNoBody() throws Exception {
        Order order = new Order();
        MockHttpServletResponse response = mvc.perform(post("/orders")).andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void deleteOrder() {
    }

    @Test
    void getOrder() {
    }

    @Test
    void updateOrder() {
    }

    @Test
    void checkPayment() {
    }
}