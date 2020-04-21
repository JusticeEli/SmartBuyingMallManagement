package com.justice.smartbuyingmallmanagement.update;

import androidx.appcompat.app.AppCompatActivity;

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
import com.justice.smartbuyingmallmanagement.business.AddBusinessActivity;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.data.AllData;
import com.justice.smartbuyingmallmanagement.data.ApplicationClass;
import com.justice.smartbuyingmallmanagement.user.UserLogin;

import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    private String businessName;
    private Business business;


    private EditText regIdEdtTxt;
    private EditText businessNameEdtTxt;
    private EditText offerEdtTxt;
    private EditText productServiceDescriptionEdtTxt;
    private EditText offerUptoEdtTxt;
    private EditText addressEdtTxt;
    private EditText landmarkEdtTxt;
    private EditText localityEdtTxt;
    private EditText mobileEdtTxt;

    private Button submitBtn;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        businessName = getIntent().getStringExtra("businessName");
        business = getBusiness();
        initWidgets();
        setDefaultValues();
        setOnClickListeners();
    }

    private Business getBusiness() {
        for (Business business : AllData.businessList) {
            if (business.getBusinessName().equals(businessName)) {
                return business;
            }
        }
        return null;
    }

    private void setOnClickListeners() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveDataFromEdtTxt();
                putIntoDatabase();
                Toast.makeText(UpdateActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                //   changeNameOfBusinessInTheRatingListOfEveryUser();
                finish();
            }
        });
    }

    ///////////////BACKENDLESS/////////////////////////////////
    private void putIntoDatabase() {
        showProgress(true);
        Backendless.Persistence.save(business, new AsyncCallback<Business>() {
            @Override
            public void handleResponse(Business response) {
                loadAllRatingList();
                showProgress(false);
                Toast.makeText(UpdateActivity.this, "Update  ", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void handleFault(BackendlessFault fault) {

                showProgress(false);
                Toast.makeText(UpdateActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void loadAllRatingList() {
        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
        showProgress(true);
        Backendless.Persistence.of(Rating.class).find(dataQueryBuilder, new AsyncCallback<List<Rating>>() {
            @Override
            public void handleResponse(List<Rating> response) {
                showProgress(false);
                AllData.ratingList = response;
                updateRatingListForOverallAndForAllUsers();

                Toast.makeText(UpdateActivity.this, "updating rating list..", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
                Toast.makeText(UpdateActivity.this, "Error:" + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void changeNameOfBusinessInTheRatingListOfEveryUser() {
        for (UserLogin userLogin : AllData.userLoginList) {
            for (Rating rating : userLogin.getUserRatingList()) {
                if (rating.getBusinessName().equals(businessName)) {
                    rating.setBusinessName(businessNameEdtTxt.getText().toString().trim());
                }
            }
        }
    }


    public void updateRatingListForOverallAndForAllUsers() {

        for (Rating rating : UserLogin.currentUserRatingList) {
            if (rating.getBusinessName().equals(businessName)) {
                rating.setBusinessName(business.getBusinessName());
            }
        }


        for (Rating rating : AllData.ratingList) {
            if (rating.getBusinessName().equals(businessName)) {
                rating.setBusinessName(business.getBusinessName());

                int n1 = rating.getDiscountPolicyAsset();
                int n2 = rating.getPolitenessTheKindness();
                int n3 = rating.getQualityOfService();
                int n4 = rating.getValueForMoney();
                showProgress(true);
                Backendless.Persistence.save(rating, new AsyncCallback<Rating>() {
                    @Override
                    public void handleResponse(Rating response) {
                        showProgress(false);
                        Toast.makeText(UpdateActivity.this, "Updating List", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        showProgress(false);
                        Toast.makeText(UpdateActivity.this, "Error :" + fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }


        }


    }

    private void retrieveDataFromEdtTxt() {

        business.setBusinessName(businessNameEdtTxt.getText().toString().trim());
        business.setOffer(offerEdtTxt.getText().toString().trim());
        business.setProductServiceDescription(productServiceDescriptionEdtTxt.getText().toString().trim());
        business.setOfferUpto(offerUptoEdtTxt.getText().toString().trim());
        business.setAddress(addressEdtTxt.getText().toString().trim());
        business.setCity(landmarkEdtTxt.getText().toString().trim());
        business.setLocality(localityEdtTxt.getText().toString().trim());
        business.setMobile(mobileEdtTxt.getText().toString().trim());
        AllData.writeAllDataToFiles();
    }

    private void setDefaultValues() {
        regIdEdtTxt.setText("" + business.getRegId());
        businessNameEdtTxt.setText(business.getBusinessName());
        offerEdtTxt.setText(business.getOffer());
        productServiceDescriptionEdtTxt.setText(business.getProductServiceDescription());
        offerUptoEdtTxt.setText(business.getOfferUpto());
        addressEdtTxt.setText(business.getAddress());
        landmarkEdtTxt.setText(business.getCity());
        localityEdtTxt.setText(business.getLocality());
        mobileEdtTxt.setText(business.getMobile());
    }

    private void initWidgets() {
        regIdEdtTxt = findViewById(R.id.regIdEdtTxt);
        businessNameEdtTxt = findViewById(R.id.businessNameEdtTxt);
        offerEdtTxt = findViewById(R.id.offerEdtTxt);
        productServiceDescriptionEdtTxt = findViewById(R.id.productServiceDescriptionEdtTxt);
        offerUptoEdtTxt = findViewById(R.id.offerUptoEdtTxt);
        addressEdtTxt = findViewById(R.id.addressEdtTxt);
        landmarkEdtTxt = findViewById(R.id.landmarkEdtTxt);
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
