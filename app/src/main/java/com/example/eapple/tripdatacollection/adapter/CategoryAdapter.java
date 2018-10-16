package com.example.eapple.tripdatacollection.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eapple.tripdatacollection.R;
import com.example.eapple.tripdatacollection.activities.DetailActivity;
import com.example.eapple.tripdatacollection.data.Preferences;
import com.example.eapple.tripdatacollection.data.pojo.Data;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// This adapter will display data that is being fetch form firestore.
// And will be called from CategoryActivity.
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {

    private Context context;
    private List<Data> listOfData;
    String queryName;

    public CategoryAdapter(Context context,String query, List<Data> list) {
        this.context = context;
        this.queryName = query;
        listOfData = new ArrayList<>();
        listOfData.addAll(list);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.search_result_row, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Data data = listOfData.get(position);
        holder.itemName.setText(data.getName());
        holder.itemDistance.setText("location: " + data.getLatitude() + " " + data.getLatitude());
        holder.itemSearchName.setText(this.queryName);

        if (data.getImage() != null) {
            Picasso.with(context)
                    .load(data.getImage())
                    .placeholder(R.drawable.place_holder_image)
                    .error(R.drawable.error_holder_image)
                    .into(holder.itemImage);
        }
    }

    @Override
    public int getItemCount() {
        return listOfData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView itemImage;
        TextView itemName, itemSearchName, itemDistance, itemMaxTime;

        public MyHolder(View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.img_item_list);
            itemName = itemView.findViewById(R.id.lbl_name_item_list);
            itemSearchName = itemView.findViewById(R.id.lbl_search_name_item_list);
            itemDistance = itemView.findViewById(R.id.lbl_distance_item_list);
            itemMaxTime = itemView.findViewById(R.id.lbl_max_time_item_list);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Data data = listOfData.get(getAdapterPosition());
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(Preferences.DATA_LIST, data);
            context.startActivity(intent);

        }
    }
}
