package com.justice.smartbuyingmallmanagement.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.navigation.NavigationView;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.business.BusinessOwnerFirstPageActivity;
import com.justice.smartbuyingmallmanagement.data.AllData;
import com.justice.smartbuyingmallmanagement.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewBusinessDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText searchEdtTxt;
    private RecyclerView recyclerView;
    private ViewBusinessDetailsActivityRecyclerAdapter viewBusinessDetailsActivityRecyclerAdapter;
    private String email;
    private List<Business> businessList = new ArrayList<>();
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
        setContentView(R.layout.activity_view_business_details);

        email = getIntent().getStringExtra("email");

        initWidgets();
        // getBusinessListFromDataBase();
        getBusinessListAndSetItToAdapter();
        setOnClickListeners();
        initNavigationDrawer();
    }

    private void getBusinessListFromDataBase() {
        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();

        showProgress(true);
        Backendless.Persistence.of(Business.class).find(dataQueryBuilder, new AsyncCallback<List<Business>>() {
            @Override
            public void handleResponse(List<Business> response) {
                showProgress(false);
                AllData.businessList = response;
                getBusinessListAndSetItToAdapter();
                Toast.makeText(ViewBusinessDetailsActivity.this, "Business List loaded : success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ViewBusinessDetailsActivity.this, "Error:" + fault.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });

    }


    private void getBusinessListAndSetItToAdapter() {
        if (!AllData.businessList.isEmpty()) {
            for (Business business : AllData.businessList) {
                if (business.getBusinessOwnerEmail().equals(email)) {
                    businessList.add(business);
                }
            }

        }
        viewBusinessDetailsActivityRecyclerAdapter = new ViewBusinessDetailsActivityRecyclerAdapter(this, businessList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(viewBusinessDetailsActivityRecyclerAdapter);


    }

    private void setOnClickListeners() {
        searchEdtTxt.addTextChangedListener(new TextWatcher() {
            List<Business> list = new ArrayList<>();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchEdtTxt.getText().toString().isEmpty()) {
                    viewBusinessDetailsActivityRecyclerAdapter.setList(businessList);
                    return;

                }
                list.clear();
                for (Business business : businessList) {
                    if (business.getBusinessName().toLowerCase().contains(searchEdtTxt.getText().toString())) {
                        if (!list.contains(business)) {
                            list.add(business);

                        }
                    }
                }
                viewBusinessDetailsActivityRecyclerAdapter.setList(list);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initWidgets() {

        drawerLayout = findViewById(R.id.drawer);
        searchEdtTxt = findViewById(R.id.searchEdtTxt);
        recyclerView = findViewById(R.id.recyclerView);


////////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);


    }

    /////////////////////PROGRESS_BAR////////////////////////////
    public void showProgress(boolean show) {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ViewBusinessDetailsActivityRecyclerAdapter.PICKER_REQUEST && requestCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(data, this);
            String latitude = String.valueOf(place.getLatLng().latitude);
            String longitude = String.valueOf(place.getLatLng().longitude);
            String businessName = data.getStringExtra("businessName");

            setTheLatLongitude(businessName, latitude, longitude);


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewBusinessDetailsActivityRecyclerAdapter = new ViewBusinessDetailsActivityRecyclerAdapter(this, businessList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(viewBusinessDetailsActivityRecyclerAdapter);


    }

    private void setTheLatLongitude(String businessName, String latitude, String longitude) {
        for (Business business : businessList) {
            if (business.getBusinessName().equals(businessName)) {
                business.setLat(latitude);
                business.setLongtide(longitude);
                viewBusinessDetailsActivityRecyclerAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

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
                Intent intent = new Intent(this, BusinessOwnerFirstPageActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();

                break;
            case R.id.aboutUsMenu:
                Toast.makeText(this, "Page Not Yet Set Up!!", Toast.LENGTH_SHORT).show();

                break;
            case R.id.logoutMenu:
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Are you Sure!!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ViewBusinessDetailsActivity.this, MainActivity.class));
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
