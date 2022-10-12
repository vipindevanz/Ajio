package com.pns.ajio.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pns.ajio.R;
import com.pns.ajio.databinding.ActivityAccountBinding;
import com.pns.ajio.fragment.StoresFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountActivity extends AppCompatActivity {

    private static final int SIGN_IN_KEY = 1;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ActivityAccountBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initViews();
    }

    private void initViews() {

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        checkLoginState(mUser);

        mBinding.btnLogin.setOnClickListener(v -> signInWithGoogle());
        mBinding.back.setOnClickListener(v -> finish());
        mBinding.feedback.setOnClickListener(v -> openFeedback());
        mBinding.home.setOnClickListener(v -> {
            startActivity(new Intent(AccountActivity.this, HomeActivity.class));
            finish();
        });
        mBinding.store.setOnClickListener(v -> {
            startActivity(new Intent(AccountActivity.this, StoresFragment.class));
            finish();
        });
        mBinding.categories.setOnClickListener(v -> {
            startActivity(new Intent(AccountActivity.this, CategoryActivity.class));
            finish();
        });
        mBinding.wishlist.setOnClickListener(v -> {
            startActivity(new Intent(AccountActivity.this, WishlistActivity.class));
            finish();
        });
        mBinding.tvEmail.setOnLongClickListener(view -> {
            if (mUser == null) return false;
            if (Objects.requireNonNull(mUser.getEmail()).equals("officialvipindev@gmail.com")) {
                startActivity(new Intent(AccountActivity.this, AdminActivity.class));
            }
            return true;
        });

        mBinding.logout.setOnClickListener(v -> {

            if (mUser != null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Logout from the App");
                builder.setPositiveButton("Cancel", (dialogInterface, i) -> {

                }).setNegativeButton("Logout", (dialogInterface, i) -> {
                    mAuth.signOut();
                    finish();
                });
                builder.create();
                builder.show();
            }
        });
    }

    private void openFeedback() {

        View view = getLayoutInflater().inflate(R.layout.feedback, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        final EditText text = view.findViewById(R.id.suggestion);
        Button submit = view.findViewById(R.id.submit);

        submit.setOnClickListener(v -> {

            String feed = text.getText().toString().trim();

            if (!feed.equals("")) {

                alertDialog.dismiss();

                Map<String, Object> map = new HashMap<>();
                map.put("feed", feed);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Suggestion");
                reference.child(Objects.requireNonNull(reference.push().getKey())).setValue(map);
                Toast.makeText(this, "Thank You for your Feedback!", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void signInWithGoogle() {

        // Initializing Google sign In Options

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_KEY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Fetching request code for sign in

        if (requestCode == SIGN_IN_KEY) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);

                assert account != null;

                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {

                Toast.makeText(this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        // Generating credential and token

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {

                        Toast.makeText(AccountActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        mUser = mAuth.getCurrentUser();

                        assert mUser != null;

                        // Saving state of user login

                        SharedPreferences.Editor preferences = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                        preferences.putBoolean("loggedIn", true);
                        preferences.apply();

                        checkLoginState(mUser);

                    } else {

                        Toast.makeText(AccountActivity.this, task.getException() + "", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void checkLoginState(FirebaseUser user) {

        if (user == null) return;

        mBinding.btnLogin.setVisibility(View.GONE);
        mBinding.tvName.setText(user.getDisplayName());
        mBinding.tvEmail.setText(user.getEmail());
        mBinding.tvName.setVisibility(View.VISIBLE);
        mBinding.tvEmail.setVisibility(View.VISIBLE);
    }
}