package com.ecommerce.service;

import com.ecommerce.exception.InsufficientProductQuantityException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ShoppingCartRepository;
import com.ecommerce.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ShoppingCartRepository shoppingCartRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    OrderRepository orderRepository;

    User user = new User("testUser", "testUser@abc.com", "Test", "User",
            "1234567890", "testAddress", User.UserType.CUSTOMER);

    @Test
    public void get_validOrder_success() {
        Order order = new Order(user, 1234);
        user.getOrders().add(order);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThat(orderService.get("testUser", order.getId())).isEqualTo(order);
    }

    @Test
    public void get_invalidUser_throwsException() {
        Order order = new Order(user, 1234);
        user.getOrders().add(order);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.get("testUser", order.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with given id: testUser");
    }

    @Test
    public void get_invalidOrder_throwsException() {
        Order order = new Order(user, 1234);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> orderService.get("testUser", "dummyOrderId"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Order not found with given id: dummyOrderId");
    }

    @Test
    public void getAll_validOrders_success() {
        Order order = new Order(user, 1234);
        user.getOrders().add(order);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThat(orderService.getAll("testUser")).isEqualTo(user.getOrders());
    }

    @Test
    public void getAll_invalidUser_throwsException() {
        Order order = new Order(user, 1234);
        user.getOrders().add(order);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.get("testUser", order.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with given id: testUser");
    }

    @Test
    public void placeOrder_validOrder_success() {
        User.CartProduct cartProduct = new User.CartProduct("PCJ100", "Jeans", "Clothing", "LP",
                3000, 2, user.getCart());
        user.getCart().getProducts().add(cartProduct);
        user.getCart().setSubTotal(6000.0);

        Product product = new Product("PCJ100", "Jeans", "Clothing", "LP",
                3000, 4, "Test description", "test specification");

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById("PCJ100")).thenReturn(Optional.of(product));

        assertThat(orderService.placeOrder("testUser").getProducts().size()).isEqualTo(1);
    }

    @Test
    public void placeOrder_invalidUser_throwsException() {
        Mockito.when(userRepository.findById("dummyUser")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.placeOrder("dummyUser"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with given id: dummyUser");
    }

    @Test
    public void placeOrder_invalidProduct_throwsException() {
        User.CartProduct cartProduct = new User.CartProduct("dummyProductId", "Jeans", "Clothing", "LP",
                3000, 2, user.getCart());
        user.getCart().getProducts().add(cartProduct);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById("dummyProductId")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.placeOrder("testUser"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with given id: dummyProductId");
    }

    @Test
    public void placeOrder_insufficientQuantity_throwsException() {
        User.CartProduct cartProduct = new User.CartProduct("PCJ100", "Jeans", "Clothing", "LP",
                3000, 2, user.getCart());
        user.getCart().getProducts().add(cartProduct);

        Product product = new Product("PCJ100", "Jeans", "Clothing", "LP",
                3000, 1, "Test description", "test specification");

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById("PCJ100")).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> orderService.placeOrder("testUser"))
                .isInstanceOf(InsufficientProductQuantityException.class)
                .hasMessage("Insufficient quantity for product with given id: PCJ100");
    }
}
