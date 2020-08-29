package com.example.catbreeds.repository;

import com.example.catbreeds.models.CatBreed;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface Repository {

    MutableLiveData<List<CatBreed>> getBreeds();

    void fetchList();

}
