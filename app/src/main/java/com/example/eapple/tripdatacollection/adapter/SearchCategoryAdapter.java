package com.example.eapple.tripdatacollection.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eapple.tripdatacollection.Main2Activity;
import com.example.eapple.tripdatacollection.R;
import com.example.eapple.tripdatacollection.activities.CategoryActivity;
import com.example.eapple.tripdatacollection.data.Preferences;
import com.example.eapple.tripdatacollection.data.SearchCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.MyHolder> {

    Context context;
    List<SearchCategoryModel> categoryList;
    String queryName;

    public SearchCategoryAdapter(Context context, String queryName, List<SearchCategoryModel> list) {
        this.context = context;
        this.queryName = queryName;
        categoryList = new ArrayList<>();
        categoryList.addAll(list);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.grid_item_view, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        SearchCategoryModel model = categoryList.get(position);
        holder.image.setImageResource(model.getImage());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;

        public MyHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.grid_img_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            SearchCategoryModel model = categoryList.get(getAdapterPosition());
            Intent intent = new Intent(context, CategoryActivity.class);
            intent.putExtra(Preferences.ITEM_NAME, model.getName());
            intent.putExtra(Preferences.QUERY_NAME, queryName);
            // you can also pass image to CategoryActivity using extras e.g model.getImage()
            context.startActivity(intent);
        }
    }
}
