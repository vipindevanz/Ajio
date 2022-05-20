package com.pns.ajio.viewholder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.pns.ajio.R;
import com.pns.ajio.activity.BagActivity;
import com.pns.ajio.activity.BooksActivity;
import com.pns.ajio.model.Book;
import com.pns.ajio.model.ProductModel;

public class BookViewHolder extends RecyclerView.ViewHolder {

    public ImageView bookImg;
    public TextView title, desc;
    public MaterialButton rating, language, length, writer, buy;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);

        bookImg = itemView.findViewById(R.id.book_img);
        title = itemView.findViewById(R.id.book_title);
        desc = itemView.findViewById(R.id.book_desc);
        rating = itemView.findViewById(R.id.book_rating);
        language = itemView.findViewById(R.id.book_language);
        length = itemView.findViewById(R.id.book_length);
        writer = itemView.findViewById(R.id.book_writer);
        buy = itemView.findViewById(R.id.buyNow);

    }

    // Setting the data in item layout

    @SuppressLint("SetTextI18n")
    public void setData(Book model) {

        Glide.with(bookImg).
                load(model.getImgUrl()).
                into(bookImg);
        title.setText(model.getTitle());
        desc.setText(model.getDesc());
        rating.setText(model.getRating() + " ⭐");
        language.setText(model.getLanguage());
        length.setText(model.getLength() + " Pages");
        writer.setText(model.getWriter());
    }
}