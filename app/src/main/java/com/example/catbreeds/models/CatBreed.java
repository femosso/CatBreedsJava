package com.example.catbreeds.models;

import androidx.databinding.BaseObservable;

public class CatBreed extends BaseObservable {

    private String imageUrl;

    private String text;

    public CatBreed(String imageUrl, String text) {
        this.imageUrl = imageUrl;
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
