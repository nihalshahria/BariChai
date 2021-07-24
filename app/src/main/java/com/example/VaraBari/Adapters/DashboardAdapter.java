package com.example.VaraBari.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.VaraBari.Objects.House;
import com.example.VaraBari.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> implements Filterable {

    Context context;
    public ArrayList<House>list = new ArrayList<>();
    public ArrayList<House>listFull = new ArrayList<>();
    private OnHouseClickListener mListener;
    DatabaseReference fvrtRef = FirebaseDatabase.getInstance().getReference("favourites");
    DatabaseReference fvrt_listRef = FirebaseDatabase.getInstance().getReference("favouriteList").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    Boolean fvrtChecker = false;


    public DashboardAdapter(Context context, ArrayList<House> list) {
        this.context = context;
        this.list = list;
        this.listFull = list;
    }

    @NonNull
    @NotNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.house_card, parent, false);
        return new DashboardViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DashboardAdapter.DashboardViewHolder holder, int position) {

        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        House house = list.get(position);
        final String key = house.postKey;
        Picasso.get().load(house.image.get(0)).into(holder.imageView);
        holder.title.setText(house.title);
        holder.area.setText(String.valueOf((int)house.area));
        holder.address.setText(house.address);
        holder.bedrooms.setText(String.valueOf(house.bedRoom));
        holder.rent.setText(String.valueOf(house.rent));
        holder.favouriteChecker(key);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fvrtChecker = true;

                fvrtRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(fvrtChecker.equals(true)){
                            if(snapshot.child(key).hasChild(uuid)){
                                fvrtRef.child(key).child(uuid).removeValue();
                                fvrt_listRef.child(key).removeValue();
//                                delete(time);
                                fvrtChecker = false;
                            }else{
                                fvrtRef.child(key).child(uuid).setValue(true);
//                                String id = fvrt_listRef.push().getKey();
                                fvrt_listRef.child(key).setValue(house);
                                fvrtChecker = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });
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

    public interface OnHouseClickListener{
        void onHouseClick(int position);
    }

    public void setOnHouseClickListener(OnHouseClickListener listener){
        mListener = listener;
    }

    public static class DashboardViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, address, bedrooms, area, rent;
        ImageButton imageButton;

        public DashboardViewHolder(@NonNull @NotNull View itemView, OnHouseClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.house_card_image);
            title = itemView.findViewById(R.id.house_card_title);
            address = itemView.findViewById(R.id.house_card_address);
            bedrooms = itemView.findViewById(R.id.house_card_beds);
            area = itemView.findViewById(R.id.house_card_area);
            rent = itemView.findViewById(R.id.house_card_rent);
            imageButton = itemView.findViewById(R.id.fvrt_f2_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onHouseClick(position);
                        }
                    }
                }
            });
        }

        public void favouriteChecker(String key) {
            imageButton = itemView.findViewById(R.id.fvrt_f2_item);
            DatabaseReference favouriteRef = FirebaseDatabase.getInstance().getReference("favourites");
            String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            favouriteRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.child(key).hasChild(uuid)){
                        imageButton.setImageResource(R.drawable.turned_in_icon);
                    }else{
                        imageButton.setImageResource(R.drawable.turned_in_not_icon);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }
}
