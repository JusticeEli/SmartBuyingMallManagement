package com.justice.smartbuyingmallmanagement.business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.admin.AdminLoginFirstPageActivity;
import com.justice.smartbuyingmallmanagement.main.MainActivity;
import com.justice.smartbuyingmallmanagement.view.ViewBusinessDetailsActivity;

public class BusinessOwnerFirstPageActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout createBusinessLinLayout;
    private LinearLayout updateBusinessLinLayout;
    private String email;

    /////////////NAVIGATION DRAWER//////////////////////
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_owner_first_page);
        email = getIntent().getStringExtra("email");
        initWidgets();
        setOnClickListeners();
        initNavigationDrawer();
    }

    private void setOnClickListeners() {
        createBusinessLinLayout.setOnClickListener(this);
        updateBusinessLinLayout.setOnClickListener(this);
    }

    private void initWidgets() {
        createBusinessLinLayout = findViewById(R.id.createBusinessLinLayout);
        updateBusinessLinLayout = findViewById(R.id.updateBusinessLinLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.createBusinessLinLayout:
                Intent intent = new Intent(BusinessOwnerFirstPageActivity.this, AddBusinessActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                break;
            case R.id.updateBusinessLinLayout:
                Intent intent1 = new Intent(BusinessOwnerFirstPageActivity.this, ViewBusinessDetailsActivity.class);
                intent1.putExtra("email", email);
                startActivity(intent1);
                break;
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
            case R.id.contactUsMenu:
                Toast.makeText(this, "Page not Available yet!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.termsAndConditionsMenu:
                Toast.makeText(this, "Page Not Yet Set Up!!", Toast.LENGTH_SHORT).show();

                break;
            case R.id.versionMenu:
                Toast.makeText(this, "Page Not Yet Set Up!!", Toast.LENGTH_SHORT).show();

                break;

            case R.id.logoutMenu:
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Are you Sure!!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(BusinessOwnerFirstPageActivity.this, MainActivity.class));
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
