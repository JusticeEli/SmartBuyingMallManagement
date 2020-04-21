package com.justice.smartbuyingmallmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.justice.smartbuyingmallmanagement.data.AllData;
import com.justice.smartbuyingmallmanagement.user.UserLogin;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    ///////////RADIO ...GROUPS/////////////////
    private RadioGroup qualityOfServiceRadioGroup;
    private RadioGroup politenessTheKindnessRadioGroup;
    private RadioGroup valueForMoneyRadioGroup;
    private RadioGroup discountPolicyAssetRadioGroup;


    ///////////////RADIO BUTTONS////////////////

    ////QUALITY
    private RadioButton qualityRadioE;
    private RadioButton qualityRadioG;
    private RadioButton qualityRadioM;
    private RadioButton qualityRadioB;
    private RadioButton qualityRadioU;
    ///////POLITENESS////////
    private RadioButton politenessRadioE;
    private RadioButton politenessRadioG;
    private RadioButton politenessRadioM;
    private RadioButton politenessRadioB;
    private RadioButton politenessRadioU;
    ////////////VALUE////////////////
    private RadioButton valueRadioE;
    private RadioButton valueRadioG;
    private RadioButton valueRadioM;
    private RadioButton valueRadioB;
    private RadioButton valueRadioU;
    /////////DISOUNT////////////////
    private RadioButton discountRadioGenerous;
    private RadioButton discountRadioGood;
    private RadioButton discountRadioNormal;
    private RadioButton discountRadioLimited;
    private RadioButton discountRadioNone;


    private Button submitBtn;
    private UserLogin user;
    private String email;
    private String businessName;
    private Rating rating;


    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        email = getIntent().getStringExtra("email");
        businessName = getIntent().getStringExtra("businessName");

        initWidgets();
        initWidgetsRadioButtons();

        rating = getRating();

        //   searchForTheRating();
        setDefaultValues();


        setOnClickListeners();
    }

    private Rating getOriginalRating() {
        for (Rating rating : UserLogin.currentUserRatingList) {
            String n1 = rating.getBusinessName();
            String n2 = businessName;
            if (rating.getBusinessName().equals(businessName) && rating.getEmail().equals(email)) {
                return rating;
            }
        }


        return null;

    }

    private Rating getRating() {
        for (Rating rating : UserLogin.currentUserRatingList) {
            String n1 = rating.getBusinessName();
            String n2 = businessName;
            if (rating.getBusinessName().equals(businessName)) {
                return rating;
            }
        }


        return null;

    }

    private void initWidgetsRadioButtons() {
        qualityRadioE = findViewById(R.id.qualityOfTheServiceExcellent);
        qualityRadioG = findViewById(R.id.qualityOfTheServiceGood);
        qualityRadioM = findViewById(R.id.qualityOfTheServiceMedium);
        qualityRadioB = findViewById(R.id.qualityOfTheServiceBad);
        qualityRadioU = findViewById(R.id.qualityOfTheServiceUnacceptable);

        politenessRadioE = findViewById(R.id.politenessExcellent);
        politenessRadioG = findViewById(R.id.politenessGood);
        politenessRadioM = findViewById(R.id.politenessMedium);
        politenessRadioB = findViewById(R.id.politenessBad);
        politenessRadioU = findViewById(R.id.politenessUnacceptable);

        valueRadioE = findViewById(R.id.valueForMoneyExcellent);
        valueRadioG = findViewById(R.id.valueForMoneyGood);
        valueRadioM = findViewById(R.id.valueForMoneyMedium);
        valueRadioB = findViewById(R.id.valueForMoneyBad);
        valueRadioU = findViewById(R.id.valueForMoneyUnacceptable);

        discountRadioGenerous = findViewById(R.id.discountPolicyGenerous);
        discountRadioGood = findViewById(R.id.discountPolicyGood);
        discountRadioNormal = findViewById(R.id.discountPolicyNormal);
        discountRadioLimited = findViewById(R.id.discountPolicyLimited);
        discountRadioNone = findViewById(R.id.discountPolicyNone);

    }

    private void setDefaultValues() {
        switch (rating.getQualityOfService()) {
            case 1:
                qualityRadioE.setChecked(true);
                break;
            case 2:
                qualityRadioG.setChecked(true);
                break;
            case 3:
                qualityRadioM.setChecked(true);
                break;
            case 4:
                qualityRadioB.setChecked(true);
                break;
            case 5:
                qualityRadioU.setChecked(true);
                break;
        }

        switch (rating.getPolitenessTheKindness()) {
            case 1:
                politenessRadioE.setChecked(true);
                break;
            case 2:
                politenessRadioG.setChecked(true);
                break;
            case 3:
                politenessRadioM.setChecked(true);
                break;
            case 4:
                politenessRadioB.setChecked(true);
                break;
            case 5:
                politenessRadioU.setChecked(true);
                break;
        }
        switch (rating.getValueForMoney()) {
            case 1:
                valueRadioE.setChecked(true);
                break;
            case 2:
                valueRadioG.setChecked(true);
                break;
            case 3:
                valueRadioM.setChecked(true);
                break;
            case 4:
                valueRadioB.setChecked(true);
                break;
            case 5:
                valueRadioU.setChecked(true);
                break;
        }
        switch (rating.getDiscountPolicyAsset()) {
            case 1:
                discountRadioGenerous.setChecked(true);
                break;
            case 2:
                discountRadioGood.setChecked(true);
                break;
            case 3:
                discountRadioNormal.setChecked(true);
                break;
            case 4:
                discountRadioLimited.setChecked(true);
                break;
            case 5:
                discountRadioNone.setChecked(true);
                break;
        }
    }

    private void searchForTheRating() {
        // TODO: 25-Feb-20 EVERY CREATED PERSON SHOULD HAVE A RATINGList OF ALL THE BUSINESSNAME/////////////

        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
        dataQueryBuilder.setWhereClause("email = '" + email + "'");
        showProgress(true);
        Backendless.Persistence.of(Rating.class).find(dataQueryBuilder, new AsyncCallback<List<Rating>>() {
            @Override
            public void handleResponse(List<Rating> response) {
                showProgress(false);
                UserLogin.currentUserRatingList = response;
                Toast.makeText(QuestionActivity.this, "Finished loading user Rating", Toast.LENGTH_SHORT).show();
                for (Rating rating : UserLogin.currentUserRatingList) {
                    if (rating.getBusinessName().equals(businessName)) {
                        QuestionActivity.this.rating = rating;
                        break;
                    }
                }

                setDefaultValues();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
                Toast.makeText(QuestionActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    //////////////LIKELY SOURCE OF ERROR///////////////////////
    private void removeTheCurrentRating() {
        String whereClause = "businessName = '" + rating.getBusinessName() + "' AND " + "email ='" + rating.getEmail() + "'";
        Backendless.Persistence.of(Rating.class).remove(whereClause, new AsyncCallback<Integer>() {
            @Override
            public void handleResponse(Integer response) {
                Toast.makeText(QuestionActivity.this, "Deleted .......", Toast.LENGTH_SHORT).show();
                addTheUpdate();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(QuestionActivity.this, "Error:." + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTheUpdate() {
        rating.setFlag("updated");
        showProgress(true);
        Backendless.Data.save(rating, new AsyncCallback<Rating>() {
            @Override
            public void handleResponse(Rating response) {
                showProgress(false);
                Toast.makeText(QuestionActivity.this, "Updated Successfully...", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
                Toast.makeText(QuestionActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setOnClickListeners() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllData.writeAllDataToFiles();
                setRadioGroupListeners();
                addTheUpdate();
                // removeCurrentRatingAndAddTheUpdate();
                Toast.makeText(QuestionActivity.this, "Submitted Successfully...", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });


    }

    private void removeCurrentRatingAndAddTheUpdate() {
        removeTheCurrentRating();
    }

    private void setRadioGroupListeners() {
        /////////////////////////////////////////////////////////
        switch (qualityOfServiceRadioGroup.getCheckedRadioButtonId()) {

            case R.id.qualityOfTheServiceExcellent:
                rating.setQualityOfService(1);


                break;
            case R.id.qualityOfTheServiceGood:
                rating.setQualityOfService(2);


                break;
            case R.id.qualityOfTheServiceMedium:
                rating.setQualityOfService(3);


                break;
            case R.id.qualityOfTheServiceBad:
                rating.setQualityOfService(4);


                break;
            case R.id.qualityOfTheServiceUnacceptable:
                rating.setQualityOfService(5);


                break;
        }

        //////////////////////////////////////////////
        switch (politenessTheKindnessRadioGroup.getCheckedRadioButtonId()) {

            case R.id.politenessExcellent:
                rating.setPolitenessTheKindness(1);

                break;
            case R.id.politenessGood:
                rating.setPolitenessTheKindness(2);

                break;
            case R.id.politenessMedium:
                rating.setPolitenessTheKindness(3);

                break;
            case R.id.politenessBad:
                rating.setPolitenessTheKindness(4);

                break;
            case R.id.politenessUnacceptable:
                rating.setPolitenessTheKindness(5);

                break;
        }
        //////////////////////////////////////////////////////////////////

        switch (valueForMoneyRadioGroup.getCheckedRadioButtonId()) {

            case R.id.valueForMoneyExcellent:
                rating.setValueForMoney(1);


                break;
            case R.id.valueForMoneyGood:
                rating.setValueForMoney(2);


                break;
            case R.id.valueForMoneyMedium:
                rating.setValueForMoney(3);


                break;
            case R.id.valueForMoneyBad:
                rating.setValueForMoney(4);


                break;
            case R.id.valueForMoneyUnacceptable:
                rating.setValueForMoney(5);


                break;
        }
        //////////////////////////////////////////////////////////////////

        switch (discountPolicyAssetRadioGroup.getCheckedRadioButtonId()) {

            case R.id.discountPolicyGenerous:
                rating.setDiscountPolicyAsset(1);


                break;
            case R.id.discountPolicyGood:
                rating.setDiscountPolicyAsset(2);


                break;
            case R.id.discountPolicyNormal:
                rating.setDiscountPolicyAsset(3);


                break;
            case R.id.discountPolicyLimited:
                rating.setDiscountPolicyAsset(4);

                break;
            case R.id.discountPolicyNone:
                rating.setDiscountPolicyAsset(5);

                break;
        }

    }

    private void initWidgets() {
        qualityOfServiceRadioGroup = findViewById(R.id.qualityOfTheServiceRadioGroup);
        politenessTheKindnessRadioGroup = findViewById(R.id.politenessRadioGroup);
        valueForMoneyRadioGroup = findViewById(R.id.valueForMoneyRadioGroup);
        discountPolicyAssetRadioGroup = findViewById(R.id.discountPolicyRadioGroup);

        submitBtn = findViewById(R.id.submitBtn);

        /////////////////////BACKENDLESS//////////////////////
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
