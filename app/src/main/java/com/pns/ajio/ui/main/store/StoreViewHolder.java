package com.pns.ajio.ui.main.store;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pns.ajio.R;
import com.pns.ajio.data.models.StoreProduct;

public class StoreViewHolder extends RecyclerView.ViewHolder {

    public ImageView mImgProduct;
    public YouTubePlayerView playerView;
    public MaterialButton buy;

    public StoreViewHolder(@NonNull View itemView) {
        super(itemView);

        mImgProduct = itemView.findViewById(R.id.product_image);
        playerView = itemView.findViewById(R.id.youtube_player_view);
        buy = itemView.findViewById(R.id.buy);
    }

    // Setting the data in item layout

    @SuppressLint("SetTextI18n")
    public void setData(StoreProduct model) {

        Glide.with(mImgProduct).
                load(model.getImgUrl()).
                into(mImgProduct);

        playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(model.getVideoId(), 0);
                youTubePlayer.pause();
            }
        });
    }
}