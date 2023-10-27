package com.kpi.springboot_laba_2.repository;

import com.kpi.springboot_laba_2.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    List<Product> findAllProductsByCategoryId(int id);
    Product findById(int id);
    void save(Product product);
    void update(int id, Product product);
    void deleteById(int id);
    int createUniqueId();
}
