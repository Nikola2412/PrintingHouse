package com.example.backend.models;

import java.util.List;

public class ProductDitails {
    private int id;
    private String name;
    private String print;
    private String city;
    private int like_cnt;
    private int dislike_cnt;
    private List<Integer> images;
    public ProductDitails(){

    }
    public ProductDitails(int id, String name, String print, String city, int like_cnt, int dislike_cnt, List<Integer> images) {
        this.id = id;
        this.name = name;
        this.print = print;
        this.city = city;
        this.like_cnt = like_cnt;
        this.dislike_cnt = dislike_cnt;
        this.images = images;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPrint() {
        return print;
    }
    public void setPrint(String print) {
        this.print = print;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
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
    public List<Integer> getImages() {
        return images;
    }
    public void setImages(List<Integer> images) {
        this.images = images;
    }
}
