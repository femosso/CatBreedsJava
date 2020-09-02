package com.example.catbreeds.data.repository;

import com.example.catbreeds.data.model.CatBreed;
import com.example.catbreeds.data.model.CatBreedImage;

import java.util.List;

public interface Repository {

    interface FetchDataCallback<T> {
        void onLoaded(T data);
    }

    void fetchList(FetchDataCallback<List<CatBreed>> callback);

    void fetchImage(String breedId, FetchDataCallback<CatBreedImage> callback);

}
