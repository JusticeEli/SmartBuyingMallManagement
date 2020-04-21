package com.justice.smartbuyingmallmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.justice.smartbuyingmallmanagement.data.AllData;

import java.util.List;

public class MyRatingActivityRecyclerAdapter extends RecyclerView.Adapter<MyRatingActivityRecyclerAdapter.ViewHolder> {
   private MyRatingActivity myRatingActivity;
    private Context context;
    private List<Rating> list;

    public MyRatingActivityRecyclerAdapter(Context context, List<Rating> list) {
        myRatingActivity=(MyRatingActivity)context;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_rating,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.businessNameTxtView.setText(""+ list.get(position).getBusinessName());
        holder.discount.setText(""+ list.get(position).getDiscountPolicyAsset());
        holder.value.setText(""+ list.get(position).getValueForMoney());
        holder.politeness.setText(""+ list.get(position).getPolitenessTheKindness());
        holder.quality.setText(""+ list.get(position).getQualityOfService());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView businessNameTxtView;
        private TextView discount;
        private TextView value;
        private TextView politeness;
        private TextView quality;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            businessNameTxtView=itemView.findViewById(R.id.businessNameTxtView);
            discount = itemView.findViewById(R.id.discount);
            value = itemView.findViewById(R.id.value);
            politeness = itemView.findViewById(R.id.politeness);
            quality = itemView.findViewById(R.id.quality);
        }
    }


    public void setList(List<Rating> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
