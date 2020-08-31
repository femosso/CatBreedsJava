package com.example.catbreeds.viewmodel;

import android.view.View;

import com.example.catbreeds.R;
import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.models.CatBreedImage;
import com.example.catbreeds.repository.Repository;
import com.example.catbreeds.ui.breeds.CatBreedsAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CatBreedsViewModel extends ViewModel {

    private CatBreedsAdapter adapter;

    private Repository repository;

    private MutableLiveData<List<CatBreed>> catBreeds;
    private MutableLiveData<CatBreed> selected;

    public ObservableArrayMap<String, String> images;
    public ObservableBoolean loading;
    public ObservableInt showEmpty;

    @Inject
    public CatBreedsViewModel(Repository repository) {
        this.repository = repository;
        this.adapter = new CatBreedsAdapter(R.layout.view_cat_breed, this);
        this.catBreeds = new MutableLiveData<>();
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

    public void resetSelected() {
        selected = new MutableLiveData<>();
    }

    public CatBreed getCatBreedAt(Integer index) {
        if (catBreeds.getValue() != null && index != null && catBreeds.getValue().size() > index) {
            return catBreeds.getValue().get(index);
        }
        return null;
    }

    public void setCatBreedsInAdapter(List<CatBreed> breeds) {
        adapter.setCatBreeds(breeds);
        adapter.notifyDataSetChanged();
    }

    public void clearCatBreedsInAdapter() {
        adapter.setCatBreeds(new ArrayList<CatBreed>());
        adapter.notifyDataSetChanged();
    }

    public CatBreedsAdapter getAdapter() {
        return adapter;
    }

    public void fetchList(boolean force) {
        if (force) {
            invalidateImageCache();
        }

        loading.set(true);
        repository.fetchList(new Repository.FetchDataCallback<List<CatBreed>>() {
            @Override
            public void onLoaded(List<CatBreed> data) {
                if (data != null) {
                    catBreeds.setValue(data);
                }
            }
        });
    }

    public void fetchCatBreedImageAt(Integer index) {
        final CatBreed catBreed = getCatBreedAt(index);
        if (catBreed != null && !images.containsKey(catBreed.getId())) {
            repository.fetchImage(catBreed.getId(),
                    new Repository.FetchDataCallback<CatBreedImage>() {
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

    private void invalidateImageCache() {
        images = new ObservableArrayMap<>();
    }
}
