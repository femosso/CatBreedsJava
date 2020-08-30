package com.example.catbreeds.bindings;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.catbreeds.R;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// TODO - check if this is the best place to add this file
public class CustomViewBindings {

    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("imageUrl")
    public static void bindUrlToCatBreedImageView(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            // If we don't do this, you'll see the old image appear briefly
            // before it's replaced with the current image
            if (imageView.getTag(R.id.image_url) == null || !imageView.getTag(R.id.image_url).equals(imageUrl)) {
                imageView.setImageBitmap(null);
                imageView.setTag(R.id.image_url, imageUrl);
                // TODO - add a placeholder using RequestOptions from glide also see how to use diskCacheStrategy
                // https://stackoverflow.com/questions/56025326/how-to-reuse-a-downloaded-image-in-another-activity
                Glide.with(imageView).load(imageUrl).into(imageView);
            }
        } else {
            imageView.setTag(R.id.image_url, null);
            imageView.setImageBitmap(null);
        }
    }

    @BindingAdapter("flagUrl")
    public static void bindUrlToFlagImageView(ImageView imageView, String countryCode) {
        String url = String.format("https://www.countryflags.io/%s/shiny/64.png", countryCode);
        Glide.with(imageView).load(url).into(imageView);
    }
}