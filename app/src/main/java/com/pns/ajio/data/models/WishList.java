package com.pns.ajio.data.models;

public class WishList {

    private String key;
    private String category;

    public WishList(String key, String category) {
        this.key = key;
        this.category = category;
    }

    public WishList() {
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


