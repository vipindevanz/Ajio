package com.pns.ajio.ui.category;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.databinding.ActivityCategoryBinding;
import com.pns.ajio.data.models.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private List<Category> categoryList;
    private CategoryAdapter adapter;
    private ActivityCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews(){

        binding.ibBack.setOnClickListener(v -> {
            finish();
        });
        binding.icBack.setOnClickListener(v -> {
            finish();
        });

        buildList();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setAdapter() {

        if (!categoryList.isEmpty()) {
            binding.shimmerLayout.setVisibility(View.GONE);
        }
        Collections.shuffle(categoryList);
        adapter = new CategoryAdapter(categoryList, this);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void buildList() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Category");
        reference.keepSynced(true);

        categoryList = new ArrayList<>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    categoryList.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                        Category category = dataSnapshot.getValue(Category.class);

                        categoryList.add(category);
                    }
                    setAdapter();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CategoryActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}