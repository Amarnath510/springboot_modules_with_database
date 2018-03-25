package com.model;

import org.springframework.data.annotation.Id;

public class Product {

    @Id
    public String id;

    public String name;
    public String type;

    public Product() {}

    public Product(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Product[id=%s, name=%s, type=%s]", id, name, type);
    }
}
