package com.ecommerce.dto;

public class ProductIdQuantityDTO {

    private String productId;

    private int quantity;

    private ProductIdQuantityDTO() {
    }

    public ProductIdQuantityDTO(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
