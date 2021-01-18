package com.ecommerce.service;

import com.ecommerce.exception.ResourceAlreadyExistException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
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
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserRepository userRepository;

    User user = new User("testUser", "testUser@abc.com", "Test", "User",
            "1234567890", "testAddress", User.UserType.SELLER);

    @Test
    public void add_validProduct_success() {
        Product product = new Product("PCJ100", "Jeans", "Clothing", "LP",
                3000, 5, "LP Jeans", "LP Jeans");

        Mockito.when(productRepository.save(product)).thenReturn(product);
        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThat(productService.add("testUser", product)).isEqualTo(product);
    }

    @Test
    public void add_existingProduct_throwsException() {
        Product product = new Product("PCJ100", "Jeans", "Clothing", "LP",
                3000, 5, "LP Jeans", "LP Jeans");

        Mockito.when(productRepository.findById("PCJ100")).thenReturn(Optional.of(product));
        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> productService.add("testUser", product))
                .isInstanceOf(ResourceAlreadyExistException.class)
                .hasMessage("Product already exists with given id: PCJ100");
    }

    @Test
    public void delete_existingProduct_success() {
        Product product = new Product("PCJ100", "Jeans", "Clothing", "LP",
                3000, 5, "LP Jeans", "LP Jeans");

        Mockito.when(productRepository.findById("PCJ100")).thenReturn(Optional.of(product));
        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        productService.delete("testUser","PCJ100");
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }

    @Test
    public void delete_dummyProduct_throwsException() {
        Mockito.when(productRepository.findById("PCJ100")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> productService.delete("testUser", "PCJ100"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with given id: PCJ100");
    }

    @Test
    public void getById_existingProduct_success() {
        Product product = new Product("PCJ100", "Jeans", "Clothing", "LP",
                3000, 5, "LP Jeans", "LP Jeans");

        Mockito.when(productRepository.findById("PCJ100")).thenReturn(Optional.of(product));

        assertThat(productService.getById("PCJ100")).isEqualTo(product);
    }

    @Test
    public void getById_dummyProduct_throwsException() {
        Mockito.when(productRepository.findById("PCJ100")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getById("PCJ100"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with given id: PCJ100");
    }

    @Test
    public void update_validProduct_success() {
        Product product = new Product("PCJ100", "Jeans", "Clothing", "LP",
                3000, 5, "LP Jeans", "LP Jeans");

        Product updatedProduct = new Product("PCJ100", "Jeans - Updated Name", "Clothing",
                "LP",3000,5, "LP Jeans - Updated description",
                "Updated specification LP Jeans");
        Mockito.when(productRepository.findById("PCJ100")).thenReturn(Optional.of(product));
        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThat(productService.update("testUser","PCJ100", updatedProduct)).isEqualTo(updatedProduct);
    }

    @Test
    public void update_dummyProduct_throwsException() {
        Product updatedProduct = new Product("PCJ100", "Jeans - Updated Name", "Clothing",
                "LP",3000,5, "LP Jeans - Updated description",
                "Updated specification LP Jeans");

        Mockito.when(productRepository.findById("PCJ100")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> productService.update("testUser","PCJ100", updatedProduct))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with given id: PCJ100");
    }
}
