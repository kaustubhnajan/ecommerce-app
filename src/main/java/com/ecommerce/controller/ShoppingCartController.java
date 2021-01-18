package com.ecommerce.controller;

import com.ecommerce.dto.ProductIdQuantityDTO;
import com.ecommerce.dto.ShoppingCartDTO;
import com.ecommerce.exception.ErrorResponse;
import com.ecommerce.model.User;
import com.ecommerce.service.ShoppingCartService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ApiOperation(value = "/api/{userId}/shoppingCart", tags = "Shopping Cart Controller")
@RestController
@RequestMapping("/api/{userId}/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    private enum Operation { ADD, REMOVE }

    @ApiOperation(value = "Returns shopping cart of specific user", response = ShoppingCartDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ShoppingCartDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @GetMapping
    public ShoppingCartDTO getCart(@PathVariable String userId) {
        User.ShoppingCart shoppingCart = shoppingCartService.get(userId);
        List<ShoppingCartDTO.CartProductDTO> cartProductDTOs = new ArrayList<>();
        shoppingCart.getProducts()
                    .forEach(product -> cartProductDTOs.add(
                            new ShoppingCartDTO.CartProductDTO(product.getProductId(), product.getName(),
                                    product.getType(), product.getBrand(), product.getPrice(), product.getQuantity())));
        return new ShoppingCartDTO(cartProductDTOs, shoppingCart.getSubTotal());
    }

    @ApiOperation(value = "Add/Remove given list of products to and from shopping cart", response = ShoppingCartDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ShoppingCartDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @PutMapping
    public ShoppingCartDTO addProductsToCart(@PathVariable String userId, @RequestBody List<ProductIdQuantityDTO> productIdQuantityDTOs,
                                             @RequestParam Operation operation) {
        User.ShoppingCart shoppingCart = null;
        if (operation == Operation.ADD) {
            shoppingCart = shoppingCartService.addProducts(userId, productIdQuantityDTOs);
        } else if (operation == Operation.REMOVE) {
            shoppingCart = shoppingCartService.removeProducts(userId, productIdQuantityDTOs);
        }

        List<ShoppingCartDTO.CartProductDTO> cartProductDTOs = new ArrayList<>();
        shoppingCart.getProducts()
                .forEach(product -> cartProductDTOs.add(
                        new ShoppingCartDTO.CartProductDTO(product.getProductId(), product.getName(),
                                product.getType(), product.getBrand(), product.getPrice(), product.getQuantity())));
        return new ShoppingCartDTO(cartProductDTOs, shoppingCart.getSubTotal());
    }
}
