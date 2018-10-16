package com.example.eapple.tripdatacollection.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eapple.tripdatacollection.R;
import com.example.eapple.tripdatacollection.SavedListFragment;

import java.util.ArrayList;
import java.util.List;

// This adapter is used in SavedListFragment
// Its purpose is to show the list of user saved maps or locations
public class SavedListAdapter extends RecyclerView.Adapter<SavedListAdapter.MyHolder> {

    Context context;
    List<SavedListFragment.Model> modelList;

    public SavedListAdapter(Context context, List<SavedListFragment.Model> models) {
        this.context = context;
        this.modelList = new ArrayList<>();
        this.modelList = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_downloaded, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        SavedListFragment.Model model = modelList.get(position);
        holder.title.setText(model.Title);
        holder.subTitle.setText(model.SubTitle);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView title, subTitle;

        public MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.lbl_place_name_item_map_downloaded);
            subTitle = itemView.findViewById(R.id.lbl_prov_name_item_map_downloaded);

            // you can also specify the image and you can also set setOnClickListener right here
        }
    }
}
