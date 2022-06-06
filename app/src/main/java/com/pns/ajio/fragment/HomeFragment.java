package com.pns.ajio.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.activity.ProductActivity;
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

        initViews();
    }

    private void initViews() {

        getSlideImages();
        setList();
        mBinding.viewFlipper.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProductActivity.class);
            intent.putExtra("category", "Smartphones");
            requireContext().startActivity(intent);
        });
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
//                    mBinding.viewFlipper.setVisibility(View.VISIBLE); Scrolling issue with recyclerview
//                    mBinding.notice.setVisibility(View.VISIBLE); Scrolling issue with recyclerview
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

    private void getSlideImages() {

        List<String> list = new ArrayList<>();
        list.add("https://i.ytimg.com/vi/aeQnQ-vjtuY/maxresdefault.jpg");
        list.add("https://gadgets-africa.com/wp-content/uploads/2020/05/1_BGkB9VOJrhNPGuJDia8vgQ.jpeg");
        list.add("https://miro.medium.com/max/1400/1*goGPwn50r5CuNC_dlXnU9A.jpeg");
        list.add("https://i.ytimg.com/vi/kbeEkwlTeqQ/maxresdefault.jpg");
        list.add("https://images.ctfassets.net/wcfotm6rrl7u/2sDJE99xaUTEDxrkiopmtK/be3cba35562ec25a738ac957d93d7964/april_8th_launch-og_image.jpg");
        list.add("https://media.gq-magazine.co.uk/photos/62456f804a9bdf194462386f/master/pass/30032022_PHONES_HP1.jpg");

        for (String img : list) {
            showSlideImages(img);
        }
    }

    private void showSlideImages(String img) {

        // Sliding the images one by one in infinite loop

        ImageView imageView = new ImageView(getContext());
        Glide.with(imageView).load(img).into(imageView);

        mBinding.viewFlipper.addView(imageView);
        mBinding.viewFlipper.setFlipInterval(3000);
        mBinding.viewFlipper.setAutoStart(true);

        mBinding.viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        mBinding.viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }
}