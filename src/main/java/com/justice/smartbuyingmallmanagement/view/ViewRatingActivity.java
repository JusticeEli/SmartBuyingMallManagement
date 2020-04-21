package com.justice.smartbuyingmallmanagement.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.Rating;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.data.AllData;
import com.justice.smartbuyingmallmanagement.user.UserLogin;

import java.util.List;

public class ViewRatingActivity extends AppCompatActivity {
    private String businessName;
    private Business business;
    private TextView businessNameTxtView;

    private TextView quality;
    private TextView politeness;
    private TextView value;
    private TextView discount;
    private Rating rating;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private RelativeLayout relativeLayout;




    ////////////////Ratings Total///////
    private int qualityTotal;
    private int politenessTotal;
    private int valueTotal;
    private int discountTotal;

    ////////////////Ratings Average///////
    private int qualityAverage;
    private int politenessAverage;
    private int valueAverage;
    private int discountAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rating);
        businessName = getIntent().getStringExtra("businessName");
        initWidgets();
        rating=getRating();
        setAverageRating();

       // loadOverallRatingForSpecificBusiness();
        //  getBusiness();

        //  calculateRating();

    }

    private Rating getRating() {
        for (Rating rating:AllData.ratingList){
            if(rating.getBusinessName().equals(businessName)&&rating.getEmail().equals("average")){
                return rating;
            }

        }
        return null;

    }

    private void loadOverallRatingForSpecificBusiness() {
        showProgress(true);
        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
        dataQueryBuilder.setWhereClause("email = 'average' AND businessName = '" + businessName + "'");
        Backendless.Persistence.of(Rating.class).find(dataQueryBuilder, new AsyncCallback<List<Rating>>() {
            @Override
            public void handleResponse(List<Rating> response) {
                showProgress(false);
                rating = response.get(0);
                Toast.makeText(ViewRatingActivity.this, "Average rating", Toast.LENGTH_SHORT).show();
                setAverageRating();


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ViewRatingActivity.this, "Error :" + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void calculateRating() {
        for (UserLogin userLogin : AllData.userLoginList) {
            for (Rating rating : userLogin.getUserRatingList()) {
                if (rating.getBusinessName().equals(businessName)) {
                    qualityTotal += rating.getQualityOfService();
                    politenessTotal += rating.getPolitenessTheKindness();
                    valueTotal += rating.getValueForMoney();
                    discountTotal += rating.getDiscountPolicyAsset();
                }
            }
        }

        setAverageRating();

    }

    /**
     * private void findTheAverageForTheRatings() {
     * qualityAverage = qualityTotal / AllData.userLoginList.size();
     * politenessAverage = politenessTotal / AllData.userLoginList.size();
     * valueAverage = valueTotal / AllData.userLoginList.size();
     * discountAverage = discountTotal / AllData.userLoginList.size();
     * <p>
     * businessNameTxtView.setText(business.getBusinessName());
     * <p>
     * <p>
     * switch (qualityAverage) {
     * case 1:
     * quality.setText("Excellent");
     * break;
     * <p>
     * case 2:
     * quality.setText("Good");
     * <p>
     * break;
     * case 3:
     * quality.setText("Medium");
     * <p>
     * break;
     * case 4:
     * quality.setText("Bad");
     * <p>
     * break;
     * case 5:
     * quality.setText("Unacceptable");
     * <p>
     * break;
     * }
     * switch (politenessAverage) {
     * case 1:
     * politeness.setText("Excellent");
     * break;
     * <p>
     * case 2:
     * politeness.setText("Good");
     * <p>
     * break;
     * case 3:
     * politeness.setText("Medium");
     * <p>
     * break;
     * case 4:
     * politeness.setText("Bad");
     * <p>
     * break;
     * case 5:
     * politeness.setText("Unacceptable");
     * <p>
     * break;
     * }
     * switch (valueAverage) {
     * case 1:
     * value.setText("Excellent");
     * break;
     * <p>
     * case 2:
     * value.setText("Good");
     * <p>
     * break;
     * case 3:
     * value.setText("Medium");
     * <p>
     * break;
     * case 4:
     * value.setText("Bad");
     * <p>
     * break;
     * case 5:
     * value.setText("Unacceptable");
     * <p>
     * break;
     * }
     * switch (discountAverage) {
     * case 1:
     * discount.setText("Generous");
     * break;
     * <p>
     * case 2:
     * discount.setText("Good");
     * <p>
     * break;
     * case 3:
     * discount.setText("Normal");
     * <p>
     * break;
     * case 4:
     * discount.setText("Limited");
     * <p>
     * break;
     * case 5:
     * discount.setText("None");
     * <p>
     * break;
     * }
     * <p>
     * }
     */
    private void setAverageRating() {


        switch (rating.getQualityOfService()) {
            case 1:
                quality.setText("Excellent");
                break;

            case 2:
                quality.setText("Good");

                break;
            case 3:
                quality.setText("Medium");

                break;
            case 4:
                quality.setText("Bad");

                break;
            case 5:
                quality.setText("Unacceptable");

                break;
        }
        switch (rating.getPolitenessTheKindness()) {
            case 1:
                politeness.setText("Excellent");
                break;

            case 2:
                politeness.setText("Good");

                break;
            case 3:
                politeness.setText("Medium");

                break;
            case 4:
                politeness.setText("Bad");

                break;
            case 5:
                politeness.setText("Unacceptable");

                break;
        }
        switch (rating.getValueForMoney()) {
            case 1:
                value.setText("Excellent");
                break;

            case 2:
                value.setText("Good");

                break;
            case 3:
                value.setText("Medium");

                break;
            case 4:
                value.setText("Bad");

                break;
            case 5:
                value.setText("Unacceptable");

                break;
        }
        switch (rating.getDiscountPolicyAsset()) {
            case 1:
                discount.setText("Generous");
                break;

            case 2:
                discount.setText("Good");

                break;
            case 3:
                discount.setText("Normal");

                break;
            case 4:
                discount.setText("Limited");

                break;
            case 5:
                discount.setText("None");

                break;
        }

    }

    private void getBusiness() {
        for (Business business : AllData.businessList) {
            if (business.getBusinessName().equals(businessName)) {
                this.business = business;
                return;
            }
        }
    }


    private void initWidgets() {
        businessNameTxtView = findViewById(R.id.businessNameTxtView);
        quality = findViewById(R.id.quality);
        politeness = findViewById(R.id.politeness);
        value = findViewById(R.id.value);
        discount = findViewById(R.id.discount);

        //////////////////////BACKENDLESS//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        relativeLayout = findViewById(R.id.relativeLayout);

    }

    /////////////////////BACKENDLESS////////////////////////////
    private void showProgress(boolean show) {
        if (show) {
            load.setVisibility(View.VISIBLE);
            loadTxtView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);

        } else {
            load.setVisibility(View.GONE);
            loadTxtView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);


        }

    }

}
