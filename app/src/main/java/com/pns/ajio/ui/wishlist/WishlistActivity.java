package com.pns.ajio.ui.wishlist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.ui.product.ProductAdapter;
import com.pns.ajio.databinding.ActivityWishlistBinding;
import com.pns.ajio.data.models.Product;
import com.pns.ajio.data.models.WishList;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private List<Product> mList;
    private ProductAdapter mAdapter;
    private ActivityWishlistBinding mBinding;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initDataAndView();
    }

    private void initDataAndView() {

        mBinding.btnContinueShopping.setOnClickListener(v -> finish());
        mList = new ArrayList<>();
        mAdapter = new ProductAdapter(mList, this, "Wishlist Products");
        mBinding.recyclerView.setAdapter(mAdapter);
        fetchData();
    }

    private void fetchData() {

        if (user == null) return;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Affiliate");
        reference.keepSynced(true);

        FirebaseDatabase.getInstance().getReference("Wishlist").child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                WishList wishList = snapshot1.getValue(WishList.class);

                                if (wishList == null) return;

                                reference.child(wishList.getCategory()).child(wishList.getKey())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                if (snapshot.exists()) {

                                                    Product product = snapshot.getValue(Product.class);

                                                    mList.add(product);

                                                    mAdapter.notifyDataSetChanged();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(WishlistActivity.this, error.getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                            mBinding.layoutRelative.setVisibility(View.GONE);
                            mBinding.recyclerView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(WishlistActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}