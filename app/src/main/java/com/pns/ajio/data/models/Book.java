package com.pns.ajio.data.models;

public class Book {

    private String imgUrl;
    private String bookUrl;
    private String title;
    private String desc;
    private double rating;
    private String language;
    private int length;
    private String writer;
    private String category;
    private int purchased;
    private String key;

    public Book() {
    }

    public Book(String imgUrl, String bookUrl, String title, String desc, double rating, String language, int length, String writer, String category, int purchased, String key) {
        this.imgUrl = imgUrl;
        this.bookUrl = bookUrl;
        this.title = title;
        this.desc = desc;
        this.rating = rating;
        this.language = language;
        this.length = length;
        this.writer = writer;
        this.category = category;
        this.purchased = purchased;
        this.key = key;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}