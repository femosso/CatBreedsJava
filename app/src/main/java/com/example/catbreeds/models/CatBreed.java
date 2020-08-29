package com.example.catbreeds.models;

import androidx.databinding.BaseObservable;

public class CatBreed extends BaseObservable {

    private String id;
    private String name;

    public CatBreed(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
