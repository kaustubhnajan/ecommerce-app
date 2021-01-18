package com.ecommerce.model;

import com.ecommerce.util.AppUtils;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents the user's order
 */
@Entity
@Table(name = "shopping_order")
public class Order {

    /** Order id of the order */
    @Id
    private String id;

    /** User owning the instance of the order */
    @JsonBackReference
    @ManyToOne
    private User user;

    /** Products those are ordered */
    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderedProduct> products = new ArrayList<>();

    /** Subtotal of prices of the products ordered */
    private double subTotal;

    /** Date and time of the order placed */
    private Date purchaseDate;

    /** No-arg private constructor */
    private Order() { }

    public Order(User user, double subTotal) {
        this.id = AppUtils.getRandomOrderId();
        this.user = user;
        this.subTotal = subTotal;
        this.purchaseDate = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderedProduct> products) {
        this.products = products;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Represents the ordered product as part of specific order
     */
    @Entity
    @Table(name = "ordered_product")
    public static class OrderedProduct {

        /** Id used for internal mapping */
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;

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

        /** Shopping cart owning instance of the product */
        @JsonBackReference
        @ManyToOne
        private Order order;

        /** No-arg constructor */
        private OrderedProduct() { }

        public OrderedProduct(String productId, String name, String type, String brand,
                              double price, int quantity, Order order) {
            this.productId = productId;
            this.name = name;
            this.type = type;
            this.brand = brand;
            this.price = price;
            this.quantity = quantity;
            this.order = order;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }
    }
}
