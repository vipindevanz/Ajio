package com.pns.ajio.ui.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pns.ajio.databinding.ActivityAdminBinding;
import com.pns.ajio.ui.book.BooksActivity;
import com.pns.ajio.ui.category.CategoryActivity;
import com.pns.ajio.ui.main.home.HomeActivity;
import com.pns.ajio.data.models.Book;
import com.pns.ajio.data.models.Category;
import com.pns.ajio.data.models.HomeProduct;
import com.pns.ajio.data.models.Product;
import com.pns.ajio.data.models.StoreProduct;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initViews();
    }

    private void initViews(){

        mBinding.addBooks.setOnClickListener(v->{
            mBinding.addCategory.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addHome.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addStore.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addProducts.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addBooks.setBackgroundColor(Color.parseColor("#FFFF0000"));

            mBinding.category.setVisibility(View.GONE);
            mBinding.home.setVisibility(View.GONE);
            mBinding.store.setVisibility(View.GONE);
            mBinding.products.setVisibility(View.GONE);
            mBinding.books.setVisibility(View.VISIBLE);
        });

        mBinding.addCategory.setOnClickListener(v->{
            mBinding.addBooks.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addHome.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addStore.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addProducts.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addCategory.setBackgroundColor(Color.parseColor("#FFFF0000"));

            mBinding.books.setVisibility(View.GONE);
            mBinding.home.setVisibility(View.GONE);
            mBinding.store.setVisibility(View.GONE);
            mBinding.products.setVisibility(View.GONE);
            mBinding.category.setVisibility(View.VISIBLE);
        });

        mBinding.addHome.setOnClickListener(v->{
            mBinding.addBooks.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addCategory.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addStore.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addProducts.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addHome.setBackgroundColor(Color.parseColor("#FFFF0000"));

            mBinding.books.setVisibility(View.GONE);
            mBinding.category.setVisibility(View.GONE);
            mBinding.store.setVisibility(View.GONE);
            mBinding.products.setVisibility(View.GONE);
            mBinding.home.setVisibility(View.VISIBLE);
        });

        mBinding.addStore.setOnClickListener(v->{
            mBinding.addBooks.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addCategory.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addHome.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addProducts.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addStore.setBackgroundColor(Color.parseColor("#FFFF0000"));

            mBinding.books.setVisibility(View.GONE);
            mBinding.category.setVisibility(View.GONE);
            mBinding.home.setVisibility(View.GONE);
            mBinding.products.setVisibility(View.GONE);
            mBinding.store.setVisibility(View.VISIBLE);
        });

        mBinding.addProducts.setOnClickListener(v->{
            mBinding.addBooks.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addCategory.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addHome.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addStore.setBackgroundColor(Color.parseColor("#FF3333FF"));
            mBinding.addProducts.setBackgroundColor(Color.parseColor("#FFFF0000"));

            mBinding.books.setVisibility(View.GONE);
            mBinding.category.setVisibility(View.GONE);
            mBinding.home.setVisibility(View.GONE);
            mBinding.store.setVisibility(View.GONE);
            mBinding.products.setVisibility(View.VISIBLE);
        });

        mBinding.bookSubmit.setOnClickListener(v-> {
            bookSubmit();
            mBinding.bookSubmit.setEnabled(false);
        });
        mBinding.categorySubmit.setOnClickListener(v-> {
            categorySubmit();
            mBinding.categorySubmit.setEnabled(false);
        });
        mBinding.homeSubmit.setOnClickListener(v-> {
            homeSubmit();
            mBinding.homeSubmit.setEnabled(false);
        });
        mBinding.storeSubmit.setOnClickListener(v-> {
            storeSubmit();
            mBinding.storeSubmit.setEnabled(false);
        });
        mBinding.productsSubmit.setOnClickListener(v-> {
            productSubmit();
            mBinding.productsSubmit.setEnabled(false);
        });

        mBinding.addHome.setOnLongClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, HomeActivity.class));
            return true;
        });
        mBinding.addProducts.setOnLongClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, HomeActivity.class));
            return true;
        });
        mBinding.addStore.setOnLongClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, HomeActivity.class);
            intent.putExtra("store", true);
            startActivity(intent);
            return true;
        });
        mBinding.addCategory.setOnLongClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, CategoryActivity.class));
            return true;
        });
        mBinding.addBooks.setOnLongClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, BooksActivity.class));
            return true;
        });
    }

    private void productSubmit() {

        String name = mBinding.productsName.getText().toString().trim();
        String desc = mBinding.productsDescription.getText().toString().trim();
        String productUrl = mBinding.productsUrl.getText().toString().trim();
        String category = mBinding.productsCategory.getText().toString().trim();
        String imageUrl = mBinding.productsImageUrl.getText().toString().trim();
        String rating = mBinding.productsRating.getText().toString().trim();
        String brand = mBinding.productsBrand.getText().toString().trim();
        int purchased = 0;
        String key;

        if (name.isEmpty() || desc.isEmpty() || productUrl.isEmpty() || category.isEmpty() ||
                imageUrl.isEmpty() || brand.isEmpty() || rating.isEmpty()) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Affiliate").child(category);

        key = reference.push().getKey();

        if (key == null) {
            Toast.makeText(this, "Key is null", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product(imageUrl, productUrl, name, desc, Double.parseDouble(rating), brand, purchased, key, category);

        reference.child(key).setValue(product).addOnCompleteListener(task -> {

            mBinding.productsSubmit.setEnabled(true);

            if (task.isSuccessful()) {

                mBinding.productsName.getText().clear();
                mBinding.productsDescription.getText().clear();
                mBinding.productsUrl.getText().clear();
                mBinding.productsImageUrl.getText().clear();
                mBinding.productsRating.getText().clear();
                mBinding.productsBrand.getText().clear();
                mBinding.productsName.requestFocus();

            } else {
                Toast.makeText(AdminActivity.this, "failed to upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(AdminActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
    }

    private void storeSubmit() {

        String videoId = mBinding.storeVideoId.getText().toString().trim();
        String imageUrl = mBinding.storeImageUrl.getText().toString().trim();
        String productUrl = mBinding.storeProductUrl.getText().toString().trim();

        if (videoId.isEmpty() || imageUrl.isEmpty() || productUrl.isEmpty()) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Store");

        String key = reference.push().getKey();

        if (key == null) {
            Toast.makeText(this, "Key is null", Toast.LENGTH_SHORT).show();
            return;
        }

        StoreProduct product = new StoreProduct(videoId, imageUrl, productUrl, key, 0);

        reference.child(key).setValue(product).addOnCompleteListener(task -> {

            mBinding.storeSubmit.setEnabled(true);

            if (task.isSuccessful()) {

                mBinding.storeVideoId.getText().clear();
                mBinding.storeImageUrl.getText().clear();
                mBinding.storeProductUrl.getText().clear();
                mBinding.storeImageUrl.requestFocus();

            } else {
                Toast.makeText(AdminActivity.this, "failed to upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(AdminActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
    }

    private void homeSubmit() {

        String category = mBinding.homeCategory.getText().toString().trim();
        String imageUrl = mBinding.homeImageUrl.getText().toString().trim();
        String productUrl = mBinding.homeProductUrl.getText().toString().trim();

        if (category.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }

        if (productUrl.isEmpty()) productUrl = "";

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Home");

        String key = reference.push().getKey();

        if (key == null) {
            Toast.makeText(this, "Key is null", Toast.LENGTH_SHORT).show();
            return;
        }

        HomeProduct product = new HomeProduct(category, imageUrl, productUrl);

        reference.child(key).setValue(product).addOnCompleteListener(task -> {

            mBinding.homeSubmit.setEnabled(true);

            if (task.isSuccessful()) {

                mBinding.homeCategory.getText().clear();
                mBinding.homeImageUrl.getText().clear();
                mBinding.homeProductUrl.getText().clear();
                mBinding.homeProductUrl.requestFocus();

            } else {
                Toast.makeText(AdminActivity.this, "failed to upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(AdminActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
    }

    private void categorySubmit() {

        String category = mBinding.categoryCategory.getText().toString().trim();
        String imageUrl = mBinding.categoryImageUrl.getText().toString().trim();

        if (category.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Category");

        String key = reference.push().getKey();

        if (key == null) {
            Toast.makeText(this, "Key is null", Toast.LENGTH_SHORT).show();
            return;
        }

        Category cat = new Category(category, imageUrl);

        reference.child(key).setValue(cat).addOnCompleteListener(task -> {

            mBinding.categorySubmit.setEnabled(true);

            if (task.isSuccessful()) {

                mBinding.categoryCategory.getText().clear();
                mBinding.categoryImageUrl.getText().clear();
                mBinding.categoryCategory.requestFocus();

            } else {
                Toast.makeText(AdminActivity.this, "failed to upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(AdminActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
    }

    private void bookSubmit() {

        String title = mBinding.bookTitle.getText().toString().trim();
        String desc = mBinding.bookDescription.getText().toString().trim();
        String bookUrl = mBinding.bookBookUrl.getText().toString().trim();
        String category = mBinding.bookCategory.getText().toString().trim();
        String imageUrl = mBinding.bookImageUrl.getText().toString().trim();
        String length = mBinding.bookLength.getText().toString().trim();
        String rating = mBinding.bookRating.getText().toString().trim();
        String writer = mBinding.bookWriter.getText().toString().trim();
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

            mBinding.bookSubmit.setEnabled(true);

            if (task.isSuccessful()) {

                mBinding.bookTitle.getText().clear();
                mBinding.bookImageUrl.getText().clear();
                mBinding.bookBookUrl.getText().clear();
                mBinding.bookDescription.getText().clear();
                mBinding.bookRating.getText().clear();
                mBinding.bookLength.getText().clear();
                mBinding.bookWriter.getText().clear();
                mBinding.bookTitle.requestFocus();

            } else {
                Toast.makeText(AdminActivity.this, "failed to upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(AdminActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
    }
}