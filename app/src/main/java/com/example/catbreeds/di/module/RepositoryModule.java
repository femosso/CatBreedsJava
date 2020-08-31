package com.example.catbreeds.di.module;

import com.example.catbreeds.di.scope.AppScope;
import com.example.catbreeds.repository.Repository;
import com.example.catbreeds.repository.remote.retrofit.RetrofitRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @AppScope
    @Provides
    Repository provideRepository() {
        return RetrofitRepositoryImpl.getInstance();
    }
}