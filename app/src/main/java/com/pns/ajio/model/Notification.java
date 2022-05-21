package com.pns.ajio.model;

public class Notification {

    private String imgUrl;
    private String contentUrl;
    private String text;
    private String key;
    private int clicks;

    public Notification() {
    }

    public Notification(String imgUrl, String contentUrl, String text, String key, int clicks) {
        this.imgUrl = imgUrl;
        this.contentUrl = contentUrl;
        this.text = text;
        this.key = key;
        this.clicks = clicks;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
}
