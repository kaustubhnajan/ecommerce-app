package com.ecommerce.controller;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.exception.ErrorResponse;
import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@ApiOperation(value = "/api/user/{userId}", tags = "User Controller")
@RestController
@RequestMapping("/api/user/{userId}")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Returns a specific user with given id", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @GetMapping
    public UserDTO get(@PathVariable String userId) {
        User user = userService.get(userId);
        return new UserDTO(user.getId(), user.getEmailId(), user.getFname(), user.getLname(), user.getMobile(),
                user.getAddress(), user.getType());
    }

    @ApiOperation(value = "Updates a specific user with given id", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @PutMapping
    public UserDTO update(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        userService.update(userId, new User(userDTO.getId(), userDTO.getEmailId(), userDTO.getFname(),
                userDTO.getLname(), userDTO.getMobile(), userDTO.getAddress(), userDTO.getType()));
        return userDTO;
    }
}
