package com.justice.smartbuyingmallmanagement.admin;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.business.BusinessOwnerLoginActivity;
import com.justice.smartbuyingmallmanagement.main.MainActivity;
import com.justice.smartbuyingmallmanagement.view.ViewDetailsActivity;

public class AdminLoginFirstPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String email;
    private LinearLayout approveBusinessLinearLayout;
    private LinearLayout userDetailsLinearLayout;

    //////////////////DRAWER LAYOUT////////////////////////
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_first_page);
        email = getIntent().getStringExtra("email");
        initWidgets();
        setOnClickListeners();
        initNavigationDrawer();

    }

    private void setOnClickListeners() {
        approveBusinessLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLoginFirstPageActivity.this, ViewDetailsActivity.class);
                startActivity(intent);

            }
        });
        userDetailsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminLoginFirstPageActivity.this, UserDetailsActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);

            }
        });
    }

    private void initWidgets() {
        approveBusinessLinearLayout = findViewById(R.id.approveBusinessLinearLayout);
        userDetailsLinearLayout = findViewById(R.id.userDetailsLinearLayout);
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
                Intent intent = new Intent(this, AdminLoginFirstPageActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
                break;
            case R.id.logoutMenu:
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Are you Sure!!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(AdminLoginFirstPageActivity.this, MainActivity.class));
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
