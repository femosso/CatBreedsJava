package com.example.catbreeds.viewmodel;

import android.view.View;

import com.example.catbreeds.R;
import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.models.CatBreedImage;
import com.example.catbreeds.repository.Repository;
import com.example.catbreeds.repository.remote.RemoteRepositoryImpl;
import com.example.catbreeds.ui.CatBreedsAdapter;

import java.util.List;

import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CatBreedsViewModel extends ViewModel {

    private CatBreedsAdapter adapter;
    private MutableLiveData<List<CatBreed>> catBreeds;
    private MutableLiveData<CatBreed> selected;
    private Repository repo;

    public ObservableArrayMap<String, String> images;
    public ObservableBoolean loading;
    public ObservableInt showEmpty;

    public void init() {
        this.adapter = new CatBreedsAdapter(R.layout.view_cat_breed, this);
        this.repo = RemoteRepositoryImpl.getInstance();
        this.catBreeds = this.repo.getBreeds();
        this.selected = new MutableLiveData<>();

        this.images = new ObservableArrayMap<>();
        this.loading = new ObservableBoolean();
        this.showEmpty = new ObservableInt(View.GONE);
    }

    public MutableLiveData<List<CatBreed>> getCatBreeds() {
        return catBreeds;
    }

    public MutableLiveData<CatBreed> getSelected() {
        return selected;
    }

    public CatBreed getCatBreedAt(Integer index) {
        if (catBreeds.getValue() != null && index != null && catBreeds.getValue().size() > index) {
            return catBreeds.getValue().get(index);
        }
        return null;
    }

    public void setCatBreedsInAdapter(List<CatBreed> breeds) {
        this.adapter.setCatBreeds(breeds);
        this.adapter.notifyDataSetChanged();
    }

    public CatBreedsAdapter getAdapter() {
        return this.adapter;
    }

    public void fetchList() {
        this.loading.set(true);
        this.repo.fetchList();
    }

    public void fetchCatBreedImageAt(Integer index) {
        final CatBreed catBreed = getCatBreedAt(index);
        if (catBreed != null && !images.containsKey(catBreed.getId())) {
            this.repo.fetchImage(catBreed.getId(), new Repository.FetchImageCallback<CatBreedImage>() {
                @Override
                public void onLoaded(CatBreedImage data) {
                    if (data != null) {
                        String thumbnailUrl = data.getUrl();
                        images.put(catBreed.getId(), thumbnailUrl);
                    }
                }
            });
        }
    }

    public void onItemClick(Integer index) {
        selected.setValue(getCatBreedAt(index));
    }
}
