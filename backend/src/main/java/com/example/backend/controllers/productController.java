package com.example.backend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.dao.productRepo;
import com.example.backend.models.Message;
import com.example.backend.models.Product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200/")
public class productController {
    
    @GetMapping("/getProductCnt")
    public Message getCnt() {
        return new productRepo().getProductCnt();
    }
    
    @GetMapping("/getTopProducts")
    public List<Product> getTopProducts() {
        return new productRepo().getTopProducts();
    }
    //http://localhost:8080/products/find?param=H
    @GetMapping("/find")
    public List<Product> getMethodName(@RequestParam String param) {
        return new productRepo().findProducts(param);
    }
    
}
