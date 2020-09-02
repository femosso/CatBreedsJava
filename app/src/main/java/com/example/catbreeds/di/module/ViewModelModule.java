package com.example.catbreeds.di.module;

import com.example.catbreeds.di.scope.ViewModelKey;
import com.example.catbreeds.ui.viewmodel.CatBreedsViewModel;
import com.example.catbreeds.ui.viewmodel.CatBreedsViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CatBreedsViewModel.class)
    abstract ViewModel bindCatBreedsViewModel(CatBreedsViewModel catBreedsViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindCatBreedsViewModelFactory(CatBreedsViewModelFactory factory);
}