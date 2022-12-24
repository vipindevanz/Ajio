package com.pns.ajio.ui.category;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pns.ajio.R;
import com.pns.ajio.data.models.Category;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public ImageView categoryImg;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryImg = itemView.findViewById(R.id.product_image);
    }

    // Setting the data in item layout

    @SuppressLint("SetTextI18n")
    public void setData(Category model) {

        Glide.with(categoryImg).
                load(model.getImgUrl()).
                into(categoryImg);
    }
}