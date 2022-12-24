package com.pns.ajio.ui.product;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.pns.ajio.R;
import com.pns.ajio.data.models.Product;

import kr.co.prnd.readmore.ReadMoreTextView;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public ImageView mImgProduct, mImgWishList;
    public TextView mTvProductName;
    public ReadMoreTextView mTvProductDesc;
    public MaterialButton rating, brand, buy;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        mImgProduct = itemView.findViewById(R.id.product_image);
        mTvProductDesc = itemView.findViewById(R.id.desc);
        mTvProductName = itemView.findViewById(R.id.product_name);
        rating = itemView.findViewById(R.id.rating);
        mImgWishList = itemView.findViewById(R.id.wishlist);
        brand = itemView.findViewById(R.id.brand);
        buy = itemView.findViewById(R.id.buy);

    }

    // Setting the data in item layout

    @SuppressLint("SetTextI18n")
    public void setData(Product model) {

        Glide.with(mImgProduct).
                load(model.getImgUrl()).
                into(mImgProduct);
        mTvProductName.setText(model.getName());
        mTvProductDesc.setText(model.getDesc());
        brand.setText(model.getBrand());
        rating.setText(model.getRating() + " ⭐");
    }
}