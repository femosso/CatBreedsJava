package com.example.catbreeds.ui.breeds;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.catbreeds.BR;
import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.viewmodel.CatBreedsViewModel;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class CatBreedsAdapter extends RecyclerView.Adapter<CatBreedsAdapter.GenericViewHolder> {

    private int layoutId;
    private List<CatBreed> breeds;
    private CatBreedsViewModel viewModel;

    public CatBreedsAdapter(@LayoutRes int layoutId, CatBreedsViewModel viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    @Override
    public int getItemCount() {
        return breeds == null ? 0 : breeds.size();
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    public void setCatBreeds(List<CatBreed> breeds) {
        this.breeds = breeds;
    }

    static class GenericViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CatBreedsViewModel viewModel, Integer position) {
            viewModel.fetchCatBreedImageAt(position);

            this.binding.setVariable(BR.position, position);
            this.binding.setVariable(BR.viewModel, viewModel);
            this.binding.executePendingBindings();
        }
    }
}