package com.example.catbreeds.ui.breeds;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.catbreeds.BR;
import com.example.catbreeds.data.model.CatBreed;
import com.example.catbreeds.ui.viewmodel.CatBreedsViewModel;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class CatBreedsAdapter extends RecyclerView.Adapter<CatBreedViewHolder> {

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
    public CatBreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new CatBreedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CatBreedViewHolder holder, int position) {
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

}

class CatBreedViewHolder extends RecyclerView.ViewHolder {
    final ViewDataBinding binding;

    CatBreedViewHolder(ViewDataBinding binding) {
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