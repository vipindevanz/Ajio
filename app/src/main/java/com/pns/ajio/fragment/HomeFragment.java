package com.pns.ajio.fragment;

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
import com.pns.ajio.adapter.HomeAdapter;
import com.pns.ajio.databinding.FragmentHomeBinding;
import com.pns.ajio.model.HomeProduct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<HomeProduct> list;
    private HomeAdapter adapter;
    private FragmentHomeBinding mBinding;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setList();
    }

    private void setList() {

        list = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Home");
        reference.keepSynced(true);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    list.clear();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        HomeProduct product = snapshot1.getValue(HomeProduct.class);
                        list.add(product);
                    }

                    Collections.shuffle(list);
                    adapter = new HomeAdapter(list, getContext());
                    mBinding.recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mBinding.shimmerLayout.setVisibility(View.GONE);
                    mBinding.recyclerView.setVisibility(View.VISIBLE);

                } else {
                    if (getContext() != null)
                        Toast.makeText(getContext(), "Products unavailable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}