package com.example.eapple.tripdatacollection.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eapple.tripdatacollection.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    Context context;
    int images[];

    /**
     * Constructor
     *
     * @param context
     * @param images
     */
    public MyAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_view, null);
        MyHolder myHolder = new MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.imageView.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.grid_img_view);
        }
    }


}
