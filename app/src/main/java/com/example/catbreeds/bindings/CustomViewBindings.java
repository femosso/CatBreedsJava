package com.example.catbreeds.bindings;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.catbreeds.R;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewBindings {

    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView,
                                               RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("imageUrl")
    public static void bindUrlToCatBreedImageView(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            // avoid old images appearing briefly before it's replaced with the current image
            if (imageView.getTag(R.id.image_url) == null ||
                    !imageView.getTag(R.id.image_url).equals(imageUrl)) {
                imageView.setImageBitmap(null);
                imageView.setTag(R.id.image_url, imageUrl);
                Glide.with(imageView)
                        .load(imageUrl)
                        .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                        .into(imageView);
            }
        } else {
            imageView.setTag(R.id.image_url, null);
            imageView.setImageBitmap(null);
        }
    }

    @BindingAdapter("flagUrl")
    public static void bindUrlToFlagImageView(ImageView imageView, String countryCode) {
        if (!TextUtils.isEmpty(countryCode)) {
            Glide.with(imageView)
                    .load(String.format("https://www.countryflags.io/%s/shiny/64.png", countryCode))
                    .into(imageView);
        }
    }

    @BindingAdapter("setVisibility")
    public static void bindVisibilityToTextView(TextView textView, Boolean value) {
        textView.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}