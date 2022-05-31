package com.pns.ajio.model;

public class HomeProduct {

    public String category;
    public String imgUrl;
    public String productUrl;

    public HomeProduct() {
    }

    public HomeProduct(String category, String imgUrl, String productUrl) {
        this.category = category;
        this.imgUrl = imgUrl;
        this.productUrl = productUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
}
