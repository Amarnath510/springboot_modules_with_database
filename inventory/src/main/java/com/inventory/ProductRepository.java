package com.inventory;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

    Product findByName(String name);

    List<Product> findByType(String type);

    List<Product> findAll();

    List<Product> deleteByName(String name);

}
