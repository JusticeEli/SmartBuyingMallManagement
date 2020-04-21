package com.justice.smartbuyingmallmanagement.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.Rating;
import com.justice.smartbuyingmallmanagement.data.AllData;
import com.justice.smartbuyingmallmanagement.update.UpdateActivity;
import com.justice.smartbuyingmallmanagement.business.Business;

import java.util.List;

public class ViewBusinessDetailsActivityRecyclerAdapter extends RecyclerView.Adapter<ViewBusinessDetailsActivityRecyclerAdapter.ViewHolder> {
    public static final int PICKER_REQUEST = 1;
    private ViewBusinessDetailsActivity viewBusinessDetailsActivity;
    private Context context;
    private List<Business> list;
    private String businessName;

    public ViewBusinessDetailsActivityRecyclerAdapter(Context context, List<Business> list) {
        viewBusinessDetailsActivity = (ViewBusinessDetailsActivity) context;
        this.context = context;
        this.list = list;
    }

    public void setList(List<Business> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_business_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.businessNameTxtView.setText(list.get(position).getBusinessName());
        holder.offerTxtView.setText(list.get(position).getOffer());
        holder.productServiceDescriptionTxtView.setText(list.get(position).getProductServiceDescription());
        holder.offerUptoTxtView.setText(list.get(position).getOfferUpto());
        holder.addressTxtView.setText(list.get(position).getAddress());
        holder.cityTxtView.setText(list.get(position).getCity());
        holder.localityTxtView.setText(list.get(position).getLocality());
        holder.mobileTxtView.setText(list.get(position).getMobile());
        holder.latTxtView.setText(list.get(position).getLat());
        holder.longTxtView.setText(list.get(position).getLongtide());

        holder.updateMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpPlacePicker(position);
            }
        });

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("businessName", list.get(position).getBusinessName());
                context.startActivity(intent);

            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBusinessDetailsActivity.showProgress(true);
                Backendless.Persistence.of(Business.class).remove(AllData.businessList.get(position), new AsyncCallback<Long>() {
                    @Override
                    public void handleResponse(Long response) {
                        viewBusinessDetailsActivity.showProgress(false);
                        Toast.makeText(viewBusinessDetailsActivity, "Deleted...", Toast.LENGTH_SHORT).show();
                        businessName = list.get(position).getBusinessName();
                        deleteBusinessFromRatingList();
                        AllData.businessList.remove(list.get(position));
                        if (AllData.businessList != list) {
                            list.remove(list.get(position));
                        }

                        ViewBusinessDetailsActivityRecyclerAdapter.this.notifyDataSetChanged();
                        AllData.writeAllDataToFiles();

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        viewBusinessDetailsActivity.showProgress(false);
                        Toast.makeText(viewBusinessDetailsActivity, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void deleteBusinessFromRatingList() {
        for (Rating rating : AllData.ratingList) {

            if (rating.getBusinessName().equals(businessName)) {
                viewBusinessDetailsActivity.showProgress(true);
                Backendless.Persistence.of(Rating.class).remove(rating, new AsyncCallback<Long>() {
                    @Override
                    public void handleResponse(Long response) {
                        viewBusinessDetailsActivity.showProgress(false);
                        Toast.makeText(viewBusinessDetailsActivity, "Deleting Business From Rating List ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        viewBusinessDetailsActivity.showProgress(false);
                        Toast.makeText(viewBusinessDetailsActivity, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }


    private void setUpPlacePicker(int position) {





        // TODO: 26-Feb-20 setup placepicker
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent intent = builder.build((Activity) context);
            intent.putExtra("businessName", list.get(position).getBusinessName());
            viewBusinessDetailsActivity.startActivityForResult(intent, PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView businessNameTxtView;
        private TextView offerTxtView;
        private TextView productServiceDescriptionTxtView;
        private TextView offerUptoTxtView;
        private TextView addressTxtView;
        private TextView cityTxtView;
        private TextView localityTxtView;
        private TextView mobileTxtView;
        private TextView latTxtView;
        private TextView longTxtView;

        private Button updateMapBtn;
        private Button updateBtn;
        private Button deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            businessNameTxtView = itemView.findViewById(R.id.businessNameTxtView);
            offerTxtView = itemView.findViewById(R.id.offerTxtView);
            productServiceDescriptionTxtView = itemView.findViewById(R.id.productServiceDescriptionTxtView);
            offerUptoTxtView = itemView.findViewById(R.id.offerUptoTxtView);
            addressTxtView = itemView.findViewById(R.id.addressTxtView);
            cityTxtView = itemView.findViewById(R.id.cityTxtView);
            localityTxtView = itemView.findViewById(R.id.localityTxtView);
            mobileTxtView = itemView.findViewById(R.id.mobileTxtView);
            latTxtView = itemView.findViewById(R.id.latTxtView);
            longTxtView = itemView.findViewById(R.id.longTxtView);
            updateMapBtn = itemView.findViewById(R.id.updateMapBtn);
            updateBtn = itemView.findViewById(R.id.updateBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
