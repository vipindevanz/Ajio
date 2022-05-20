package com.pns.ajio.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.Utils;
import com.pns.ajio.R;
import com.pns.ajio.databinding.ActivityHomeBinding;
import com.pns.ajio.fragment.BottomSheetFragment;
import com.pns.ajio.fragment.HomeFragment;
import com.pns.ajio.fragment.StoresFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.menuBottom.setOnNavigationItemSelectedListener(this);
        binding.menuBottom.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        binding.imgHamburger.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NavigationActivity.class)));
        binding.imgAffiliate.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, BooksActivity.class)));
        binding.imgDrop.setOnClickListener(v -> openBottomSheetDialog());
        Glide.with(binding.imgAffiliate).load(R.drawable.spinning_circle).into(binding.imgAffiliate);
        updateVisits();
    }

    private void updateVisits() {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        String date = formatter.format(d);

        SharedPreferences sharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE);

        if (!sharedPreferences.getString("date", "").equals(date)){

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Visits").child("home")
                    .child(date.substring(6)).child(date.substring(3, 5)).child(date.substring(0, 2));

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                Integer visitors = snapshot.getValue(Integer.class);

                                reference.setValue(visitors+1);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            // Setting menu

            case R.id.menu_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
                break;

            case R.id.menu_stores:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new StoresFragment())
                        .commit();
                break;

            case R.id.menu_account:
                startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                break;

            case R.id.menu_wishlist:

                SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
                boolean loggedInAlready = preferences.getBoolean("loggedIn", false);

                if (loggedInAlready) {
                    startActivity(new Intent(HomeActivity.this, WishlistActivity.class));
                } else {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }

                break;

            case R.id.menu_bag:
                startActivity(new Intent(HomeActivity.this, BagActivity.class));
                break;

            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    private void openBottomSheetDialog() {

        BottomSheetFragment fragment = new BottomSheetFragment();
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    public void onProductClick(View v) {

        startActivity(new Intent(HomeActivity.this, ProductActivity.class));
    }
}