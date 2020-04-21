package com.justice.smartbuyingmallmanagement.business;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.justice.smartbuyingmallmanagement.R;

public class BusinessOwnerSetupActivity extends AppCompatActivity {


    private EditText nameEdtTxt;
    private EditText mobileEdtTxt;
    private EditText userTypeEdtTxt;
    private EditText addressEdtTxt;
    private EditText aadharNoEdtTxt;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;


    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_owner_setup);
        initWidgets();
        setOnClickListeners();

    }

    private void setOnClickListeners() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (someEdtTxtNotField()) {
                    return;
                }
                saveDataToDataBase();


            }
        });
    }

    private void saveDataToDataBase() {
        BusinessOwnerSetup businessOwnerSetup = new BusinessOwnerSetup();

        String name = nameEdtTxt.getText().toString().trim();
        String mobile = mobileEdtTxt.getText().toString().trim();
        String userType = userTypeEdtTxt.getText().toString().trim();
        String address = addressEdtTxt.getText().toString().trim();
        String aadharNo = aadharNoEdtTxt.getText().toString().trim();

        businessOwnerSetup.setName(name);
        businessOwnerSetup.setMobile(mobile);
        businessOwnerSetup.setUserType(userType);
        businessOwnerSetup.setAddress(address);
        businessOwnerSetup.setAadharNo(aadharNo);
        businessOwnerSetup.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        showProgress(true);
        FirebaseFirestore.getInstance().collection("business_owner_setup").document(FirebaseAuth.getInstance().getUid()).set(businessOwnerSetup).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(BusinessOwnerSetupActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BusinessOwnerSetupActivity.this, BusinessOwnerFirstPageActivity.class);
                    resetEdtTxt();
                    startActivity(intent);
                    finish();


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(BusinessOwnerSetupActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                showProgress(false);
            }
        });
        ///////////SAVE DATA IN DATABASE/////////////


        ////////////////////////////////////
    }

    private boolean someEdtTxtNotField() {
        if (nameEdtTxt.getText().toString().trim().isEmpty() || mobileEdtTxt.getText().toString().trim().isEmpty() || userTypeEdtTxt.getText().toString().trim().isEmpty() || addressEdtTxt.getText().toString().trim().isEmpty() || aadharNoEdtTxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please Fill All Fields !!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private void resetEdtTxt() {
        nameEdtTxt.setText("");
        mobileEdtTxt.setText("");
        userTypeEdtTxt.setText("");
        addressEdtTxt.setText("");
        aadharNoEdtTxt.setText("");
    }


    private void initWidgets() {
        nameEdtTxt = findViewById(R.id.nameEdtTxt);
        mobileEdtTxt = findViewById(R.id.mobileEdtTxt);
        userTypeEdtTxt = findViewById(R.id.addressEdtTxt);
        addressEdtTxt = findViewById(R.id.addressEdtTxt);
        aadharNoEdtTxt = findViewById(R.id.aadharNoEdtTxt);


        registerBtn = findViewById(R.id.registerBtn);

        ///////////////////////BACKENDLESS//////////////////////
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
