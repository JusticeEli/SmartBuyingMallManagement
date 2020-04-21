package com.justice.smartbuyingmallmanagement.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.data.AllData;

import java.util.ArrayList;
import java.util.List;

public class ViewDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText searchEdtTxt;

    private ViewDetailsActivityRecyclerAdapter viewDetailsActivityRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        initWidgets();
        setOnClickListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewDetailsActivityRecyclerAdapter.setList(AllData.businessList);
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
                    viewDetailsActivityRecyclerAdapter.setList(AllData.businessList);
                    return;

                }
                list.clear();
                for (Business business:AllData.businessList){
                    if(business.getBusinessName().toLowerCase().contains(searchEdtTxt.getText().toString())){
                        if(!list.contains(business)){
                            list.add(business);

                        }
                    }
                }
                viewDetailsActivityRecyclerAdapter.setList(list);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void initWidgets() {
        if (AllData.businessList.isEmpty()){
            Toast.makeText(this, "Wait for businesses to Load OR No business is currently Available!!", Toast.LENGTH_SHORT).show();
            finish();
        }
        viewDetailsActivityRecyclerAdapter=new ViewDetailsActivityRecyclerAdapter(this,AllData.businessList);
        searchEdtTxt=findViewById(R.id.searchEdtTxt);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(viewDetailsActivityRecyclerAdapter);
    }
}
