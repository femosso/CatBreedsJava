package com.example.catbreeds.models;

import androidx.databinding.BaseObservable;

public class CatBreed extends BaseObservable {

    private String name;

    public CatBreed(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
