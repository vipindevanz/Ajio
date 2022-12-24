package com.pns.ajio.ui.category;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pns.ajio.R;
import com.pns.ajio.ui.book.BooksActivity;
import com.pns.ajio.ui.product.ProductActivity;
import com.pns.ajio.data.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private final List<Category> mList;
    private final Context mContext;

    public CategoryAdapter(List<Category> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.setData(mList.get(position));

        holder.categoryImg.setOnClickListener(v -> openProduct(mList.get(position)));
    }

    private void openProduct(Category category) {

        if (category.getCategory().equalsIgnoreCase("books")){
            mContext.startActivity(new Intent(mContext, BooksActivity.class));
            return;
        }

        Intent intent = new Intent(mContext, ProductActivity.class);
        intent.putExtra("category", category.getCategory());
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}