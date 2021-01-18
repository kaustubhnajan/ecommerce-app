package com.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Represent the product in the system
 */
@Entity
@Table(name = "product")
public class Product {

    /** Product id of the product */
    @Id
    private String id;

    /** Product Name */
    private String name;

    /** Product type */
    private String type;

    /** Product brand */
    private String brand;

    /** Product price */
    private double price;

    /** Available quantity of the product in the system */
    private int availableQuantity;

    /** Product description */
    private String description;

    /** Product specifications */
    private String specifications;

    /** No-arg private constructor */
    private Product() { }

    public Product(String id, String name, String type, String brand, double price, int availableQuantity,
                   String description, String specifications) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.description = description;
        this.specifications = specifications;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    /**
     * Overrides parent to check if two products are same or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                availableQuantity == product.availableQuantity &&
                id.equals(product.id) &&
                name.equals(product.name) &&
                type.equals(product.type) &&
                brand.equals(product.brand) &&
                Objects.equals(description, product.description) &&
                Objects.equals(specifications, product.specifications);
    }

    /**
     * Overrides parent to check if two products are same or not
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, brand, price, availableQuantity,
                description, specifications);
    }

    /**
     * Represent filter which can be applied to retrieve products
     * with some filter criteria
     */
    public static class ProductFilter {

        /** Product id with which matching products will be retrieved */
        private String id;

        /** Product name with which matching products will be retrieved */
        private String name;

        /** Product type with which matching products will be retrieved */
        private String type;

        /** Product brand with which matching products will be retrieved */
        private String brand;

        /** Product maximum price with which matching products will be retrieved */
        private Double maxPrice;

        /** Product minimum with which matching products will be retrieved */
        private Double minPrice;

        /** Product maximum available with which matching products will be retrieved */
        private Integer maxAvailableQuantity;

        /** Product minimum available with which matching products will be retrieved */
        private Integer minAvailableQuantity;

        public ProductFilter() { }

        public ProductFilter(String id, String name, String type, String brand, double maxPrice, double minPrice,
                             int maxAvailableQuantity, int minAvailableQuantity) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.brand = brand;
            this.maxPrice = maxPrice;
            this.minPrice = minPrice;
            this.maxAvailableQuantity = maxAvailableQuantity;
            this.minAvailableQuantity = minAvailableQuantity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public Double getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(Double maxPrice) {
            this.maxPrice = maxPrice;
        }

        public Double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(Double minPrice) {
            this.minPrice = minPrice;
        }

        public Integer getMaxAvailableQuantity() {
            return maxAvailableQuantity;
        }

        public void setMaxAvailableQuantity(Integer maxAvailableQuantity) {
            this.maxAvailableQuantity = maxAvailableQuantity;
        }

        public Integer getMinAvailableQuantity() {
            return minAvailableQuantity;
        }

        public void setMinAvailableQuantity(Integer minAvailableQuantity) {
            this.minAvailableQuantity = minAvailableQuantity;
        }
    }
}
