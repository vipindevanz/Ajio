package com.pns.ajio.ui.category;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pns.ajio.data.models.Category;
import com.pns.ajio.databinding.ActivityCategoryBinding;
import com.pns.ajio.viewmodels.CategoryViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private List<Category> categoryList;
    private CategoryAdapter adapter;
    private ActivityCategoryBinding binding;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        setAdapter();

        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryListData().observe(this, categories -> {

            if (!categories.isEmpty()) {
                binding.shimmerLayout.setVisibility(View.GONE);
            }

            categoryList.addAll(categories);
            adapter.notifyDataSetChanged();
        });
    }

    private void initViews() {

        binding.ibBack.setOnClickListener(v -> finish());
        binding.icBack.setOnClickListener(v -> finish());

        setAdapter();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setAdapter() {

        categoryList = new ArrayList<>();

        Collections.shuffle(categoryList);
        adapter = new CategoryAdapter(categoryList, this);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}