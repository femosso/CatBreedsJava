package com.example.catbreeds.di.module;

import com.example.catbreeds.ui.breeds.CatBreedsFragment;
import com.example.catbreeds.ui.detail.CatBreedDetailsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBindingModule {

    @ContributesAndroidInjector()
    abstract CatBreedsFragment bindBreedsFragment();

    @ContributesAndroidInjector()
    abstract CatBreedDetailsFragment binBreedDetailsFragment();
}
