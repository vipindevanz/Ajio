package com.pns.ajio.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pns.ajio.databinding.ActivityAdminBinding;
import com.pns.ajio.model.Book;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.Submit.setOnClickListener(v -> submit());
    }

    private void submit() {

        String title = mBinding.Title.getText().toString().trim();
        String desc = mBinding.Description.getText().toString().trim();
        String bookUrl = mBinding.BookUrl.getText().toString().trim();
        String category = mBinding.Category.getText().toString().trim();
        String imageUrl = mBinding.ImageUrl.getText().toString().trim();
        String length = mBinding.Length.getText().toString().trim();
        String rating = mBinding.Rating.getText().toString().trim();
        String writer = mBinding.Writer.getText().toString().trim();
        int purchased = 0;
        String language = "English";
        String key;

        if (title.isEmpty() || desc.isEmpty() || bookUrl.isEmpty() || category.isEmpty() ||
                imageUrl.isEmpty() || length.isEmpty() || rating.isEmpty() ||
                writer.isEmpty()) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Affiliate").child("books");

        key = reference.push().getKey();

        if (key == null) {
            Toast.makeText(this, "Key is null", Toast.LENGTH_SHORT).show();
            return;
        }

        Book book = new Book(imageUrl, bookUrl, title, desc, Double.parseDouble(rating), language, Integer.parseInt(length), writer, category, purchased, key);

        reference.child(key).setValue(book).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                mBinding.Title.getText().clear();
                mBinding.ImageUrl.getText().clear();
                mBinding.BookUrl.getText().clear();
                mBinding.Description.getText().clear();
                mBinding.Rating.getText().clear();
                mBinding.Length.getText().clear();
                mBinding.Writer.getText().clear();
                mBinding.Category.getText().clear();
                mBinding.Title.requestFocus();

            } else {
                Toast.makeText(AdminActivity.this, "failed to upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(AdminActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
    }
}