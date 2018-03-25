package com.inventory;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.Product;

@RestController
@SpringBootApplication(scanBasePackages = "com")
@RequestMapping("/inventory")
public class InventoryApplication {

    @Autowired
    public ProductRepository productRepository;

    /*
        Input
        {
            "name": "laptop",
            "type": "electronic"
        }
     */
    @RequestMapping(
            value = "/product/add",
            method = RequestMethod.POST,
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    /*
        Input:
        [
            {
                "name": "Bed",
                "type": "furniture"
            },
            {
                "name": "Tooth Paste",
                "type": "health"
            }
        ]
     */
    @RequestMapping(
            value = "/product/addall",
            method = RequestMethod.POST,
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public List<Product> addProducts(@RequestBody List<Product> products) {
        for (Product product : products) {
            if (productRepository.save(product) == null) {
                return null;
            }
        }

        return allProducts();
    }

    @RequestMapping(
            value = "/product/all",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    @RequestMapping(
            value = "/product/get/{name}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public Product getProduct(@PathVariable("name") String name) {
        return productRepository.findByName(name);
    }

    @RequestMapping(
            value = "/product/delete/{name}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    public List<Product> deleteProduct(@PathVariable("name") String name) {
        return productRepository.deleteByName(name);
    }

    /*
        Input:
        {
            "id": "5aaebdade9e2ca14b4ff9625",
            "name": "laptop",
            "type": "electronic"
        }
     */
    @RequestMapping(
            value = "/product/update",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Product updateProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    public static void main(String[] args) { SpringApplication.run(InventoryApplication.class, args); }
}
