package com.pns.ajio.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.adapter.SpotlightAdapter;
import com.pns.ajio.databinding.FragmentSpotLightBinding;
import com.pns.ajio.model.SpotlightModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpotLightFragment extends Fragment {

    private FragmentSpotLightBinding mBinding;
    private List<SpotlightModel> list;
    private DatabaseReference reference;

    public SpotLightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSpotLightBinding.inflate(inflater, container, false);
        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Videos");
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buildList();
    }

    private void buildList() {

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    list.clear();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        SpotlightModel videos = snapshot1.getValue(SpotlightModel.class);
                        list.add(videos);
                    }
                    Collections.shuffle(list);
                    mBinding.viewPager.setAdapter(new SpotlightAdapter(list));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
