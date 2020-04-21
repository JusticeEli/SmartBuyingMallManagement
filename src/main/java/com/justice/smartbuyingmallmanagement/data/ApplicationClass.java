package com.justice.smartbuyingmallmanagement.data;

import android.app.Application;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.backendless.Backendless;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.justice.smartbuyingmallmanagement.Rating;
import com.justice.smartbuyingmallmanagement.admin.AdminLogin;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.business.BusinessOwner;
import com.justice.smartbuyingmallmanagement.business.BusinessOwnerLogin;
import com.justice.smartbuyingmallmanagement.user.UserLogin;

import java.util.ArrayList;
import java.util.List;


public class ApplicationClass extends Application {

    public static final String APPLICATION_ID = "52258DF2-BD97-271B-FFAC-977CDC99AF00";
    public static final String API_KEY = "A819B15F-7F34-468C-A424-1E20688C94A0";
    public static final String SERVER_URL = "https://api.backendless.com";
    // public static final String SERVER_URL = "66.114.116.4";


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(getApplicationContext(),
                APPLICATION_ID,
                API_KEY);

////////////////DEALING WITH FILES//////////////////
        AllData.createDirectory();
        //   AllData.readAllDataFromFiles();

        ////////////////////////DATABASE/////////////////
        //   createAllTables();
      //  readAllDataFromDataBase();
        if (AllData.adminLoginList.isEmpty()) {
            //    putDummyData();
        }

    }

    //////////////CREATE ALL TABLES WITH DEFAULT VALUES IN THE DATABASE//////////////////
    private void createAllTables() {
        Business business = new Business(2, "applied", "mobile development", "43", "we do app developement for business in", "797", "4567", "Nairobi", "luckySummer", "345678", "6578", "644");
        BusinessOwner businessOwner = new BusinessOwner("justice eli", "j", "j", "07488383838", "takeme", "456786", "789765");
        Rating rating = new Rating("mobile development", "", 0, 0, 0, 0);
        Backendless.Persistence.of(Business.class).save(business, new AsyncCallback<Business>() {
            @Override
            public void handleResponse(Business response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
        Backendless.Persistence.of(BusinessOwner.class).save(businessOwner, new AsyncCallback<BusinessOwner>() {
            @Override
            public void handleResponse(BusinessOwner response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
        Backendless.Persistence.of(Rating.class).save(rating, new AsyncCallback<Rating>() {
            @Override
            public void handleResponse(Rating response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
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

        Backendless.Persistence.of(Business.class).find(dataQueryBuilder, new AsyncCallback<List<Business>>() {
            @Override
            public void handleResponse(List<Business> response) {
                AllData.businessList = response;
                Toast.makeText(ApplicationClass.this, "Business List loaded : success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ApplicationClass.this, "Error:" + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        Backendless.Persistence.of(Rating.class).find(dataQueryBuilder, new AsyncCallback<List<Rating>>() {
            @Override
            public void handleResponse(List<Rating> response) {
                AllData.ratingList = response;
                Toast.makeText(ApplicationClass.this, "success", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ApplicationClass.this, "Error:" + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        Backendless.Persistence.of(UserLogin.class).find(dataQueryBuilder, new AsyncCallback<List<UserLogin>>() {
            @Override
            public void handleResponse(List<UserLogin> response) {
                AllData.userLoginList = response;
                Toast.makeText(ApplicationClass.this, "success", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ApplicationClass.this, "Error:" + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        ///THE BUG IS WHEN I CREATED A BUSINESS IN THE PAST AND A USER HAD NOT YET REGISTERED THE USER RATING LIST WILL NOT HAVE THE BUSINESS///////
        //////////WHAT I CAN DO IS AFTER GETTING ALL THE BUSINESS FROM DATABASE I CAN CHECK MY LOGIN LIST TO CHECK IF THE BUSINESS ARE AVAILABLE IS NOT ADD THERE RATING TO THE RATING LIST ////////////


    }


    @Override
    public void onTerminate() {
        AllData.writeAllDataToFiles();
        super.onTerminate();

    }

    private void putDummyData() {

        AllData.businessOwnerList.add(new BusinessOwner("justice eli", "j", "j", "07488383838", "takeme", "456786", "789765"));
        AllData.businessOwnerList.add(new BusinessOwner("Suzaan awino", "s", "s", "4358768868", "home", "0987", "243987"));
        Business business = new Business(2, "applied", "mobile development", "43", "we do app developement for business in", "797", "4567", "Nairobi", "luckySummer", "345678", "6578", "644");
        business.setBusinessOwnerEmail("j");
        AllData.businessList.add(business);
        AllData.businessOwnerLoginList.add(new BusinessOwnerLogin("j", "j"));
        AllData.businessOwnerLoginList.add(new BusinessOwnerLogin("e", "e"));
        AllData.adminLoginList.add(new AdminLogin("j", "j"));
        AllData.adminLoginList.add(new AdminLogin("e", "e"));
        AllData.userLoginList.add(new UserLogin("j", "j", new ArrayList<Rating>()));
        AllData.userLoginList.add(new UserLogin("e", "e", new ArrayList<Rating>()));
        AllData.ratingList.add(new Rating("mobile development", "", 0, 0, 0, 0));
        AllData.userLoginList.get(0).getUserRatingList().add(new Rating("mobile development"));
        AllData.userLoginList.get(1).getUserRatingList().add(new Rating("mobile development"));

    }


}
