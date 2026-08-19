package com.example.backend.db.dao;

import java.util.List;

import com.example.backend.models.Message;
import com.example.backend.models.Product;
import com.example.backend.models.ProductDitails;

public interface productInterface {
    public Message getProductCnt();
    public List<Product> getTopProducts();
    public Product findProductByID(Long id);
    public ProductDitails getPorductDitails(Long id);
    public List<Product> findProductsBySearchParam(String searchParam);
    public List<Product> findProductsByCategory(String category, String searchParam);
    public String getMainImage(Long id);
    public String getImage(Long id);
}
