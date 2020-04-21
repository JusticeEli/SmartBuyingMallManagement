package com.justice.smartbuyingmallmanagement.business;

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
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.Rating;
import com.justice.smartbuyingmallmanagement.data.AllData;
import com.justice.smartbuyingmallmanagement.data.ApplicationClass;
import com.justice.smartbuyingmallmanagement.user.UserLogin;

import java.util.List;

public class AddBusinessActivity extends AppCompatActivity {
    private String email;
    private EditText businessNameEdtTxt;
    private EditText offerEdtTxt;
    private EditText productServiceDescriptionEdtTxt;
    private EditText offerUptoEdtTxt;
    private EditText addressEdtTxt;
    private EditText cityEdtTxt;
    private EditText localityEdtTxt;
    private EditText mobileEdtTxt;

    private Button submitBtn;

    private Business business;


    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);
        email = getIntent().getStringExtra("email");

     //   updateUserLoginList();
        initWidgets();
        setOnClickListeners();

    }

    private void updateUserLoginList() {
        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
        Backendless.Persistence.of(UserLogin.class).find(dataQueryBuilder, new AsyncCallback<List<UserLogin>>() {
            @Override
            public void handleResponse(List<UserLogin> response) {
                AllData.userLoginList = response;
                Toast.makeText(AddBusinessActivity.this, "User Login list loaded", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(AddBusinessActivity.this, "Error:" + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void setOnClickListeners() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                business = retrieveAllDataFromEdtTxt();
                showProgress(true);
                ///////////////////ADDING RATING FOR OVER_RAL RATING LIST///////////////////////
                //   addRatingRecordForAllUsers();
                AllData.writeAllDataToFiles();
                putIntoDataBase();
                resetEdtTxt();
                //////////GO BACK TO BUSINESS OWNER FIRST PAGE//////////////
                onBackPressed();
            }
        });
    }

    ///////////////BACKENDLESS/////////////////////////////////
    private void putIntoDataBase() {
        Backendless.Data.of(Business.class).save(business, new AsyncCallback<Business>() {
            @Override
            public void handleResponse(Business response) {
                AllData.businessList.add(response);

                showProgress(false);
                Toast.makeText(AddBusinessActivity.this, "Business Added Successfully", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void handleFault(BackendlessFault fault) {

                showProgress(false);
                Toast.makeText(AddBusinessActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Backendless.Persistence.save(addRatingForOverRallRatingList(), new AsyncCallback<Rating>() {
            @Override
            public void handleResponse(Rating response) {
                AllData.ratingList.add(response);
                addRatingRecordForAllUsers();
                showProgress(false);
                Toast.makeText(AddBusinessActivity.this, "Rating added ", Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
                Toast.makeText(AddBusinessActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }

    private Rating addRatingForOverRallRatingList() {
        Rating rating = new Rating();
        rating.setBusinessName(business.getBusinessName());
        rating.setEmail("average");
        return rating;

    }

    private void addRatingRecordForAllUsers() {

        for (UserLogin userLogin : AllData.userLoginList) {
            Rating rating = new Rating();
            rating.setBusinessName(business.getBusinessName());
            rating.setEmail(userLogin.getEmail());
            Backendless.Persistence.of(Rating.class).save(rating, new AsyncCallback<Rating>() {
                @Override
                public void handleResponse(Rating response) {
                    AllData.ratingList.add(response);
                    Toast.makeText(AddBusinessActivity.this, "Adding Rating Record For All User.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(AddBusinessActivity.this, "Error" + fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


        for (UserLogin userLogin : AllData.userLoginList) {
            userLogin.getUserRatingList().add(new Rating(business.getBusinessName()));
        }
    }

    private void resetEdtTxt() {
        businessNameEdtTxt.setText("");
        offerEdtTxt.setText("");
        productServiceDescriptionEdtTxt.setText("");
        offerUptoEdtTxt.setText("");
        addressEdtTxt.setText("");
        cityEdtTxt.setText("");
        localityEdtTxt.setText("");
        mobileEdtTxt.setText("");
    }

    private Business retrieveAllDataFromEdtTxt() {
        Business business = new Business();
        business.setEntity(businessNameEdtTxt.getText().toString().trim() + " : " + email);
        business.setBusinessOwnerEmail(email);
        business.setBusinessName(businessNameEdtTxt.getText().toString().trim());
        business.setOffer(offerEdtTxt.getText().toString().trim());
        business.setProductServiceDescription(productServiceDescriptionEdtTxt.getText().toString().trim());
        business.setOfferUpto(offerUptoEdtTxt.getText().toString().trim());
        business.setAddress(addressEdtTxt.getText().toString().trim());
        business.setCity(cityEdtTxt.getText().toString().trim());
        business.setLocality(localityEdtTxt.getText().toString().trim());
        business.setMobile(mobileEdtTxt.getText().toString().trim());

        return business;
    }

    private void initWidgets() {
        businessNameEdtTxt = findViewById(R.id.businessNameEdtTxt);
        offerEdtTxt = findViewById(R.id.offerEdtTxt);
        productServiceDescriptionEdtTxt = findViewById(R.id.productServiceDescriptionEdtTxt);
        offerUptoEdtTxt = findViewById(R.id.offerUptoEdtTxt);
        addressEdtTxt = findViewById(R.id.addressEdtTxt);
        cityEdtTxt = findViewById(R.id.cityEdtTxt);
        localityEdtTxt = findViewById(R.id.localityEdtTxt);
        mobileEdtTxt = findViewById(R.id.mobileEdtTxt);

        submitBtn = findViewById(R.id.submitBtn);

        ////////////////////BACKENDLESS//////////////////////
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
