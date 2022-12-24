package com.pns.ajio.ui.book;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pns.ajio.R;
import com.pns.ajio.data.models.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private final List<Book> mList;
    private final Context mContext;
    private final String uid = FirebaseAuth.getInstance().getUid();

    public BookAdapter(List<Book> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_layout, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        holder.setData(mList.get(position));

        holder.buy.setOnClickListener(v -> openLink(mList.get(position)));
    }

    private void openLink(Book book) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Affiliate").child("books").child(book.getKey());
        Map<String, Object> map = new HashMap<>();
        map.put("purchased", book.getPurchased()+1);
        reference.updateChildren(map);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(book.getBookUrl()));
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}