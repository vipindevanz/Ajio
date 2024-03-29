package com.pns.ajio.ui.onboarding;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.databinding.ActivitySplashScreenBinding;
import com.pns.ajio.ui.main.home.HomeActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        checkStatus();
    }

    private void checkStatus() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnected()) {
            openHomeActivity();
            return;
        }

        FirebaseDatabase.getInstance().getReference("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    try {
                        if (Boolean.TRUE.equals(snapshot.getValue(Boolean.class))) {
                            openHomeActivity();
                        } else {
                            mBinding.ins.setVisibility(View.VISIBLE);
                        }
                    } catch (NullPointerException e) {
                        openHomeActivity();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openHomeActivity() {

        // Switching to Home screen after 2 seconds

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
            finish();
        }, 2000);
    }
}