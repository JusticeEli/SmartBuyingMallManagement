package com.justice.smartbuyingmallmanagement.user;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.Rating;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.data.AllData;

import java.util.List;

public class UserRegisterActivity extends AppCompatActivity {
    private Button registerBtn;
    private EditText emailEdtTxt;
    private EditText passwordEdtTxt;
    private EditText confirmPasswordEdtTxt;

    private UserLogin userLogin;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;

    private boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        initWidgets();
        setOnClickListeners();
    }


    private void setOnClickListeners() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData();
            }
        });
    }

    private void retrieveData() {
        String email = emailEdtTxt.getText().toString().trim();
        String password = emailEdtTxt.getText().toString().trim();
        String confirmPassword = emailEdtTxt.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please Fill All Fields !!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
            return;
        }

showProgress(true);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UserRegisterActivity.this, "User registered successfully :", Toast.LENGTH_SHORT).show();
                    fetchTheFuckingUserRecords();
                    showProgress(false);
                    loadUserRatingList(emailEdtTxt.getText().toString().trim());
                    saveDataToFile();

                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(UserRegisterActivity.this, "Error"+error, Toast.LENGTH_SHORT).show();

                }
                showProgress(false);
            }
        });

        ///////////SAVE DATA IN DATABASE/////////////
        BackendlessUser user = new BackendlessUser();
        user.setEmail(emailEdtTxt.getText().toString().trim());
        user.setPassword(passwordEdtTxt.getText().toString().trim());
        user.setProperty("type", "user");
        showProgress(true);


        ////////////////////////////////////


    }

    private void fetchTheFuckingUserRecords() {
        Rating original = null;
        if (!AllData.ratingList.isEmpty()) {

            for (Rating rating : AllData.ratingList) {

                if (rating.getEmail().equals("average")) {

                    original = new Rating();

                    original.setEmail(emailEdtTxt.getText().toString().trim());
                    original.setBusinessName(rating.getBusinessName());


                    Backendless.Persistence.of(Rating.class).save(original, new AsyncCallback<Rating>() {
                        @Override
                        public void handleResponse(Rating response) {
                            Toast.makeText(UserRegisterActivity.this, "Creating User Rating Record..", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(UserRegisterActivity.this, "Error" + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }


        } else {
            Toast.makeText(UserRegisterActivity.this, "All Data Rating List is empty!!", Toast.LENGTH_SHORT).show();
        }
    }

    /////////////////////BACKENDLESS////////////////////////////
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

    private void saveDataToFile() {
        userLogin = new UserLogin();
        userLogin.setEmail(emailEdtTxt.getText().toString().trim());
        userLogin.setPassword(passwordEdtTxt.getText().toString().trim());
        addUserLoginDataIntoDatabase();
        AllData.writeAllDataToFiles();
        Toast.makeText(UserRegisterActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(UserRegisterActivity.this, UserFirstPageActivity.class);
        intent.putExtra("email", emailEdtTxt.getText().toString().trim());
        startActivity(intent);
        finish();


    }

    private void addUserLoginDataIntoDatabase() {
        ////////////PUT DATA IN  BACKENDLESS  USER_LOGIN TABLE/////////////////
        Backendless.Persistence.of(UserLogin.class).save(userLogin, new AsyncCallback<UserLogin>() {
            @Override
            public void handleResponse(UserLogin response) {
                AllData.userLoginList.add(response);
                Toast.makeText(UserRegisterActivity.this, "Data Added into UserLogin table :", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(UserRegisterActivity.this, "Error :" + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserRatingList(String email) {

        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
        dataQueryBuilder.setWhereClause("email = '" + email + "'");
        Backendless.Persistence.of(Rating.class).find(dataQueryBuilder, new AsyncCallback<List<Rating>>() {
            @Override
            public void handleResponse(List<Rating> response) {
                UserLogin.currentUserRatingList = response;
                Toast.makeText(UserRegisterActivity.this, "Finished loading user Rating Records", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(UserRegisterActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initWidgets() {
        registerBtn = findViewById(R.id.registerBtn);
        emailEdtTxt = findViewById(R.id.emailEdtTxt);
        passwordEdtTxt = findViewById(R.id.passwordEdtTxt);
        confirmPasswordEdtTxt = findViewById(R.id.confirmPasswordEdtTxt);
////////BACKENDLESS////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);

    }
}
