package com.example.catbreeds.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        refreshList();
    }

    private void initDataBinding() {
        // add data binding in this activity to set up the adapter
        ActivityCatBreedsBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_cat_breeds);
        viewModel = new ViewModelProvider(this).get(CatBreedsViewModel.class);
        viewModel.init();
        activityBinding.setViewModel(viewModel);
    }

    private void initObservables() {
        viewModel.getCatBreeds().observe(this, new Observer<List<CatBreed>>() {
            @Override
            public void onChanged(List<CatBreed> catBreeds) {
                viewModel.loading.set(false);
                if (catBreeds == null || catBreeds.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                } else {
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setCatBreedsInAdapter(catBreeds);
                }
            }
        });

        viewModel.getSelected().observe(this, new Observer<CatBreed>() {
            @Override
            public void onChanged(CatBreed catBreed) {
                Toast.makeText(CatBreedsActivity.this, "Selected: " + catBreed.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshList() {
        viewModel.showEmpty.set(View.GONE);
        viewModel.fetchList();
    }
}