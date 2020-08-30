package com.example.catbreeds.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catbreeds.R;
import com.example.catbreeds.databinding.FragmentCatBreedDetailsBinding;
import com.example.catbreeds.viewmodel.CatBreedsViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class CatBreedDetailsFragment extends Fragment {

    private CatBreedsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(CatBreedsViewModel.class);
        FragmentCatBreedDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cat_breed_details, container, false);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }
}