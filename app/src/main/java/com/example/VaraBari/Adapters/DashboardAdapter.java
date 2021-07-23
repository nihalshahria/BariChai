package com.example.VaraBari.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.VaraBari.Objects.House;
import com.example.VaraBari.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> implements Filterable {

    Context context;
    public ArrayList<House>list = new ArrayList<>();
    public ArrayList<House>listFull = new ArrayList<>();
    public OnHouseListener onHouseListener;

    public DashboardAdapter(Context context, ArrayList<House> list, OnHouseListener onHouseListener) {
        this.context = context;
        this.list = list;
        this.listFull = list;
        this.onHouseListener = onHouseListener;
    }

    @NonNull
    @NotNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.house_card, parent, false);
        return new DashboardViewHolder(v, onHouseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DashboardAdapter.DashboardViewHolder holder, int position) {
        House house = list.get(position);
        Picasso.get().load(house.image.get(0)).into(holder.imageView);
        holder.title.setText(house.title);
        holder.area.setText(String.valueOf((int)house.area));
        holder.address.setText(house.address);
        holder.bedrooms.setText(String.valueOf(house.bedRoom));
        holder.rent.setText(String.valueOf(house.rent));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return houseFilter;
    }

    private Filter houseFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<House> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length()== 0){
                filteredList = listFull;
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(House house: listFull){
                    if(house.title.toLowerCase().contains(filterPattern) || house.address.toLowerCase().contains(filterPattern) || house.description.toLowerCase().contains(filterPattern)){
                        filteredList.add(house);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
//            list.clear();
//            list.addAll((ArrayList) results.values);
            list = (ArrayList<House>)results.values;
            notifyDataSetChanged();
        }
    };

    public static class DashboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView title, address, bedrooms, area, rent;
        OnHouseListener onHouseListener;

        public DashboardViewHolder(@NonNull @NotNull View itemView, OnHouseListener onHouseListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.house_card_image);
            title = itemView.findViewById(R.id.house_card_title);
            address = itemView.findViewById(R.id.house_card_address);
            bedrooms = itemView.findViewById(R.id.house_card_beds);
            area = itemView.findViewById(R.id.house_card_area);
            rent = itemView.findViewById(R.id.house_card_rent);
            this.onHouseListener = onHouseListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onHouseListener.onHouseClick(getAdapterPosition());
        }
    }
    public interface OnHouseListener{
         void onHouseClick(int position);
    }
}
