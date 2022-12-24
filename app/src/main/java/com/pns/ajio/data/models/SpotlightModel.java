package com.pns.ajio.data.models;

public class SpotlightModel {

    private String videoUrl;
    private String des;

    public SpotlightModel() {
    }

    public SpotlightModel(String videoUrl, String des) {
        this.videoUrl = videoUrl;
        this.des = des;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getDes() {
        return des;
    }
}