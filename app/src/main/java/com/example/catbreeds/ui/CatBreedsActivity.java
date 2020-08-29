package com.example.catbreeds.ui;

import android.os.Bundle;
import android.view.View;

import com.example.catbreeds.R;
import com.example.catbreeds.databinding.ActivityCatBreedsBinding;
import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.viewmodel.CatBreedsViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class CatBreedsActivity extends AppCompatActivity {

    private CatBreedsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_breeds);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initDataBinding();
        initObservables();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    private void initDataBinding() {
        // add data binding in this activity to set up the adapter
        ActivityCatBreedsBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_cat_breeds);
        viewModel = new ViewModelProvider(this).get(CatBreedsViewModel.class);
        viewModel.init();
        activityBinding.setModel(viewModel);
    }

    private void initObservables() {
        viewModel.getCatBreeds().observe(this, new Observer<List<CatBreed>>() {
            @Override
            public void onChanged(List<CatBreed> catBreeds) {
                viewModel.loading.set(View.GONE);
                if (catBreeds.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                } else {
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setCatBreedsInAdapter(catBreeds);
                }
            }
        });

        viewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                // if boolean -> show progress bar, else hide it
            }
        });
    }

    private void loadList() {
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchList();
    }
}