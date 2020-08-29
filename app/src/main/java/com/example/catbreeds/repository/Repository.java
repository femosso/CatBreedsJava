package com.example.catbreeds.repository;

import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.models.CatBreedImage;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface Repository {

    interface FetchImageCallback<T> {
        void onLoaded(T data);
    }

    MutableLiveData<List<CatBreed>> getBreeds();

    void fetchList();

    void fetchImage(String breedId, FetchImageCallback<CatBreedImage> callback);

}
