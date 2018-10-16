package com.example.eapple.tripdatacollection.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eapple.tripdatacollection.R;

import java.util.ArrayList;
import java.util.List;

/*This adapter is being called from SearchFilterFragment.
* The sole purpose of this adpter is to show the data list of firstore collection names.
* And also it will act as search filter for our edit text.*/
public class SearchableAdapter extends RecyclerView.Adapter<SearchableAdapter.CustomHolder> {

    Context context;
    List<String> dataList;
    List<String> copyList;

    EditText searchQuery;

    public SearchableAdapter(Context context, List<String> list, EditText editText) {
        this.context = context;
        dataList = new ArrayList<>();
        copyList = new ArrayList<>();
        dataList.addAll(list);
        copyList.addAll(list);

        this.searchQuery = editText;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_filter, parent, false);
        return new CustomHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        String data = dataList.get(position);
        holder.filterText.setText(data);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // Method -> take the input from user and check if the user input is in list
    // if in list show it show some more names which have same character.
    public void filter(String charText) {
        dataList.clear();
        Log.i("search", "copy list: " + copyList.size());
        if (charText.isEmpty()) {
            Log.i("search", "text is empty");
            dataList.addAll(copyList);
        } else {
            charText = charText.toLowerCase();
            Log.i("search", "text is not empty");
            for (String str : copyList) {
                if (str.toLowerCase().contains(charText)) {
                    dataList.add(str);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class CustomHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView filterText;

        public CustomHolder(View itemView) {
            super(itemView);

            filterText = itemView.findViewById(R.id.lbl_item_filter);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String data = dataList.get(getAdapterPosition());
            searchQuery.setText(data);
        }
    }
}
