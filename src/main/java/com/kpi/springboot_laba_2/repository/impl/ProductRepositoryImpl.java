package com.kpi.springboot_laba_2.repository.impl;

import com.kpi.springboot_laba_2.entity.Product;
import com.kpi.springboot_laba_2.repository.ProductRepository;
import com.kpi.springboot_laba_2.util.ProductDB;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private ProductDB productDB;

    public ProductRepositoryImpl(ProductDB productDB) {
        this.productDB = productDB;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productDB.getProductDB().values());
    }

    @Override
    public List<Product> findAllProductsByCategoryId(int id) {
        List<Product> products = findAll();
        products.removeIf(el -> el.getCategoryId() != id);
        return products;
    }

    @Override
    public Product findById(int id) {
        return productDB.getProductDB().get(id);
    }

    @Override
    public void save(Product product) {
        productDB.getProductDB().put(product.getId(), product);
    }

    @Override
    public void deleteById(int id) {
        productDB.getProductDB().remove(id);
    }

    @Override
    public void update(int id, Product product) {
        productDB.getProductDB().replace(id, product);
    }

    @Override
    public int createUniqueId() {
        return productDB.createUniqueId();
    }
}
