package com.example.catbreeds.repository;

import com.example.catbreeds.models.CatBreed;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class RemoteRepositoryImpl implements Repository {

    private static RemoteRepositoryImpl sInstance;

    private ArrayList<CatBreed> dataSet = new ArrayList<>();

    public static RemoteRepositoryImpl getInstance() {
        if (sInstance == null) {
            synchronized (RemoteRepositoryImpl.class) {
                if (sInstance == null) {
                    sInstance = new RemoteRepositoryImpl();
                }
            }
        }
        return sInstance;
    }

    // TODO - get data from web service using Retrofit
    @Override
    public MutableLiveData<List<CatBreed>> getBreeds() {
        setCatBreeds();

        MutableLiveData<List<CatBreed>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setCatBreeds() {
        dataSet.add(new CatBreed("https://64.media.tumblr.com/tumblr_lu230691dm1qdvbl3o1_250.jpg", "cat 1"));
        dataSet.add(new CatBreed("https://64.media.tumblr.com/tumblr_lu230691dm1qdvbl3o1_250.jpg", "cat 2"));
        dataSet.add(new CatBreed("https://64.media.tumblr.com/tumblr_lu230691dm1qdvbl3o1_250.jpg", "cat 3"));
    }
}
