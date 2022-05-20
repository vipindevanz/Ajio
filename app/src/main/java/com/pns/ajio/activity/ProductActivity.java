package com.pns.ajio.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.R;
import com.pns.ajio.adapter.ProductAdapter;
import com.pns.ajio.databinding.ActivityProductBinding;
import com.pns.ajio.model.ProductModel;
import com.razorpay.Checkout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding mBinding;
    private ProductAdapter mAdapter;
    private List<ProductModel> mList;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Checkout.preload(getApplicationContext());

        initializeData();
        openDialog();
        addData();
    }

    private void openDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setCancelable(false);
        builder.setMessage(getResources().getString(R.string.product_ins));
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {});
        builder.create();
        builder.show();
    }

    private void initializeData() {

        mList = new ArrayList<>();
        mAdapter = new ProductAdapter(mList, this);
        mBinding.recyclerView.setAdapter(mAdapter);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("ProductDetails");

        Glide.with(mBinding.imgAffiliate).load(R.drawable.spinning_circle).into(mBinding.imgAffiliate);
        mBinding.imgAffiliate.setOnClickListener(v -> startActivity(new Intent(ProductActivity.this, BooksActivity.class)));
        mBinding.imgBag.setOnClickListener(v -> startActivity(new Intent(ProductActivity.this, BagActivity.class)));
        mBinding.imgFavourite.setOnClickListener(v -> {
            startActivity(new Intent(ProductActivity.this, WishlistActivity.class));
            finish();
        });
        mBinding.imgSearch.setOnClickListener(v -> startActivity(new Intent(ProductActivity.this, HomeActivity.class)));
        mBinding.imgHamburger.setOnClickListener(v -> startActivity(new Intent(ProductActivity.this, NavigationActivity.class)));
    }

    private void addData() {

        // Retrieving product details from firebase

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    mList.clear();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        ProductModel model = snapshot1.getValue(ProductModel.class);

                        mList.add(model);
                    }

                    Collections.shuffle(mList);
                    adjustView();
                    mAdapter.notifyDataSetChanged();

                } else {

                    adjustView();

                    Toast.makeText(ProductActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                adjustView();

                Toast.makeText(ProductActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adjustView() {

        mBinding.shimmerLayout.setVisibility(View.GONE);
        mBinding.tvPrice.setVisibility(View.VISIBLE);
        mBinding.tvProducts.setVisibility(View.VISIBLE);
        mBinding.recyclerView.setVisibility(View.VISIBLE);
        mBinding.bottomLayout.setVisibility(View.VISIBLE);
    }

}