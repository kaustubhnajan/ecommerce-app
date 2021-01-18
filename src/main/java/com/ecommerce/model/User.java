package com.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the application user
 */
@Entity
@Table(name = "user")
public class User {

    /** User id of the user */
    @Id
    private String id;

    /** User's email id */
    private String emailId;

    /** User's full name */
    private String fname;

    /** User's last name */
    private String lname;

    /** User's mobile */
    private String mobile;

    /** User's address */
    private String address;

    /** Type of user */
    private UserType type;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart cart;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    private User() { }

    public User(String id, String emailId, String fname, String lname, String mobile,
                String address, UserType type) {
        this.id = id;
        this.emailId = emailId;
        this.fname = fname;
        this.lname = lname;
        this.mobile = mobile;
        this.address = address;
        this.type = type;
        this.cart = new ShoppingCart(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserType getType() {
        return type;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public enum UserType { CUSTOMER, SELLER }

    /**
     * Represents the shopping cart of the specific user
     */
    @Entity
    @Table(name = "shopping_cart")
    public static class ShoppingCart {

        /** Shopping cart id. Used for internal mapping */
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;

        /** User owning the instance of the shopping cart */
        @JsonBackReference
        @OneToOne(mappedBy = "cart")
        private User user;

        /** Products those are added to the shopping cart */
        @JsonManagedReference
        @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<CartProduct> products = new ArrayList<>();

        /** Subtotal of prices of the products added to the cart */
        private double subTotal;

        /** No-arg private constructor */
        private ShoppingCart() { }

        public ShoppingCart(User user) {
            this.user = user;
            this.subTotal = 0;
        }

        public User getUser() {
            return user;
        }

        public List<CartProduct> getProducts() {
            return products;
        }

        public void setProducts(List<CartProduct> products) {
            this.products = products;
        }

        public double getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(double subTotal) {
            this.subTotal = subTotal;
        }
    }

    /**
     * Represents the product added to the shopping cart
     */
    @Entity
    @Table(name = "cart_product")
    public static class CartProduct {

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

        /** Product quantity which is added to the cart */
        private int quantity;

        /** Shopping cart owning instance of the product */
        @JsonBackReference
        @ManyToOne
        private ShoppingCart cart;

        /** No-arg constructor */
        private CartProduct() { }

        public CartProduct(String id, String name, String type, String brand, double price,
                           int quantity, ShoppingCart cart) {
            this.productId = id;
            this.name = name;
            this.type = type;
            this.brand = brand;
            this.price = price;
            this.quantity = quantity;
            this.cart = cart;
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

        public ShoppingCart getCart() {
            return cart;
        }

        public void setCart(ShoppingCart cart) {
            this.cart = cart;
        }
    }
}
