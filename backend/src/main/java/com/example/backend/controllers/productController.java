package com.example.backend.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.core.io.Resource;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.dao.productRepo;
import com.example.backend.models.Message;
import com.example.backend.models.Product;
import com.example.backend.models.ProductDitails;

import jakarta.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200/")
public class productController {
    
    private final productRepo productRepo;

    public productController(productRepo productRepo) {
        this.productRepo = productRepo;
    }


    @GetMapping("/getProductCnt")
    public Message getCnt() {
        return productRepo.getProductCnt();
    }
    
    @GetMapping("/getTopProducts")
    public List<Product> getTopProducts() {
        return productRepo.getTopProducts();
    }
    
    @GetMapping("/product")
    public Product getProductByID(@RequestParam Long id) {
        return productRepo.findProductByID(id);
    }

    @GetMapping("/productDitails")
    public ProductDitails PorductDitails(@RequestParam Long id) {
        return productRepo.getPorductDitails(id);
    }
    
    @PostConstruct
    public void init() {
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        System.out.println("Upload path: " + uploadPath);
    }


    private String uploadPath = Paths
                .get("uploads")
                .toAbsolutePath()
                .normalize()
                .toString();

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getMainImage(@PathVariable Long id) {
        String name = productRepo.getMainImage(id);

        if (name == null || name.isBlank()) {
            return ResponseEntity.notFound().build();
        }

        Path imagePath = Paths.get(uploadPath, "products", name);

        if (!Files.exists(imagePath) ||
            !Files.isRegularFile(imagePath) ||
            !Files.isReadable(imagePath)) {

            return ResponseEntity.notFound().build();
        }

        Resource image = new FileSystemResource(imagePath);

        return ResponseEntity.ok(image);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> getImages(@PathVariable Long id) {
        String name = productRepo.getImage(id);

        if (name == null || name.isBlank()) {
            return ResponseEntity.notFound().build();
        }

        Path imagePath = Paths.get(uploadPath, "products", name);

        if (!Files.exists(imagePath) ||
            !Files.isRegularFile(imagePath) ||
            !Files.isReadable(imagePath)) {

            return ResponseEntity.notFound().build();
        }

        Resource image = new FileSystemResource(imagePath);

        return ResponseEntity.ok(image);
    }

    
    
    //http://localhost:8080/products/find?param=H
    @GetMapping("/find")
    public List<Product> findProductBySeachParam(@RequestParam String param) {
        return productRepo.findProductsBySearchParam(param);
    }
    @GetMapping("/findByCategory/{category}")
    public List<Product> findProductsByCategory(@PathVariable String category, @RequestParam String param) {
        return productRepo.findProductsByCategory(category, param);
    }
    
    
}
