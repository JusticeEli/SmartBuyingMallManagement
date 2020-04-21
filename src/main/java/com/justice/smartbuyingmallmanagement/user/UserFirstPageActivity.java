package com.justice.smartbuyingmallmanagement.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.justice.smartbuyingmallmanagement.MyRatingActivity;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.search.SearchOfferActivity;

public class UserFirstPageActivity extends AppCompatActivity {
    private String email;

    private LinearLayout findShopServiceLinLayout;
    private LinearLayout myRatingLinLayout;
    private LinearLayout myProfileLinLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_first_page);
        email = getIntent().getStringExtra("email");
        initWidgets();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        findShopServiceLinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserFirstPageActivity.this, SearchOfferActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);

            }
        });

        myRatingLinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserFirstPageActivity.this, MyRatingActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);


            }
        });
        myProfileLinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserFirstPageActivity.this, MyProfileActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);

            }
        });
    }

    private void initWidgets() {
        findShopServiceLinLayout = findViewById(R.id.findShopServicesLinLayout);
        myRatingLinLayout = findViewById(R.id.updateBusinessLinLayout);
        myProfileLinLayout = findViewById(R.id.myProfileLinLayout);
    }
}
