package com.pns.ajio.ui.main.spotlight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pns.ajio.R;
import com.pns.ajio.data.models.SpotlightModel;

import java.util.List;

public class SpotlightAdapter extends RecyclerView.Adapter<SpotlightViewHolder> {

    private final List<SpotlightModel> list;

    public SpotlightAdapter(List<SpotlightModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SpotlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spotlight_row_item, parent, false);
        return new SpotlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpotlightViewHolder holder, int position) {

        SpotlightModel spotlightModel = list.get(position);
        holder.setData(spotlightModel);

        holder.descTexts.setVisibility(View.INVISIBLE);
        holder.progressBar.setVisibility(View.VISIBLE);

        holder.descTexts.setOnClickListener(v -> {
            if (holder.scrollView.getVisibility() == View.VISIBLE)
                holder.scrollView.setVisibility(View.INVISIBLE);
            else
                holder.scrollView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}