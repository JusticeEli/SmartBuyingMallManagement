package com.justice.smartbuyingmallmanagement.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.update.UpdateActivity;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.update.UpdateContractStatusActivity;

import java.util.List;

public class ViewDetailsActivityRecyclerAdapter extends RecyclerView.Adapter<ViewDetailsActivityRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Business> list;

    public ViewDetailsActivityRecyclerAdapter(Context context, List<Business> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<Business> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewDetailsActivityRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_details, parent, false);
        return new ViewDetailsActivityRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDetailsActivityRecyclerAdapter.ViewHolder holder, final int position) {
        holder.businessNameTxtView.setText(list.get(position).getBusinessName());
        holder.offerTxtView.setText(list.get(position).getOffer());
        holder.productServiceDescriptionTxtView.setText(list.get(position).getProductServiceDescription());
        holder.offerUptoTxtView.setText(list.get(position).getOfferUpto());
        holder.addressTxtView.setText(list.get(position).getAddress());
        holder.cityTxtView.setText(list.get(position).getCity());
        holder.localityTxtView.setText(list.get(position).getLocality());
        holder.mobileTxtView.setText(list.get(position).getMobile());
        holder.statusTxtView.setText(list.get(position).getStatus());

        holder.updateStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateContractStatusActivity.class);
                intent.putExtra("businessName",list.get(position).getBusinessName());
                context.startActivity(intent);

            }
        });

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
        private TextView statusTxtView;
        private Button updateStatusBtn;

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
            statusTxtView = itemView.findViewById(R.id.statusTxtView);
            updateStatusBtn = itemView.findViewById(R.id.updateStatusBtn);

        }
    }
}

