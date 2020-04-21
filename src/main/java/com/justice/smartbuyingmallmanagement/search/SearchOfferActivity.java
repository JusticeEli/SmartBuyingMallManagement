package com.justice.smartbuyingmallmanagement.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.Rating;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.data.AllData;
import com.justice.smartbuyingmallmanagement.user.UserLogin;

import java.util.ArrayList;
import java.util.List;

public class SearchOfferActivity extends AppCompatActivity {
    private SearchOfferActivityRecyclerAdapter searchOfferActivityRecyclerAdapter;
    private RecyclerView recyclerView;
    private EditText searchNameEdtTxt;
    private EditText searchCityEdtTxt;
    private EditText searchLocalityEdtTxt;

    private String email;

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
        setContentView(R.layout.activity_search_offer);
        email = getIntent().getStringExtra("email");
        initWidgets();
        setOnClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search_offer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync:
                syncTheRatings();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void syncTheRatings() {
        /////////////////////////////////////////
        for(Rating rating:AllData.ratingList){

            for(UserLogin userLogin:AllData.userLoginList){
                if(userLogin.getEmail().equals(rating.getEmail())){
                    userLogin.getUserRatingList().add(rating);
                    break;
                }
            }
        }

        //////////////////////////////////////////
        for (Business business : AllData.businessList) {
            for (UserLogin userLogin : AllData.userLoginList) {
                for (Rating rating : userLogin.getUserRatingList()) {
                    if (rating.getBusinessName().equals(business.getBusinessName())) {
                        qualityTotal += rating.getQualityOfService();
                        politenessTotal += rating.getPolitenessTheKindness();
                        valueTotal += rating.getValueForMoney();
                        discountTotal += rating.getDiscountPolicyAsset();
                    }
                }
            }
            findTheAverageForTheRatings(business.getBusinessName());
        }

        Toast.makeText(this, "Syncing Complete", Toast.LENGTH_SHORT).show();
    }

    private void findTheAverageForTheRatings(String businessName) {
        qualityAverage = qualityTotal / AllData.userLoginList.size();
        politenessAverage = politenessTotal / AllData.userLoginList.size();
        valueAverage = valueTotal / AllData.userLoginList.size();
        discountAverage = discountTotal / AllData.userLoginList.size();

        for (Rating rating : AllData.ratingList) {
            if (rating.getBusinessName().equals(businessName)&&rating.getEmail().equals("average")) {
                rating.setQualityOfService(qualityAverage);
                rating.setPolitenessTheKindness(politenessAverage);
                rating.setValueForMoney(valueAverage);
                rating.setDiscountPolicyAsset(discountAverage);


                Backendless.Persistence.save(rating, new AsyncCallback<Rating>() {
                    @Override
                    public void handleResponse(Rating response) {
                        Toast.makeText(SearchOfferActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(SearchOfferActivity.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });



            }
        }

        qualityTotal = 0;
        politenessTotal = 0;
        valueTotal = 0;
        discountTotal = 0;
        //////////////
        qualityAverage = 0;
        politenessAverage = 0;
        valueAverage = 0;
        discountAverage = 0;
    }


    public String getEmail() {
        return email;
    }

    private void setOnClickListeners() {
//////////////////SEARCH_NAME_EDT_TXT////////////////////////////////
        searchNameEdtTxt.addTextChangedListener(new TextWatcher() {
            List<Business> list = new ArrayList<>();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchNameEdtTxt.getText().toString().isEmpty()) {
                    searchOfferActivityRecyclerAdapter.setList(AllData.businessList);
                    return;

                }
                list.clear();
                for (Business business : AllData.businessList) {
                    if (business.getBusinessName().toLowerCase().contains(searchNameEdtTxt.getText().toString())) {
                        if (!list.contains(business)) {
                            list.add(business);

                        }
                    }
                }
                searchOfferActivityRecyclerAdapter.setList(list);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//////////////////////SEARCH_CITY_EDT_TXT///////////////////////////
        searchCityEdtTxt.addTextChangedListener(new TextWatcher() {
            List<Business> list = new ArrayList<>();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchCityEdtTxt.getText().toString().isEmpty()) {
                    searchOfferActivityRecyclerAdapter.setList(AllData.businessList);
                    return;

                }
                list.clear();
                for (Business business : AllData.businessList) {
                    if (business.getCity().toLowerCase().contains(searchCityEdtTxt.getText().toString())) {
                        if (!list.contains(business)) {
                            list.add(business);

                        }
                    }
                }
                searchOfferActivityRecyclerAdapter.setList(list);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//////////////////////SEARCH_LOCALITY_EDT_TXT///////////////////////////
        searchLocalityEdtTxt.addTextChangedListener(new TextWatcher() {
            List<Business> list = new ArrayList<>();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchLocalityEdtTxt.getText().toString().isEmpty()) {
                    searchOfferActivityRecyclerAdapter.setList(AllData.businessList);
                    return;

                }
                list.clear();
                for (Business business : AllData.businessList) {
                    if (business.getLocality().toLowerCase().contains(searchLocalityEdtTxt.getText().toString())) {
                        if (!list.contains(business)) {
                            list.add(business);

                        }
                    }
                }
                searchOfferActivityRecyclerAdapter.setList(list);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void initWidgets() {
        recyclerView = findViewById(R.id.recyclerView);
        if (AllData.businessList.isEmpty()) {
            Toast.makeText(this, "No Shop/Service currently Available !!", Toast.LENGTH_SHORT).show();
            finish();
        }
        searchOfferActivityRecyclerAdapter = new SearchOfferActivityRecyclerAdapter(this, AllData.businessList);
        searchNameEdtTxt = findViewById(R.id.searchNameEdtTxt);
        searchCityEdtTxt = findViewById(R.id.searchCityEdtTxt);
        searchLocalityEdtTxt = findViewById(R.id.searchLocalityEdtTxt);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchOfferActivityRecyclerAdapter);

    }

}
