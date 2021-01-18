package com.ecommerce.dto;

import java.util.List;

/**
 * Represent the Order DTO
 */
public class OrderDTO {

    /** Order id of the order */
    private  String orderId;

    /** Product DTOs of products those are ordered */
    private List<OrderedProductDTO> products;

    /** Subtotal of prices of the products ordered */
    private double subTotal;

    /** Date and time of the order placed */
    private String purchaseDate;

    private OrderDTO() { }

    public OrderDTO(String orderId, List<OrderedProductDTO> products, double subTotal,
                    String purchaseDate) {
        this.orderId = orderId;
        this.products = products;
        this.subTotal = subTotal;
        this.purchaseDate = purchaseDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<OrderedProductDTO> getProducts() {
        return products;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Represents the OrderedProduct DTO
     */
    public static class OrderedProductDTO {

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

        /** Ordered product quantity */
        private int quantity;

        private OrderedProductDTO() { }

        public OrderedProductDTO(String productId, String name, String type, String brand, double price,
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
