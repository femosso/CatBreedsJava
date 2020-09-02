package com.example.catbreeds.data.model;

import androidx.databinding.BaseObservable;

public class CatBreedImage extends BaseObservable {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
