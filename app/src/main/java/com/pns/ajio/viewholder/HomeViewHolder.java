package com.pns.ajio.viewholder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pns.ajio.R;
import com.pns.ajio.model.HomeProduct;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    public ImageView mImgProduct;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);

        mImgProduct = itemView.findViewById(R.id.product_image);
    }

    // Setting the data in item layout

    @SuppressLint("SetTextI18n")
    public void setData(HomeProduct model) {

        Glide.with(mImgProduct).
                load(model.getImgUrl()).
                into(mImgProduct);
    }
}