package com.ecommerce.dto;

import java.util.List;

/**
 * Represents ShoppingCart DTO
 */
public class ShoppingCartDTO {

    /** Product DTOs those are added to the shopping cart */
    private List<CartProductDTO> products;

    /** Subtotal of prices of the products added to the cart */
    private double subTotal;

    public ShoppingCartDTO() { }

    public ShoppingCartDTO(List<CartProductDTO> products, double subTotal) {
        this.products = products;
        this.subTotal = subTotal;
    }

    public List<CartProductDTO> getProducts() {
        return products;
    }

    public double getSubTotal() {
        return subTotal;
    }

    /**
     * Represents the CartProduct DTO
     */
    public static class CartProductDTO {

        /** Product id of the product */
        private String productId;

        /** Product name */
        private String name;

        /** Product type */
        private String type;

        /** Product brand */
        private String brand;

        /** Product price */
        private double price;

        /** Product quantity which is added to the cart */
        private int quantity;

        private CartProductDTO() { }

        public CartProductDTO(String productId, String name, String type, String brand, double price,
                              int quantity) {
            this.productId = productId;
            this.name = name;
            this.type = type;
            this.brand = brand;
            this.price = price;
            this.quantity = quantity;
        }

        public String getProductId() {
            return productId;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getBrand() {
            return brand;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
