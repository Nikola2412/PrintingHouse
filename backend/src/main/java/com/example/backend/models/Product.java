package com.example.backend.models;


public class Product {
    private int id;
    private String print_id;
    private String code;
    private String name;
    private String description;
    private String subcategory;
    private double price;
    private int stock;
    private int like_cnt;
    private int dislike_cnt;
    private String image;
    public Product() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPrint_id() {
        return print_id;
    }
    public void setPrint_id(String print_id) {
        this.print_id = print_id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSubcategory() {
        return subcategory;
    }
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getLike_cnt() {
        return like_cnt;
    }
    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }
    public int getDislike_cnt() {
        return dislike_cnt;
    }
    public void setDislike_cnt(int dislike_cnt) {
        this.dislike_cnt = dislike_cnt;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = "http://localhost:8080/images/products/" + image;
    }
    public Product(int id, String print_id, String code, String name, String description, String subcategory,
            double price, int stock, int like_cnt, int dislike_cnt, String image) {
        this.id = id;
        this.print_id = print_id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.subcategory = subcategory;
        this.price = price;
        this.stock = stock;
        this.like_cnt = like_cnt;
        this.dislike_cnt = dislike_cnt;
        this.image = image;
    }
}
