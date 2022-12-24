package com.pns.ajio.data.models;

public class Product {

    private String imgUrl;
    private String productUrl;
    private String name;
    private String desc;
    private double rating;
    private String brand;
    private int purchased;
    private String key;
    private String category;

    public Product() {
    }

    public Product(String imgUrl, String productUrl, String name, String desc, double rating, String brand, int purchased, String key, String category) {
        this.imgUrl = imgUrl;
        this.productUrl = productUrl;
        this.name = name;
        this.desc = desc;
        this.rating = rating;
        this.brand = brand;
        this.purchased = purchased;
        this.key = key;
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}