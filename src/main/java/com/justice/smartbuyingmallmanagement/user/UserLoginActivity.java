package com.justice.smartbuyingmallmanagement.user;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.justice.smartbuyingmallmanagement.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import com.justice.smartbuyingmallmanagement.Rating;
import com.justice.smartbuyingmallmanagement.data.AllData;

import java.util.List;

public class UserLoginActivity extends AppCompatActivity {
    private EditText emailEdtTxt;
    private EditText passwordEdtTxt;
    private Button loginBtn;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initWidgets();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmEmailAndPassword();


            }
        });
    }

    private void confirmEmailAndPassword() {
        String email=emailEdtTxt.getText().toString().trim();
        String password=passwordEdtTxt.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please Fill All Fields !!", Toast.LENGTH_SHORT).show();
            return;
        }

        /////////////BACKENDLESS////////////////////////
        showProgress(true);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    loadUserRatingList(emailEdtTxt.getText().toString().trim());
                    Toast.makeText(UserLoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserLoginActivity.this, UserFirstPageActivity.class);
                     startActivity(intent);
                    finish();


                }else{
                    String error=task.getException().getMessage();
                    Toast.makeText(UserLoginActivity.this, "Error"+error, Toast.LENGTH_SHORT).show();
                }
                showProgress(false);
            }
        });


        ////////////////////////////////////////////////

    }

    private void loadUserRatingList(String email) {
        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
        dataQueryBuilder.setWhereClause("email = '" + email + "'");
        showProgress(true);
        Backendless.Persistence.of(Rating.class).find(dataQueryBuilder, new AsyncCallback<List<Rating>>() {
            @Override
            public void handleResponse(List<Rating> response) {
                showProgress(false);
                UserLogin.currentUserRatingList = response;
                Toast.makeText(UserLoginActivity.this, "Finished loading user Rating", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
                Toast.makeText(UserLoginActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initWidgets() {
        emailEdtTxt = findViewById(R.id.emailEdtTxt);
        passwordEdtTxt = findViewById(R.id.passwordEdtTxt);
        loginBtn = findViewById(R.id.loginBtn);
////////////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);
    }

    /////////////////////PROGRESS_BAR////////////////////////////
    private void showProgress(boolean show) {
        if (show) {
            load.setVisibility(View.VISIBLE);
            loadTxtView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);

        } else {
            load.setVisibility(View.GONE);
            loadTxtView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);


        }

    }

}
