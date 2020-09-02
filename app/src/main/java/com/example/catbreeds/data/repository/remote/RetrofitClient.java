package com.example.catbreeds.data.repository.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.catbreeds.data.repository.remote.ApiConfig.AUTH_HEADER_KEY;
import static com.example.catbreeds.data.repository.remote.ApiConfig.AUTH_HEADER_VALUE;
import static com.example.catbreeds.data.repository.remote.ApiConfig.BASE_URL;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder().create();

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder requestBuilder = chain.request().newBuilder();
                            requestBuilder.header(AUTH_HEADER_KEY, AUTH_HEADER_VALUE);
                            return chain.proceed(requestBuilder.build());
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}