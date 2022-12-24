package com.pns.ajio.ui.main.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pns.ajio.R;
import com.pns.ajio.ui.book.BooksActivity;
import com.pns.ajio.ui.product.ProductActivity;
import com.pns.ajio.data.models.HomeProduct;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private final List<HomeProduct> mList;
    private final Context mContext;


    public HomeAdapter(List<HomeProduct> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        Inflating the item layout and passing to view holder
        */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_layout, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        HomeProduct product = mList.get(position);
        holder.setData(product);

        holder.mImgProduct.setOnClickListener(v -> {

            if (product.getProductUrl().isEmpty()) {

                if (product.getCategory().equalsIgnoreCase("books")) {
                    mContext.startActivity(new Intent(mContext, BooksActivity.class));
                    return;
                }

                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra("category", product.getCategory());
                mContext.startActivity(intent);

            } else {
                openLink(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void openLink(HomeProduct product) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(product.getProductUrl()));
        mContext.startActivity(intent);
    }
}