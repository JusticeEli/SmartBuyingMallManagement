package com.justice.smartbuyingmallmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.data.AllData;
import com.justice.smartbuyingmallmanagement.user.UserLogin;

public class MoreInfoActivity extends AppCompatActivity {
    private String businessName;
    private Business business;

    private TextView businessNameTxtView;
    private TextView offerTxtView;

    private TextView qualityOfTheServiceTxtView;
    private TextView politenessTxtView;
    private TextView valueForMoneyTxtView;
    private TextView discountPolicyTxtView;

    private TextView productServiceDescriptionTxtView;
    private TextView offerUptoTxtView;
    private TextView addressTxtView;
    private TextView cityTxtView;
    private TextView localityTxtView;
    private TextView mobileTxtView;
    private TextView latTxtView;
    private TextView longTxtView;


    ////////////////Ratings Total///////
    private int qualityAverage;
    private int politenessAverage;
    private int valueAverage;
    private int discountAverage;

    private Rating origalRating = new Rating();
    private Button showMapBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        businessName = getIntent().getStringExtra("businessName");
        business = getTheBusiness();
        initWidgets();
        setDefaultValues();
        setDefaultValuesForRating();

        setOnClickListeners();
    }

    private void setDefaultValuesForRating() {
        //////////////////////////////////////////
        for (Rating rating : AllData.ratingList) {
            if (rating.getBusinessName().equals(businessName) && rating.getEmail().equals("average")) {
                origalRating = rating;
                break;
            }
        }

        qualityAverage = origalRating.getQualityOfService();
        politenessAverage = origalRating.getPolitenessTheKindness();
        valueAverage = origalRating.getValueForMoney();
        discountAverage = origalRating.getDiscountPolicyAsset();

        findTheAverageForTheRatings();
    }

    private void findTheAverageForTheRatings() {

        switch (qualityAverage) {
            case 1:
                qualityOfTheServiceTxtView.setText("Excellent");
                break;

            case 2:
                qualityOfTheServiceTxtView.setText("Good");

                break;
            case 3:
                qualityOfTheServiceTxtView.setText("Medium");

                break;
            case 4:
                qualityOfTheServiceTxtView.setText("Bad");

                break;
            case 5:
                qualityOfTheServiceTxtView.setText("Unacceptable");

                break;
        }
        switch (politenessAverage) {
            case 1:
                politenessTxtView.setText("Excellent");
                break;

            case 2:
                politenessTxtView.setText("Good");

                break;
            case 3:
                politenessTxtView.setText("Medium");

                break;
            case 4:
                politenessTxtView.setText("Bad");

                break;
            case 5:
                politenessTxtView.setText("Unacceptable");

                break;
        }
        switch (valueAverage) {
            case 1:
                valueForMoneyTxtView.setText("Excellent");
                break;

            case 2:
                valueForMoneyTxtView.setText("Good");

                break;
            case 3:
                valueForMoneyTxtView.setText("Medium");

                break;
            case 4:
                valueForMoneyTxtView.setText("Bad");

                break;
            case 5:
                valueForMoneyTxtView.setText("Unacceptable");

                break;
        }
        switch (discountAverage) {
            case 1:
                discountPolicyTxtView.setText("Generous");
                break;

            case 2:
                discountPolicyTxtView.setText("Good");

                break;
            case 3:
                discountPolicyTxtView.setText("Normal");

                break;
            case 4:
                discountPolicyTxtView.setText("Limited");

                break;
            case 5:
                discountPolicyTxtView.setText("None");

                break;
        }

    }

    private Business getTheBusiness() {
        for (Business business : AllData.businessList) {
            if (business.getBusinessName().equals(businessName)) {
                return business;
            }
        }
        return null;
    }

    private void setDefaultValues() {
        businessNameTxtView.setText(business.getBusinessName());
        offerTxtView.setText(business.getOffer());
        productServiceDescriptionTxtView.setText(business.getProductServiceDescription());
        offerUptoTxtView.setText(business.getOfferUpto());
        addressTxtView.setText(business.getAddress());
        cityTxtView.setText(business.getCity());
        localityTxtView.setText(business.getLocality());
        mobileTxtView.setText(business.getMobile());
        latTxtView.setText(business.getLat());
        longTxtView.setText(business.getLongtide());
    }

    private void setOnClickListeners() {
        showMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + business.getLat() + "," + business.getLongtide()));
                startActivity(intent);

            }
        });
    }

    private void initWidgets() {
        businessNameTxtView = findViewById(R.id.businessNameTxtView);
        offerTxtView = findViewById(R.id.offerTxtView);

        qualityOfTheServiceTxtView = findViewById(R.id.qualityOfTheServiceTxtView);
        politenessTxtView = findViewById(R.id.politenessTextView);
        valueForMoneyTxtView = findViewById(R.id.valueForMoneyTxtView);
        discountPolicyTxtView = findViewById(R.id.discountPolicyTxtView);
        productServiceDescriptionTxtView = findViewById(R.id.productServiceDescriptionTxtView);
        offerUptoTxtView = findViewById(R.id.offerUptoTxtView);
        addressTxtView = findViewById(R.id.addressTxtView);
        cityTxtView = findViewById(R.id.cityTxtView);
        localityTxtView = findViewById(R.id.localityTxtView);
        mobileTxtView = findViewById(R.id.mobileTxtView);
        latTxtView = findViewById(R.id.latTxtView);
        longTxtView = findViewById(R.id.longTxtView);
        showMapBtn = findViewById(R.id.showMapBtn);

    }


}
