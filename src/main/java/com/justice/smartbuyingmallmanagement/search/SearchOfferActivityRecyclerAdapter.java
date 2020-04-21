package com.justice.smartbuyingmallmanagement.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.justice.smartbuyingmallmanagement.MoreInfoActivity;
import com.justice.smartbuyingmallmanagement.QuestionActivity;
import com.justice.smartbuyingmallmanagement.R;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.view.ViewRatingActivity;

import java.util.List;

public class SearchOfferActivityRecyclerAdapter extends RecyclerView.Adapter<SearchOfferActivityRecyclerAdapter.ViewHolder> {

    SearchOfferActivity searchOfferActivity;
    private Context context;
    private List<Business> list;

    public SearchOfferActivityRecyclerAdapter(Context context, List<Business> list) {
        searchOfferActivity=(SearchOfferActivity)context;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_offer,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.businessNameTxtView.setText(list.get(position).getBusinessName());
        holder.offerTxtView.setText(list.get(position).getOffer());
        holder.productServiceDescriptionTxtView.setText(list.get(position).getProductServiceDescription());

        /**
         *                     LISTENERS
         */
        holder.moreInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, MoreInfoActivity.class);
                intent.putExtra("businessName",list.get(position).getBusinessName());
                context.startActivity(intent);

            }
        });
        holder.viewRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ViewRatingActivity.class);
                intent.putExtra("businessName",list.get(position).getBusinessName());
                context.startActivity(intent);

            }
        });
        holder.rateItBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, QuestionActivity.class);
                intent.putExtra("email",searchOfferActivity.getEmail());
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

        private Button moreInfoBtn;
        private Button viewRatingBtn;
        private Button rateItBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            businessNameTxtView = itemView.findViewById(R.id.businessNameTxtView);
            offerTxtView = itemView.findViewById(R.id.offerTxtView);
            productServiceDescriptionTxtView = itemView.findViewById(R.id.productServiceDescriptionTxtView);

            moreInfoBtn = itemView.findViewById(R.id.moreInfoBtn);
            viewRatingBtn = itemView.findViewById(R.id.viewRatingBtn);
            rateItBtn = itemView.findViewById(R.id.rateItBtn);

        }
    }

    public void setList(List<Business> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
