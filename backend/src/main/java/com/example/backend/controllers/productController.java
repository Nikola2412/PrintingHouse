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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/product")
    public Product getProductByID(@RequestParam Long id) {
        return new productRepo().findProductByID(id);
    }

    @GetMapping("/productDitails")
    public ProductDitails PorductDitails(@RequestParam Long id) {
        return new productRepo().getPorductDitails(id);
    }
    

    private String uploadPath = Paths
                .get("backend/uploads")
                .toAbsolutePath()
                .normalize()
                .toString();

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getMainImage(@PathVariable Long id) {
        String name = new productRepo().getMainImage(id);

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
        String name = new productRepo().getImage(id);

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
        return new productRepo().findProductsBySearchParam(param);
    }
    @GetMapping("/findByCategory/{category}")
    public List<Product> findProductsByCategory(@PathVariable String category, @RequestParam String param) {
        return new productRepo().findProductsByCategory(category, param);
    }
    
    
}
