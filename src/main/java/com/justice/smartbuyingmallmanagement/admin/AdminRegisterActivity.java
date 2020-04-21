package com.justice.smartbuyingmallmanagement.admin;

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
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.data.AllData;
import com.justice.smartbuyingmallmanagement.user.UserRegisterActivity;

public class AdminRegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private EditText emailEdtTxt;
    private EditText passwordEdtTxt;
    private EditText confirmPasswordEdtTxt;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
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
        String password = passwordEdtTxt.getText().toString().trim();
        String confirmPassword = confirmPasswordEdtTxt.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please Fill All Fields!!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
            return;
        }
        ///////////SAVE DATA IN DATABASE/////////////
        showProgress(true);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    showProgress(false);
                    saveDataToFile();

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(AdminRegisterActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();

                }
                showProgress(false);
            }
        });


        ////////////////////////////////////

    }

    private void saveDataToFile() {
        AdminLogin adminLogin = new AdminLogin();
        adminLogin.setEmail(emailEdtTxt.getText().toString().trim());
        adminLogin.setPassword(passwordEdtTxt.getText().toString().trim());
        AllData.adminLoginList.add(adminLogin);
        AllData.writeAllDataToFiles();
        Toast.makeText(this, "Registration Successfull", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AdminRegisterActivity.this, AdminLoginFirstPageActivity.class);
        intent.putExtra("email", emailEdtTxt.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    private void initWidgets() {
        registerBtn = findViewById(R.id.registerBtn);
        emailEdtTxt = findViewById(R.id.emailEdtTxt);
        passwordEdtTxt = findViewById(R.id.passwordEdtTxt);
        confirmPasswordEdtTxt = findViewById(R.id.confirmPasswordEdtTxt);
        ///////BACKENDLESS////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);
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
}
