package com.pns.ajio.ui.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.R;
import com.pns.ajio.ui.account.AccountActivity;
import com.pns.ajio.data.models.Product;
import com.pns.ajio.data.models.WishList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private final List<Product> mList;
    private final Context mContext;
    private final String mCategory;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ProductAdapter(List<Product> list, Context context, String category) {
        mList = list;
        mContext = context;
        mCategory = category;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        Inflating the item layout and passing to view holder
        */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = mList.get(position);
        holder.setData(product);

        holder.buy.setOnClickListener(v -> openLink(product));
        holder.mImgWishList.setOnClickListener(v -> wishListItem(product, holder.mImgWishList));
        checkWishlistItems(product, holder.mImgWishList);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void openLink(Product product) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Affiliate").child(mCategory)
                .child(product.getKey());
        Map<String, Object> map = new HashMap<>();
        map.put("purchased", product.getPurchased() + 1);
        reference.updateChildren(map);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(product.getProductUrl()));
        mContext.startActivity(intent);
    }

    private void checkWishlistItems(Product product, ImageView imageView) {

        if (user == null) return;

        FirebaseDatabase.getInstance().getReference("Wishlist").child(user.getUid())
                .child(product.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            imageView.setImageResource(R.drawable.ic_wishlisted);
                            imageView.setTag("wishlisted");
                        } else {
                            imageView.setTag("wishlist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void wishListItem(Product product, ImageView imageView) {

        if (user == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("SignIn to Wishlist this Product");
            builder.setPositiveButton("Cancel", (dialogInterface, i) -> {

            }).setNegativeButton("SignIn", (dialogInterface, i) ->
                    mContext.startActivity(new Intent(mContext, AccountActivity.class)));
            builder.create();
            builder.show();
            return;
        }

        if (imageView.getTag() == "wishlist") {

            imageView.setImageResource(R.drawable.ic_wishlisted);

            FirebaseDatabase.getInstance().getReference("Wishlist").child(user.getUid())
                    .child(product.getKey()).setValue(new WishList(product.getKey(), mCategory))
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            imageView.setTag("wishlisted");
                            Toast.makeText(mContext, "Added in Wishlist", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show());

        } else {

            imageView.setImageResource(R.drawable.ic_favourite);

            FirebaseDatabase.getInstance().getReference("Wishlist").child(user.getUid())
                    .child(product.getKey()).removeValue().addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            imageView.setTag("wishlist");
                            Toast.makeText(mContext, "Removed from Wishlist", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}