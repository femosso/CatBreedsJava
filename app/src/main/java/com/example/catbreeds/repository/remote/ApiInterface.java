package com.example.catbreeds.repository.remote;

import com.example.catbreeds.models.CatBreed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/v1/breeds")
    Call<List<CatBreed>> getBreeds();

}