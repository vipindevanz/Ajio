package com.pns.ajio.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.R;
import com.pns.ajio.adapter.ProductAdapter;
import com.pns.ajio.databinding.ActivityProductBinding;
import com.pns.ajio.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding mBinding;
    private ProductAdapter mAdapter;
    private List<Product> mList;
    private String mCategory;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initializeData();
        loadAd();
    }

    private void loadAd() {
        MobileAds.initialize(this, initializationStatus -> {});
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-8964057723549049/7171227328", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.show(ProductActivity.this);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void initializeData() {

        mCategory = getIntent().getStringExtra("category");

        if (mCategory == null) {
            Toast.makeText(this, "Unable to display products", Toast.LENGTH_SHORT).show();
            return;
        }

        mBinding.category.setText(mCategory);
        mList = new ArrayList<>();
        mAdapter = new ProductAdapter(mList, this, mCategory);
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setAdapter(mAdapter);

        setProductList();

        mBinding.imgWishlist.setOnClickListener(v -> startActivity(new Intent(ProductActivity.this, WishlistActivity.class)));
        mBinding.imgHamburger.setOnClickListener(v -> startActivity(new Intent(ProductActivity.this, CategoryActivity.class)));
    }

    private void setProductList() {

        // Retrieving product details from firebase

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Affiliate").child(mCategory);
        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    mList.clear();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        Product model = snapshot1.getValue(Product.class);

                        mList.add(model);
                    }

                    mBinding.totalResults.setText(mList.size() + " results");
                    Collections.shuffle(mList);
                    mBinding.shimmerLayout.setVisibility(View.GONE);
                    mBinding.recyclerView.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(ProductActivity.this, "Products unavailable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ProductActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}