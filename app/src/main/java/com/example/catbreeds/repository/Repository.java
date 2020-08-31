package com.example.catbreeds.repository;

import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.models.CatBreedImage;

import java.util.List;

public interface Repository {

    interface FetchDataCallback<T> {
        void onLoaded(T data);
    }

    void fetchList(FetchDataCallback<List<CatBreed>> callback);

    void fetchImage(String breedId, FetchDataCallback<CatBreedImage> callback);

}
