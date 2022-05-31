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

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getPurchased() {
        return purchased;
    }

    public void setPurchased(double purchased) {
        this.purchased = purchased;
    }
}
