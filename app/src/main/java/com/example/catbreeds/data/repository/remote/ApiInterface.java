package com.example.catbreeds.data.repository.remote;

import com.example.catbreeds.data.model.CatBreed;
import com.example.catbreeds.data.model.CatBreedImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/v1/breeds")
    Call<List<CatBreed>> getBreeds();

    @GET("/v1/images/search")
    Call<List<CatBreedImage>> getImageByBreed(@Query("breed_id") String breed);
}