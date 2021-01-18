package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    User user = new User("testUser", "testUser@abc.com", "Test", "User",
            "1234567890", "testAddress", User.UserType.CUSTOMER);

    @Test
    public void get_validUser_success() throws Exception {
        Mockito.when(userService.get("testUser")).thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/user/testUser").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":\"testUser\",\"emailId\":\"testUser@abc.com\",\"fname\":\"Test\"," +
                "\"lname\":\"User\",\"mobile\":\"1234567890\",\"address\":\"testAddress\",\"type\":\"CUSTOMER\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void update_validUser_success() throws Exception {
        User updatedUser = new User("testUser", "testUser@abc.com", "Updated Test",
                "Updated User","1234567890", "Updated testAddress", User.UserType.CUSTOMER);

        String updatedUserJson = "{\"id\":\"testUser\",\"emailId\":\"testUser@abc.com\",\"fname\":\"Updated Test\"," +
                "\"lname\":\"Updated User\",\"mobile\":\"1234567890\",\"address\":\"Updated testAddress\",\"type\":\"CUSTOMER\"}";

        Mockito.when(userService.update("testUser", updatedUser)).thenReturn(updatedUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
                "/api/user/testUser").accept(MediaType.APPLICATION_JSON)
                .content(updatedUserJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":\"testUser\",\"emailId\":\"testUser@abc.com\",\"fname\":\"Updated Test\"," +
                "\"lname\":\"Updated User\",\"mobile\":\"1234567890\",\"address\":\"Updated testAddress\",\"type\":\"CUSTOMER\"}\n";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }
}
