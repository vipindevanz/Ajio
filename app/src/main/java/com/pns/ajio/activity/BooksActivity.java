package com.pns.ajio.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import com.plattysoft.leonids.ParticleSystem;
import com.pns.ajio.R;
import com.pns.ajio.adapter.BookAdapter;
import com.pns.ajio.databinding.ActivityBooksBinding;
import com.pns.ajio.model.Book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    private BookAdapter adapter;
    private List<Book> mList;
    private ActivityBooksBinding binding;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBooksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setList();
        updateVisits();

        adapter = new BookAdapter(mList, this);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }

    private void setList() {

        mList = new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Affiliate").child("books");

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    mList.clear();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        Book model = snapshot1.getValue(Book.class);

                        mList.add(model);
                    }

                    new ParticleSystem(BooksActivity.this, 30, R.drawable.sparkle, 8000)
                            .setSpeedRange(0.1f, 1.0f)
                            .oneShot(findViewById(R.id.sparkle), 30);

                    Collections.shuffle(mList);

                    binding.pg.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.results.setText(mList.size() + " results");
                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(BooksActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(BooksActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateVisits() {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        String date = formatter.format(d);

        SharedPreferences sharedPreferences = getSharedPreferences("PREFS2", MODE_PRIVATE);

        if (!sharedPreferences.getString("date", "").equals(date)) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Visits").child("books")
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
}