package com.justice.smartbuyingmallmanagement.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.data.AllData;

public class UserDetailsActivity extends AppCompatActivity {
    private TextView emailTxtView;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        email = getIntent().getStringExtra("email");
        initWidgets();
        setDefaultValues();
    }

    private void setDefaultValues() {
        emailTxtView.setText(email);
    }

    private void initWidgets() {
        emailTxtView = findViewById(R.id.emailTxtView);
    }
}
