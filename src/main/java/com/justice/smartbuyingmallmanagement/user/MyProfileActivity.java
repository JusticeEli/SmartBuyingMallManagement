package com.justice.smartbuyingmallmanagement.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.justice.smartbuyingmallmanagement.R;

public class MyProfileActivity extends AppCompatActivity {
    private TextView emailTxtView;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        email=getIntent().getStringExtra("email");
        initWidgets();
        setDefaultValues();
    }

    private void setDefaultValues() {
        emailTxtView.setText(email);
    }

    private void initWidgets() {
        emailTxtView=findViewById(R.id.emailTxtView);
    }
}
