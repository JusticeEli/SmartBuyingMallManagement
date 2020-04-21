package com.justice.smartbuyingmallmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.google.android.material.navigation.NavigationView;
import com.justice.smartbuyingmallmanagement.data.AllData;
import com.justice.smartbuyingmallmanagement.main.MainActivity;
import com.justice.smartbuyingmallmanagement.user.UserFirstPageActivity;
import com.justice.smartbuyingmallmanagement.user.UserLogin;

import java.util.ArrayList;
import java.util.List;

public class MyRatingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText searchEdtTxt;
    private RecyclerView recyclerView;
    private MyRatingActivityRecyclerAdapter myRatingActivityRecyclerAdapter;

    private String email;
    private UserLogin user;

    //////////////////DRAWER LAYOUT////////////////////////
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rating);
        email = getIntent().getStringExtra("email");
        user = getUser();
        initWidgets();
        setOnClickListeners();
        initNavigationDrawer();
    }

    private UserLogin getUser() {
        for (UserLogin user : AllData.userLoginList) {
            if (user.getEmail().equals(email)) {
                String email1 = user.getEmail();
                return user;
            }
        }
        return null;
    }

    private void setOnClickListeners() {
        searchEdtTxt.addTextChangedListener(new TextWatcher() {
            List<Rating> list = new ArrayList<>();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchEdtTxt.getText().toString().isEmpty()) {
                    myRatingActivityRecyclerAdapter.setList(user.getUserRatingList());
                    return;

                }
                list.clear();
                for (Rating rating : user.getUserRatingList()) {
                    if (rating.getBusinessName().toLowerCase().contains(searchEdtTxt.getText().toString())) {
                        if (!list.contains(rating)) {
                            list.add(rating);

                        }
                    }
                }
                myRatingActivityRecyclerAdapter.setList(list);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initWidgets() {
        searchEdtTxt = findViewById(R.id.searchEdtTxt);
        recyclerView = findViewById(R.id.recyclerView);
        ////////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        recyclerView = findViewById(R.id.recyclerView);

        loadUserRatingList(email);
        if (UserLogin.currentUserRatingList.isEmpty()) {
            Toast.makeText(this, "User Rating List is Empty Add some business to the businessList..", Toast.LENGTH_SHORT).show();
            finish();
        }
        //  loadUserRatingList(email);
        loadUserRating(email);

    }

    private void loadUserRating(String email) {
        UserLogin.currentUserRatingList.clear();

        for (Rating rating : AllData.ratingList) {
            if (rating.getEmail().equals(email)) {
                UserLogin.currentUserRatingList.add(rating);
            }
        }


        myRatingActivityRecyclerAdapter = new MyRatingActivityRecyclerAdapter(MyRatingActivity.this, UserLogin.currentUserRatingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyRatingActivity.this));
        recyclerView.setAdapter(myRatingActivityRecyclerAdapter);


    }

    /////////////////////PROGRESS_BAR////////////////////////////
    private void showProgress(boolean show) {
        if (show) {
            load.setVisibility(View.VISIBLE);
            loadTxtView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        } else {
            load.setVisibility(View.GONE);
            loadTxtView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);


        }

    }


    private void loadUserRatingList(String email) {
        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
        dataQueryBuilder.setWhereClause("email = '" + email + "'");
        showProgress(true);
        Backendless.Persistence.of(Rating.class).find(dataQueryBuilder, new AsyncCallback<List<Rating>>() {
            @Override
            public void handleResponse(List<Rating> response) {
                showProgress(false);
                UserLogin.currentUserRatingList = response;
                Toast.makeText(MyRatingActivity.this, "Finished loading user Rating", Toast.LENGTH_SHORT).show();
                myRatingActivityRecyclerAdapter = new MyRatingActivityRecyclerAdapter(MyRatingActivity.this, UserLogin.currentUserRatingList);
                recyclerView.setLayoutManager(new LinearLayoutManager(MyRatingActivity.this));
                recyclerView.setAdapter(myRatingActivityRecyclerAdapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(MyRatingActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * private void loadUserRatingList (String email){
     * DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
     * dataQueryBuilder.setWhereClause("email = '" + email + "'");
     * Backendless.Persistence.of(Rating.class).find(dataQueryBuilder, new AsyncCallback<List<Rating>>() {
     *
     * @Override public void handleResponse(List<Rating> response) {
     * UserLogin.userRatingList = response;
     * Toast.makeText(MyRatingActivity.this, "Finished loading user Rating", Toast.LENGTH_SHORT).show();
     * }
     * @Override public void handleFault(BackendlessFault fault) {
     * Toast.makeText(MyRatingActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
     * }
     * });
     * }
     */

    ////////////////////////NAVIGATION DRAWER/////////////////////////////////////////////
    private void initNavigationDrawer() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.homeMenu:
                Intent intent = new Intent(this, UserFirstPageActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
                break;
            case R.id.aboutUsMenu:
                Toast.makeText(this, "Page Not Yet Available!!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logoutMenu:
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Are you Sure!!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MyRatingActivity.this, MainActivity.class));
                        finish();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer);

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
