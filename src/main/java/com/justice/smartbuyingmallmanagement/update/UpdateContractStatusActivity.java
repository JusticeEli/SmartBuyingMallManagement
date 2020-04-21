package com.justice.smartbuyingmallmanagement.update;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.data.AllData;

public class UpdateContractStatusActivity extends AppCompatActivity {
    private String businessName;
    private Business business;


    private TextView regIdTxtView;
    private Spinner statusSpinner;
    private Button saveBtn;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contract_status);
        businessName = getIntent().getStringExtra("businessName");
        business = getBusiness();
        initWidgets();
        setAdapters();
        setDefaultValue();
        setOnClickListeners();
    }

    private void setAdapters() {
        String status[]={"Pending","Approved","Denied"};
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,status);
        statusSpinner.setAdapter(spinnerAdapter);

    }

    ///////////////BACKENDLESS/////////////////////////////////
    private void putIntoDatabase() {
        Business copyBusiness = new Business();
        copyBusiness.setEntity(business.getEntity());
        copyBusiness.setBusinessOwnerEmail(business.getBusinessOwnerEmail());
        copyBusiness.setLongtide(business.getLongtide());
        copyBusiness.setLat(business.getLat());
        copyBusiness.setAddress(business.getAddress());
        copyBusiness.setCity(business.getCity());
        copyBusiness.setLocality(business.getLocality());
        copyBusiness.setMobile(business.getMobile());
        copyBusiness.setProductServiceDescription(business.getProductServiceDescription());
        copyBusiness.setRegId(business.getRegId());
        copyBusiness.setBusinessName(business.getBusinessName());
        copyBusiness.setOffer(business.getOffer());
        copyBusiness.setOfferUpto(business.getOfferUpto());


        Backendless.Persistence.of(Business.class).save(copyBusiness, new AsyncCallback<Business>() {
            @Override
            public void handleResponse(Business response) {

                showProgress(false);
                Toast.makeText(UpdateContractStatusActivity.this, "Updated", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void handleFault(BackendlessFault fault) {

                showProgress(false);
                Toast.makeText(UpdateContractStatusActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void setDefaultValue() {
        setSpinnerDefaultValue();

    }

    private void setSpinnerDefaultValue() {
        String b=business.getStatus();
        switch (business.getStatus()) {
            case "Pending":
                statusSpinner.setSelection(0);
                break;
            case "Approved":
                statusSpinner.setSelection(1);
                break;
            case "Denied":
                statusSpinner.setSelection(2);
                break;
                default:
                    statusSpinner.setSelection(0);
                    break;

        }


    }

    private Business getBusiness() {
        for (Business business : AllData.businessList) {
            if (business.getBusinessName().equals(businessName)) {
                this.business = business;
                return business;
            }
        }
        return null;
    }

    private void setOnClickListeners() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                business.setStatus(statusSpinner.getSelectedItem().toString());
                //   removeFromDatabase();
                appDateInDatabase();

                AllData.writeAllDataToFiles();
                onBackPressed();
            }
        });
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void appDateInDatabase() {

        Backendless.Persistence.save(business, new AsyncCallback<Business>() {
            @Override
            public void handleResponse(Business response) {

                showProgress(false);
                Toast.makeText(UpdateContractStatusActivity.this, "Updated", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void handleFault(BackendlessFault fault) {

                showProgress(false);
                Toast.makeText(UpdateContractStatusActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void removeFromDatabase() {

        String whereClause = "businessName = '" + businessName + "'";
        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
        dataQueryBuilder.setWhereClause(whereClause);
        Backendless.Persistence.of(Business.class).remove(business, new AsyncCallback<Long>() {
            @Override
            public void handleResponse(Long response) {
                Toast.makeText(UpdateContractStatusActivity.this, "Deleting: ", Toast.LENGTH_SHORT).show();
                //   putIntoDatabase();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(UpdateContractStatusActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initWidgets() {
        regIdTxtView = findViewById(R.id.regIdTxtView);
        statusSpinner = findViewById(R.id.statusSpinner);
        saveBtn = findViewById(R.id.saveBtn);

        ////////////////////BACKENDLESS//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        linearLayout = findViewById(R.id.linearLayout);


    }

    /////////////////////BACKENDLESS////////////////////////////
    private void showProgress(boolean show) {
        if (show) {
            load.setVisibility(View.VISIBLE);
            loadTxtView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);

        } else {
            load.setVisibility(View.GONE);
            loadTxtView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }

    }
}
