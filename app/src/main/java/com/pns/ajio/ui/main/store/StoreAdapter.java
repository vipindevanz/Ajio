package com.pns.ajio.ui.main.store;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pns.ajio.R;
import com.pns.ajio.data.models.StoreProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreAdapter extends RecyclerView.Adapter<StoreViewHolder> {

    private final List<StoreProduct> mList;
    private final Context mContext;

    public StoreAdapter(List<StoreProduct> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        Inflating the item layout and passing to view holder
        */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item_layout, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {

        StoreProduct product = mList.get(position);
        holder.setData(product);

        holder.buy.setOnClickListener(v -> openLink(product));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void openLink(StoreProduct product) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Store")
                .child(product.getKey());
        Map<String, Object> map = new HashMap<>();
        map.put("purchased", product.getPurchased() + 1);
        reference.updateChildren(map);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(product.getProductUrl()));
        mContext.startActivity(intent);
    }

}