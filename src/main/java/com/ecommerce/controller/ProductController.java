package com.ecommerce.controller;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.exception.ErrorResponse;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ApiOperation(value = "/api/products", tags = "Product Controller")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation(value = "Returns all available products in the system", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = List.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @GetMapping
    public List<ProductDTO> getAll() {
        List<ProductDTO> productDTOs = new ArrayList<>();
        productService.getAll().parallelStream().forEach(product -> productDTOs.add(
                new ProductDTO(product.getId(), product.getName(), product.getType(), product.getBrand(),
                        product.getPrice(), product.getAvailableQuantity(), product.getDescription(), product.getSpecifications())
        ));
        return productDTOs;
    }

    @ApiOperation(value = "Returns a specific product with given id", response = ProductDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @GetMapping("/{productId}")
    public ProductDTO get(@PathVariable String productId) {
        Product product = productService.getById(productId);
        return new ProductDTO(product.getId(), product.getName(), product.getType(), product.getBrand(),
                product.getPrice(), product.getAvailableQuantity(), product.getDescription(), product.getSpecifications());
    }

    @ApiOperation(value = "Returns all products filtered with specific criteria", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = List.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @PostMapping("/filtered")
    public List<ProductDTO> getFiltered(@RequestBody ProductDTO.ProductFilterDTO productFilterDTO) {
        Product.ProductFilter productFilter = new Product.ProductFilter(productFilterDTO.getId(), productFilterDTO.getName(),
                productFilterDTO.getType(), productFilterDTO.getBrand(), productFilterDTO.getMaxPrice(), productFilterDTO.getMinPrice(),
                productFilterDTO.getMaxAvailableQuantity(), productFilterDTO.getMinAvailableQuantity());

        List<ProductDTO> productDTOs = new ArrayList<>();
        productService.getByFilter(productFilter)
                      .parallelStream().forEach(product -> productDTOs.add(
                              new ProductDTO(product.getId(), product.getName(), product.getType(), product.getBrand(),
                                      product.getPrice(), product.getAvailableQuantity(), product.getDescription(),
                                      product.getSpecifications())
        ));

        return productDTOs;
    }

    @ApiOperation(value = "Adds specific product in the system", response = ProductDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @PostMapping
    public ProductDTO add(@RequestBody ProductDTO productDTO, @RequestParam String userId) {
        productService.add(userId, new Product(productDTO.getId(), productDTO.getName(), productDTO.getType(),
                productDTO.getBrand(), productDTO.getPrice(), productDTO.getAvailableQuantity(),
                productDTO.getDescription(), productDTO.getSpecifications()));
        return productDTO;
    }

    @ApiOperation(value = "Updates a specific product with given id", response = ProductDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @PutMapping("/{productId}")
    public ProductDTO update(@PathVariable String productId, @RequestParam String userId,
                             @RequestBody ProductDTO productDTO) {
        productService.update(userId, productId, new Product(productDTO.getId(), productDTO.getName(), productDTO.getType(),
                productDTO.getBrand(), productDTO.getPrice(), productDTO.getAvailableQuantity(),
                productDTO.getDescription(), productDTO.getSpecifications()));
        return productDTO;
    }

    @ApiOperation(value = "Deletes a specific product with given id", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @ResponseBody
    @DeleteMapping("/{productId}")
    public String delete(@PathVariable String productId, @RequestParam String userId) {
        productService.delete(userId, productId);
        return "Product is deleted successfully";
    }
}
