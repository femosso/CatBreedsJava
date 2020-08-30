package com.example.catbreeds.repository.remote.retrofit;

import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.models.CatBreedImage;
import com.example.catbreeds.repository.Repository;
import com.example.catbreeds.repository.remote.ApiInterface;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitRepositoryImpl implements Repository {

    private static RetrofitRepositoryImpl sInstance;

    private ApiInterface apiInterface;
    private MutableLiveData<List<CatBreed>> breeds = new MutableLiveData<>();

    public RetrofitRepositoryImpl(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public static RetrofitRepositoryImpl getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitRepositoryImpl.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitRepositoryImpl(RetrofitClient.getClient().create(ApiInterface.class));
                }
            }
        }
        return sInstance;
    }

    @Override
    public MutableLiveData<List<CatBreed>> getBreeds() {
        return breeds;
    }

    @Override
    public void fetchList() {
        Callback<List<CatBreed>> retrofitCallback = new Callback<List<CatBreed>>() {
            @Override
            public void onResponse(Call<List<CatBreed>> call, Response<List<CatBreed>> response) {
                List<CatBreed> body = response.body();
                breeds.setValue(body);
            }

            @Override
            public void onFailure(Call<List<CatBreed>> call, Throwable t) {
                breeds.setValue(null);
            }
        };

        this.apiInterface.getBreeds().enqueue(retrofitCallback);
    }

    @Override
    public void fetchImage(String breedId, final FetchImageCallback<CatBreedImage> callback) {
        Callback<List<CatBreedImage>> retrofitCallback = new Callback<List<CatBreedImage>>() {
            @Override
            public void onResponse(Call<List<CatBreedImage>> call, Response<List<CatBreedImage>> response) {
                List<CatBreedImage> body = response.body();
                if (response.code() == 200 && body.size() > 0) {
                    callback.onLoaded(body.get(0));
                } else {
                    callback.onLoaded(null);
                }
            }

            @Override
            public void onFailure(Call<List<CatBreedImage>> call, Throwable t) {
                callback.onLoaded(null);
            }
        };

        this.apiInterface.getImageByBreed(breedId).enqueue(retrofitCallback);
    }
}