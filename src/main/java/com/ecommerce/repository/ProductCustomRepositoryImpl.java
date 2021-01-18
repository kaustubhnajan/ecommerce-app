package com.ecommerce.repository;

import com.ecommerce.model.Product;
import com.ecommerce.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Product> getProductByFilter(Product.ProductFilter productFilter) {
        Set<Product> products = new HashSet<>();

        if (!AppUtils.isEmpty(productFilter.getName())) {
            String sql = "from Product p where upper(p.name) = :name";
            Query query = entityManager.createQuery(sql, Product.class);
            query.setParameter("name", productFilter.getName().toUpperCase());
            products.addAll(query.getResultList());
        }

        if (!AppUtils.isEmpty(productFilter.getType())) {
            String sql = "from Product p where upper(p.type) = :type";
            Query query = entityManager.createQuery(sql, Product.class);
            query.setParameter("type", productFilter.getType().toUpperCase());
            products.addAll(query.getResultList());
        }

        if (!AppUtils.isEmpty(productFilter.getBrand())) {
            String sql = "from Product p where upper(p.brand) = :brand";
            Query query = entityManager.createQuery(sql, Product.class);
            query.setParameter("brand", productFilter.getBrand().toUpperCase());
            products.addAll(query.getResultList());
        }

        if (productFilter.getMinPrice() != null || productFilter.getMaxPrice() != null) {
            String sql = "from Product p where p.price ";
            Query query = null;
            if (productFilter.getMinPrice() != null && productFilter.getMaxPrice() != null) {
                sql += ">= :minPrice and p.price <= :maxPrice";
                query = entityManager.createQuery(sql, Product.class);
                query.setParameter("minPrice", productFilter.getMinPrice().doubleValue());
                query.setParameter("maxPrice", productFilter.getMaxPrice().doubleValue());
            } else if (productFilter.getMinPrice() != null) {
                sql += ">= :minPrice";
                query = entityManager.createQuery(sql, Product.class);
                query.setParameter("minPrice", productFilter.getMinPrice().doubleValue());
            } else {
                sql += "<= :maxPrice";
                query = entityManager.createQuery(sql, Product.class);
                query.setParameter("maxPrice", productFilter.getMaxPrice().doubleValue());
            }
            products.addAll(query.getResultList());
        }

        if (productFilter.getMinAvailableQuantity() != null || productFilter.getMaxAvailableQuantity() != null) {
            String sql = "from Product p where p.availableQuantity ";
            Query query = null;
            if (productFilter.getMinAvailableQuantity() != null && productFilter.getMaxAvailableQuantity() != null) {
                sql += ">= :minAvailableQuantity and p.availableQuantity <= :maxAvailableQuantity";
                query = entityManager.createQuery(sql, Product.class);
                query.setParameter("minAvailableQuantity", productFilter.getMinAvailableQuantity().intValue());
                query.setParameter("maxAvailableQuantity", productFilter.getMaxAvailableQuantity().intValue());
            } else if (productFilter.getMinAvailableQuantity() != null) {
                sql += ">= :minAvailableQuantity";
                query = entityManager.createQuery(sql, Product.class);
                query.setParameter("minAvailableQuantity", productFilter.getMinAvailableQuantity().intValue());
            } else {
                sql += "<= :maxAvailableQuantity";
                query = entityManager.createQuery(sql, Product.class);
                query.setParameter("maxAvailableQuantity", productFilter.getMaxAvailableQuantity().intValue());
            }
            products.addAll(query.getResultList());
        }

        return new ArrayList<>(products);
    }
}
