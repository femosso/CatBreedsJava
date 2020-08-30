package com.example.catbreeds.ui.breeds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catbreeds.R;
import com.example.catbreeds.databinding.FragmentCatBreedsBinding;
import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.viewmodel.CatBreedsViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

public class CatBreedsFragment extends Fragment {

    private CatBreedsViewModel viewModel;

    private FragmentCatBreedsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_cat_breeds, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CatBreedsViewModel.class);
        viewModel.resetSelected();
        binding.setViewModel(viewModel);
        initObservables();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel = null;
        binding = null;
    }

    public void refreshList() {
        viewModel.showEmpty.set(View.GONE);
        viewModel.fetchList(false);
    }

    private void initObservables() {
        viewModel.getCatBreeds().observe(getViewLifecycleOwner(), new Observer<List<CatBreed>>() {
            @Override
            public void onChanged(List<CatBreed> catBreeds) {
                viewModel.loading.set(false);
                if (catBreeds == null || catBreeds.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                    viewModel.clearCatBreedsInAdapter();
                } else {
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setCatBreedsInAdapter(catBreeds);
                }
            }
        });

        viewModel.getSelected().observe(getViewLifecycleOwner(), new Observer<CatBreed>() {
            @Override
            public void onChanged(CatBreed catBreed) {
                NavDirections navDir =
                        CatBreedsFragmentDirections.actionFirstFragmentToSecondFragment();
                NavHostFragment.findNavController(CatBreedsFragment.this).navigate(navDir);
            }
        });
    }
}