package com.example.eapple.tripdatacollection.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eapple.tripdatacollection.R;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyHolder> {

    private final String TAG = "SearchResultAdapter";
    private Context context;
    private int [] images;
    private String [] names;
    private String [] tags;
    private String [] distances;
    private String [] timeToReach;


    /**
     * Constructor
     *
     * @param context
     * @param images
     * @param names
     * @param tags
     * @param distances
     * @param timeToReach
     */
    public SearchResultAdapter(Context context, int[] images, String[] names, String[] tags, String[] distances, String[] timeToReach) {
        Log.d(TAG, "SearchResultAdapter: Constructor called");
        this.context = context;
        this.images = images;
        this.names = names;
        this.tags = tags;
        this.distances = distances;
        this.timeToReach = timeToReach;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_row, null);
        MyHolder myHolder = new MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");
        holder.imageView.setImageResource(images[position]);
        holder.tv_distance.setText(distances[position]);
        holder.tv_name.setText(distances[position]);
        holder.tv_tag.setText(distances[position]);
        holder.tv_time_to_reach.setText(timeToReach[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tv_name;
        TextView tv_tag;
        TextView tv_distance;
        TextView tv_time_to_reach;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.loc_img);
            tv_name = itemView.findViewById(R.id.loc_name);
            tv_tag = itemView.findViewById(R.id.loc_tag);
            tv_distance = itemView.findViewById(R.id.loc_distance);
            tv_time_to_reach = itemView.findViewById(R.id.loc_timeToReach);
        }
    }


}
