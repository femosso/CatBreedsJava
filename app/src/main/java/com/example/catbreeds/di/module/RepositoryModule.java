package com.example.catbreeds.di.module;

import com.example.catbreeds.di.scope.AppScope;
import com.example.catbreeds.data.repository.Repository;
import com.example.catbreeds.data.repository.remote.RetrofitRepositoryImpl;

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