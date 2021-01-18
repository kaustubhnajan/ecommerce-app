package com.ecommerce.dto;

/**
 * Represent Product DTO
 */
public class ProductDTO {

    /** Product id of the product */
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

    public ProductDTO() { }

    public ProductDTO(String id, String name, String type, String brand, double price, int availableQuantity,
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

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public String getDescription() {
        return description;
    }

    public String getSpecifications() {
        return specifications;
    }

    /**
     * Represents ProductFilter DTO
     */
    public static class ProductFilterDTO {

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

        private ProductFilterDTO() { }

        public ProductFilterDTO(String id, String name, String type, String brand, double maxPrice, double minPrice,
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

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getBrand() {
            return brand;
        }

        public Double getMaxPrice() {
            return maxPrice;
        }

        public Double getMinPrice() {
            return minPrice;
        }

        public Integer getMaxAvailableQuantity() {
            return maxAvailableQuantity;
        }

        public Integer getMinAvailableQuantity() {
            return minAvailableQuantity;
        }
    }
}
