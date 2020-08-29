package com.example.catbreeds.models;

import androidx.databinding.BaseObservable;

public class CatBreedImage extends BaseObservable {

    private String url;

    public CatBreedImage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
