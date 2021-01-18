package com.ecommerce.controller;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.exception.ErrorResponse;
import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import com.ecommerce.util.AppUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ApiOperation(value = "/api/{userId}/orders", tags = "Order Controller")
@RestController
@RequestMapping("/api/{userId}/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @ApiOperation(value = "Returns all orders associated with specific user", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = List.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @GetMapping()
    public List<OrderDTO> getAll(@PathVariable String userId) {
        List<Order> orders = orderService.getAll(userId);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.parallelStream().forEach(order -> {
            List<Order.OrderedProduct> orderedProducts = order.getProducts();
            List<OrderDTO.OrderedProductDTO> orderedProductDTOs = new ArrayList<>();
            orderedProducts.forEach(orderedProduct -> orderedProductDTOs.add(
                    new OrderDTO.OrderedProductDTO(orderedProduct.getProductId(), orderedProduct.getName(),
                            orderedProduct.getType(), orderedProduct.getBrand(), orderedProduct.getPrice(),
                            orderedProduct.getQuantity())));

            orderDTOs.add(new OrderDTO(order.getId(), orderedProductDTOs,
                order.getSubTotal(), AppUtils.formatDate(order.getPurchaseDate())));
        });
        return orderDTOs;
    }

    @ApiOperation(value = "Returns a specific order associated with specific user with given id", response = OrderDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @GetMapping("/{orderId}")
    public OrderDTO get(@PathVariable String userId, @PathVariable String orderId) {
        Order order = orderService.get(userId, orderId);

        List<Order.OrderedProduct> orderedProducts = order.getProducts();
        List<OrderDTO.OrderedProductDTO> orderedProductDTOs = new ArrayList<>();
        orderedProducts.forEach(orderedProduct -> orderedProductDTOs.add(
                new OrderDTO.OrderedProductDTO(orderedProduct.getProductId(), orderedProduct.getName(),
                        orderedProduct.getType(), orderedProduct.getBrand(), orderedProduct.getPrice(),
                        orderedProduct.getQuantity())));

        return new OrderDTO(order.getId(), orderedProductDTOs, order.getSubTotal(),
                AppUtils.formatDate(order.getPurchaseDate()));
    }

    @ApiOperation(value = "Place an order for specific user", response = OrderDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @PostMapping()
    public OrderDTO place(@PathVariable String userId, @RequestParam(name = "Card Number") String cardNumber,
                          @RequestParam(name = "Expiry Month") int expiryMonth, @RequestParam(name = "Expiry Day") int expiryDay,
                          @RequestParam int cvv) {
        Order order = orderService.placeOrder(userId);

        List<Order.OrderedProduct> orderedProducts = order.getProducts();
        List<OrderDTO.OrderedProductDTO> orderedProductDTOs = new ArrayList<>();
        orderedProducts.forEach(orderedProduct -> orderedProductDTOs.add(
                new OrderDTO.OrderedProductDTO(orderedProduct.getProductId(), orderedProduct.getName(),
                        orderedProduct.getType(), orderedProduct.getBrand(), orderedProduct.getPrice(),
                        orderedProduct.getQuantity())));

        return new OrderDTO(order.getId(), orderedProductDTOs, order.getSubTotal(),
                AppUtils.formatDate(order.getPurchaseDate()));
    }
}
