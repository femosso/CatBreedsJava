package com.example.catbreeds.viewmodel;

import android.view.View;

import com.example.catbreeds.R;
import com.example.catbreeds.ui.CatBreedsAdapter;
import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.repository.RemoteRepositoryImpl;
import com.example.catbreeds.repository.Repository;

import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CatBreedsViewModel extends ViewModel {

    private CatBreedsAdapter adapter;
    private MutableLiveData<List<CatBreed>> catBreeds;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private Repository repo;

    public ObservableInt showEmpty;

    public void init() {
        this.adapter = new CatBreedsAdapter(R.layout.view_cat_breed, this);
        this.repo = RemoteRepositoryImpl.getInstance();
        this.catBreeds = this.repo.getBreeds();

        showEmpty = new ObservableInt(View.GONE);
    }

    public MutableLiveData<List<CatBreed>> getCatBreeds() {
        return catBreeds;
    }

    public CatBreed getCatBreedAt(Integer index) {
        if (catBreeds.getValue() != null && index != null && catBreeds.getValue().size() > index) {
            return catBreeds.getValue().get(index);
        }
        return null;
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return mIsUpdating;
    }

    public void setCatBreedsInAdapter(List<CatBreed> breeds) {
        this.adapter.setCatBreeds(breeds);
        this.adapter.notifyDataSetChanged();
    }

    public CatBreedsAdapter getAdapter() {
        return this.adapter;
    }
}
