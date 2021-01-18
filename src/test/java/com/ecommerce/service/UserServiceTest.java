package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.User;
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
public class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    User user = new User("testUser", "testUser@abc.com", "Test", "User",
            "1234567890", "testAddress", User.UserType.CUSTOMER);

    @Test
    public void get_validUser_success() {
        Mockito.when(userRepository.findById("testUser")).thenReturn(Optional.of(user));

        assertThat(userService.get("testUser")).isEqualTo(user);
    }

    @Test
    public void get_invalidUser_throwsException() {
        Mockito.when(userRepository.findById("dummyUser")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.get("dummyUser"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with given id: dummyUser");
    }
}
