package com.pns.ajio.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.adapter.BookAdapter;
import com.pns.ajio.databinding.ActivityBooksBinding;
import com.pns.ajio.model.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    private BookAdapter adapter;
    private List<Book> mList;
    private ActivityBooksBinding binding;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBooksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
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
                        mInterstitialAd.show(BooksActivity.this);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }

    private void initViews(){

        setList();

        adapter = new BookAdapter(mList, this);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        binding.imgHamburger.setOnClickListener(v -> startActivity(new Intent(this, CategoryActivity.class)));
    }

    private void setList() {

        mList = new ArrayList<>();
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("Affiliate").child("books");
        mDatabaseReference.keepSynced(true);

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    mList.clear();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        Book model = snapshot1.getValue(Book.class);

                        mList.add(model);
                    }

                    Collections.shuffle(mList);

                    binding.shimmerLayout.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.totalResults.setText(mList.size() + " results");
                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(BooksActivity.this, "Products unavailable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(BooksActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}