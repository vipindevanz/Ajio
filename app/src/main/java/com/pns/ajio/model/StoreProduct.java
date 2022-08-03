package com.pns.ajio.model;

public class StoreProduct {

    private String videoId;
    private String imgUrl;
    private String productUrl;
    private String key;
    private double purchased;

    public StoreProduct() {
    }

    public StoreProduct(String videoId, String imgUrl, String productUrl, String key, double purchased) {
        this.videoId = videoId;
        this.imgUrl = imgUrl;
        this.productUrl = productUrl;
        this.key = key;
        this.purchased = purchased;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getKey() {
        return key;
    }

    public double getPurchased() {
        return purchased;
    }

}
