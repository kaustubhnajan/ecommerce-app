package com.ecommerce.service;

import com.ecommerce.dto.ProductIdQuantityDTO;
import com.ecommerce.exception.InsufficientProductQuantityException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ShoppingCartRepository shoppingCartRepository;

    @MockBean
    ProductRepository productRepository;

    User user = new User("testUser", "testUser@abc.com", "Test", "User",
            "1234567890", "testAddress", User.UserType.CUSTOMER);

    @Test
    public void get_validOrder_success() {
        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThat(shoppingCartService.get("testUser")).isEqualTo(user.getCart());
    }

    @Test
    public void get_invalidUser_throwsException() {
        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> shoppingCartService.get("testUser"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with given id: testUser");
    }

    @Test
    public void addProducts_validOrder_success() {
        Product product = new Product("PCJ100", "Jeans", "Clothing", "LP",
                3000, 4, "Test description", "test specification");

        ProductIdQuantityDTO productIdQuantityDTO = new ProductIdQuantityDTO("PCJ100", 2);
        List<ProductIdQuantityDTO> productIdQuantityDTOs = new ArrayList<>();
        productIdQuantityDTOs.add(productIdQuantityDTO);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById("PCJ100")).thenReturn(Optional.of(product));

        assertThat(shoppingCartService.addProducts("testUser", productIdQuantityDTOs).getSubTotal()).isEqualTo(6000.0);
        assertThat(shoppingCartService.addProducts("testUser", productIdQuantityDTOs).getProducts().size()).isEqualTo(1);
    }

    @Test
    public void addProducts_invalidUser_throwsException() {
        ProductIdQuantityDTO productIdQuantityDTO = new ProductIdQuantityDTO("PCJ100", 2);
        List<ProductIdQuantityDTO> productIdQuantityDTOs = new ArrayList<>();
        productIdQuantityDTOs.add(productIdQuantityDTO);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> shoppingCartService.addProducts("testUser", productIdQuantityDTOs))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with given id: testUser");
    }

    @Test
    public void addProducts_invalidProduct_throwsException() {
        ProductIdQuantityDTO productIdQuantityDTO = new ProductIdQuantityDTO("dummyProductId", 2);
        List<ProductIdQuantityDTO> productIdQuantityDTOs = new ArrayList<>();
        productIdQuantityDTOs.add(productIdQuantityDTO);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById("dummyProductId")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> shoppingCartService.addProducts("testUser", productIdQuantityDTOs))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with given id: dummyProductId");
    }

    @Test
    public void addProducts_insufficientQuantity_throwsException() {
        Product product = new Product("PCJ100", "Jeans", "Clothing", "LP",
                3000, 1, "Test description", "test specification");

        ProductIdQuantityDTO productIdQuantityDTO = new ProductIdQuantityDTO("PCJ100", 2);
        List<ProductIdQuantityDTO> productIdQuantityDTOs = new ArrayList<>();
        productIdQuantityDTOs.add(productIdQuantityDTO);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById("PCJ100")).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> shoppingCartService.addProducts("testUser", productIdQuantityDTOs))
                .isInstanceOf(InsufficientProductQuantityException.class)
                .hasMessage("Insufficient quantity for product with given id: PCJ100");
    }

    @Test
    public void removeProducts_validOrder_success() {
        User.CartProduct cartProduct = new User.CartProduct("PCJ100", "Jeans", "Clothing", "LP",
                3000, 2, user.getCart());
        user.getCart().getProducts().add(cartProduct);

        ProductIdQuantityDTO productIdQuantityDTO = new ProductIdQuantityDTO("PCJ100", 1);
        List<ProductIdQuantityDTO> productIdQuantityDTOs = new ArrayList<>();
        productIdQuantityDTOs.add(productIdQuantityDTO);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThat(shoppingCartService.removeProducts("testUser", productIdQuantityDTOs).getProducts().get(0).getQuantity()).isEqualTo(1);
    }

    @Test
    public void removeProducts_invalidUser_throwsException() {
        ProductIdQuantityDTO productIdQuantityDTO = new ProductIdQuantityDTO("PCJ100", 2);
        List<ProductIdQuantityDTO> productIdQuantityDTOs = new ArrayList<>();
        productIdQuantityDTOs.add(productIdQuantityDTO);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> shoppingCartService.removeProducts("testUser", productIdQuantityDTOs))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with given id: testUser");
    }

    @Test
    public void removeProducts_invalidProduct_throwsException() {
        User.CartProduct cartProduct = new User.CartProduct("PCJ100", "Jeans", "Clothing", "LP",
                3000, 2, user.getCart());
        user.getCart().getProducts().add(cartProduct);

        ProductIdQuantityDTO productIdQuantityDTO = new ProductIdQuantityDTO("dummyProductId", 2);
        List<ProductIdQuantityDTO> productIdQuantityDTOs = new ArrayList<>();
        productIdQuantityDTOs.add(productIdQuantityDTO);

        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> shoppingCartService.removeProducts("testUser", productIdQuantityDTOs))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not present in cart with given id: dummyProductId");
    }
}
