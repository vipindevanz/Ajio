package com.pns.ajio.ui.main.store;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.databinding.FragmentStoresBinding;
import com.pns.ajio.data.models.StoreProduct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoresFragment extends Fragment {

    private FragmentStoresBinding binding;
    private List<StoreProduct> list;
    private StoreAdapter adapter;

    public StoresFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStoresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    private void initViews(){

        setList();
    }
    private void setList() {

        list = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Store");
        reference.keepSynced(true);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    list.clear();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        StoreProduct product = snapshot1.getValue(StoreProduct.class);
                        list.add(product);
                    }

                    Collections.shuffle(list);
                    adapter = new StoreAdapter(list, getContext());
                    binding.recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    binding.shimmerLayout.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);

                } else {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "Products unavailable", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}