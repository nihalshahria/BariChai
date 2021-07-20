package com.example.VaraBari.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.VaraBari.Objects.House;
import com.example.VaraBari.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    Context context;
    ArrayList<House>list;

    public DashboardAdapter(Context context, ArrayList<House> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.house_card, parent, false);
        return new DashboardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DashboardAdapter.DashboardViewHolder holder, int position) {
        House house = list.get(position);
        Picasso.get().load(house.image.get(0)).into(holder.imageView);
//        holder.imageView.setImageURI(Uri.parse(house.image.get(0)));
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
                    if(house.title.toLowerCase().contains(filterPattern)){
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

    public static class DashboardViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, address, bedrooms, area, rent;

        public DashboardViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.house_card_image);
            title = itemView.findViewById(R.id.house_card_title);
            address = itemView.findViewById(R.id.house_card_address);
            bedrooms = itemView.findViewById(R.id.house_card_beds);
            area = itemView.findViewById(R.id.house_card_area);
            rent = itemView.findViewById(R.id.house_card_rent);
        }
    }
}
