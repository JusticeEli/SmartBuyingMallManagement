package com.justice.smartbuyingmallmanagement.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.Rating;
import com.justice.smartbuyingmallmanagement.admin.AdminLoginActivity;
import com.justice.smartbuyingmallmanagement.admin.AdminRegisterActivity;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.business.BusinessOwnerLoginActivity;
import com.justice.smartbuyingmallmanagement.business.BusinessOwnerRegisterActivity;
import com.justice.smartbuyingmallmanagement.data.AllData;
import com.justice.smartbuyingmallmanagement.data.ApplicationClass;
import com.justice.smartbuyingmallmanagement.user.UserLogin;
import com.justice.smartbuyingmallmanagement.user.UserLoginActivity;
import com.justice.smartbuyingmallmanagement.user.UserRegisterActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSIONS_ = 1;
    private Button userLoginBtn;
    private Button userRegisterBtn;
    private Button adminLoginBtn;
    private Button adminRegisterBtn;

    private Button businessOwnerLogin;
    private Button businessOwnerRegisterBtn;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermissions();
        initWidgets();
        readAllDataFromDataBase();
        setOnClickListeners();

    }

    private void setPermissions() {
        boolean success =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;

        if (!success) {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET};

            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setOnClickListeners() {
        userLoginBtn.setOnClickListener(this);
        userRegisterBtn.setOnClickListener(this);
        adminLoginBtn.setOnClickListener(this);
        adminRegisterBtn.setOnClickListener(this);
        userLoginBtn.setOnClickListener(this);
        businessOwnerLogin.setOnClickListener(this);
        businessOwnerRegisterBtn.setOnClickListener(this);
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
    private void initWidgets() {
        userLoginBtn = findViewById(R.id.userLoginBtn);
        userRegisterBtn = findViewById(R.id.userRegisterBtn);
        adminLoginBtn = findViewById(R.id.adminLoginBtn);
        adminRegisterBtn = findViewById(R.id.adminRegisterBtn);
        businessOwnerLogin = findViewById(R.id.businessOwnerLoginBtn);
        businessOwnerRegisterBtn = findViewById(R.id.businessOwnerRegisterBtn);

        ///////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userLoginBtn:
                Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
                startActivity(intent);

                break;
            case R.id.userRegisterBtn:
                Intent intent1 = new Intent(MainActivity.this, UserRegisterActivity.class);
                startActivity(intent1);

                break;
            case R.id.adminLoginBtn:
                Intent intent2 = new Intent(MainActivity.this, AdminLoginActivity.class);
                startActivity(intent2);

                break;
            case R.id.adminRegisterBtn:
                Intent intent5 = new Intent(MainActivity.this, AdminRegisterActivity.class);
                startActivity(intent5);

                break;

            case R.id.businessOwnerLoginBtn:
                Intent intent3 = new Intent(MainActivity.this, BusinessOwnerLoginActivity.class);
                startActivity(intent3);

                break;
            case R.id.businessOwnerRegisterBtn:
                Intent intent4 = new Intent(MainActivity.this, BusinessOwnerRegisterActivity.class);
                startActivity(intent4);

                break;


        }

    }

    private void readAllDataFromDataBase() {

        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();

        /**
         *   Backendless.Persistence.of(BusinessOwner.class).find(dataQueryBuilder, new AsyncCallback<List<BusinessOwner>>() {
         *             @Override
         *             public void handleResponse(List<BusinessOwner> response) {
         *                 AllData.businessOwnerList = response;
         *                 Toast.makeText(ApplicationClass.this, "success", Toast.LENGTH_SHORT).show();
         *             }
         *
         *
         *             @Override
         *             public void handleFault(BackendlessFault fault) {
         *                 Toast.makeText(ApplicationClass.this, "Error:" + fault.getMessage(), Toast.LENGTH_SHORT).show();
         *
         *             }
         *         });
         */
showProgress(true);
        Backendless.Persistence.of(Business.class).find(dataQueryBuilder, new AsyncCallback<List<Business>>() {
            @Override
            public void handleResponse(List<Business> response) {
                showProgress(false);
                AllData.businessList = response;
                Toast.makeText(MainActivity.this, "Business List loaded : success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
                Toast.makeText(MainActivity.this, "Error:" + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        showProgress(true);
        Backendless.Persistence.of(Rating.class).find(dataQueryBuilder, new AsyncCallback<List<Rating>>() {
            @Override
            public void handleResponse(List<Rating> response) {
                showProgress(false);
                AllData.ratingList = response;
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
                Toast.makeText(MainActivity.this, "Error:" + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        showProgress(true);
        Backendless.Persistence.of(UserLogin.class).find(dataQueryBuilder, new AsyncCallback<List<UserLogin>>() {
            @Override
            public void handleResponse(List<UserLogin> response) {
                showProgress(false);
                AllData.userLoginList = response;
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
                Toast.makeText(MainActivity.this, "Error:" + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        ///THE BUG IS WHEN I CREATED A BUSINESS IN THE PAST AND A USER HAD NOT YET REGISTERED THE USER RATING LIST WILL NOT HAVE THE BUSINESS///////
        //////////WHAT I CAN DO IS AFTER GETTING ALL THE BUSINESS FROM DATABASE I CAN CHECK MY LOGIN LIST TO CHECK IF THE BUSINESS ARE AVAILABLE IS NOT ADD THERE RATING TO THE RATING LIST ////////////


    }


}
