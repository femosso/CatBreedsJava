package com.example.catbreeds.repository.remote;

import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.models.CatBreedImage;

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