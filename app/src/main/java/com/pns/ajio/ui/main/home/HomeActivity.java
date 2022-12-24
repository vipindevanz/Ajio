package com.pns.ajio.ui.main.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.pns.ajio.data.models.Notification;
import com.pns.ajio.databinding.ActivityHomeBinding;
import com.pns.ajio.ui.account.AccountActivity;
import com.pns.ajio.ui.category.CategoryActivity;
import com.pns.ajio.ui.main.store.StoresFragment;
import com.pns.ajio.ui.wishlist.WishlistActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {

        binding.menuBottom.setOnItemSelectedListener(listener);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();


        binding.imgHamburger.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, CategoryActivity.class)));
        binding.imgAccount.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, AccountActivity.class)));
        binding.search.setOnClickListener(v -> Toast.makeText(this, "Functionality unavailable right now", Toast.LENGTH_SHORT).show());

        updateVisits();
        checkNotifications();
        isNetworkConnected();
    }

    private final OnItemSelectedListener listener = i -> {

        switch (i) {

            // Setting menu

            case 0:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
                break;

            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new StoresFragment())
                        .commit();
                break;

            case 2:
                startActivity(new Intent(HomeActivity.this, WishlistActivity.class));
                break;
        }

        return true;
    };

    private void checkNotifications() {

        SharedPreferences preferences = getSharedPreferences("PRE", MODE_PRIVATE);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    Notification notification = snapshot.getValue(Notification.class);

                    if (notification == null || !notification.isVisibility()) return;
                    if (!preferences.getString("noti", "").equals(notification.getKey())) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this).setCancelable(false);
                        AlertDialog dialog = builder.create();
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        View view = getLayoutInflater().inflate(R.layout.notification_layout, null);
                        ImageView imageView = view.findViewById(R.id.notification_image);
                        ImageView back = view.findViewById(R.id.notification_back);
                        TextView textView = view.findViewById(R.id.notification_btn);

                        Glide.with(imageView).load(notification.getImgUrl()).into(imageView);

                        if (notification.getText().isEmpty()) {
                            textView.setVisibility(View.GONE);
                        } else {
                            textView.setText(notification.getText());
                        }

                        imageView.setOnClickListener(v -> {

                            Map<String, Object> map = new HashMap<>();
                            map.put("clicks", notification.getClicks() + 1);
                            reference.updateChildren(map);

                            dialog.cancel();

                            if (notification.getContentUrl().isEmpty()) return;
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(notification.getContentUrl()));
                            startActivity(intent);
                        });


                        dialog.setView(view);

                        back.setOnClickListener(v -> dialog.cancel());

                        dialog.create();
                        dialog.show();

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("noti", notification.getKey());
                        editor.apply();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateVisits() {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        String date = formatter.format(d);

        SharedPreferences sharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE);

        if (!sharedPreferences.getString("date", "").equals(date)) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Visits").child("home")
                    .child(date.substring(6)).child(date.substring(3, 5)).child(date.substring(0, 2));

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        Integer visitors = snapshot.getValue(Integer.class);

                        if (visitors != null)
                            reference.setValue(visitors + 1);

                    } else {
                        reference.setValue(1);
                    }

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("date", date);
                    editor.apply();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottom, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnected()) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
}