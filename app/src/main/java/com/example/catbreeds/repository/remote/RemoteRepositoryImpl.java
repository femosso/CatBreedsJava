package com.example.catbreeds.repository.remote;

import android.util.Log;

import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.repository.Repository;
import com.example.catbreeds.repository.remote.retrofit.RetrofitClient;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteRepositoryImpl implements Repository {

    private static RemoteRepositoryImpl sInstance;

    private ApiInterface apiInterface;
    private MutableLiveData<List<CatBreed>> breeds = new MutableLiveData<>();

    public RemoteRepositoryImpl(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public static RemoteRepositoryImpl getInstance() {
        if (sInstance == null) {
            synchronized (RemoteRepositoryImpl.class) {
                if (sInstance == null) {
                    sInstance = new RemoteRepositoryImpl(RetrofitClient.getClient().create(ApiInterface.class));
                }
            }
        }
        return sInstance;
    }

    @Override
    public MutableLiveData<List<CatBreed>> getBreeds() {
        return breeds;
    }

    public void fetchList() {
        Callback<List<CatBreed>> callback = new Callback<List<CatBreed>>() {
            @Override
            public void onResponse(Call<List<CatBreed>> call, Response<List<CatBreed>> response) {
                List<CatBreed> body = response.body();
                breeds.setValue(body);
            }

            @Override
            public void onFailure(Call<List<CatBreed>> call, Throwable t) {
                Log.e("Test", t.getMessage(), t);
            }
        };

        apiInterface.getBreeds().enqueue(callback);
    }
}