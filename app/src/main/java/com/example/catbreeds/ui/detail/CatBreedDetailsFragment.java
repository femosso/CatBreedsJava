package com.example.catbreeds.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catbreeds.R;
import com.example.catbreeds.databinding.FragmentCatBreedDetailsBinding;
import com.example.catbreeds.ui.viewmodel.CatBreedsViewModel;
import com.example.catbreeds.ui.viewmodel.CatBreedsViewModelFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.AndroidSupportInjection;

public class CatBreedDetailsFragment extends Fragment {

    private CatBreedsViewModel viewModel;

    private FragmentCatBreedDetailsBinding binding;

    @Inject
    CatBreedsViewModelFactory providerFactory;

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_cat_breed_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), providerFactory)
                .get(CatBreedsViewModel.class);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel = null;
        binding = null;
    }
}