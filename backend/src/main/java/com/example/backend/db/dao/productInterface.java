package com.example.backend.db.dao;

import java.util.List;

import com.example.backend.models.Message;
import com.example.backend.models.Product;

public interface productInterface {
    public Message getProductCnt();
    public List<Product> getTopProducts();
    public List<Product> findProducts(String searchParam);
}
