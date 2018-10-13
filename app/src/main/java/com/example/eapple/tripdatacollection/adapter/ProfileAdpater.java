package com.example.eapple.tripdatacollection.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.eapple.tripdatacollection.R;

public class ProfileAdpater extends RecyclerView.Adapter<ProfileAdpater.MyHolder> {

    Context context;
    int [] images;
    String [] iconsDescription;

    /**
     * Constructor
     *
     * @param context
     * @param images
     */
    public ProfileAdpater(Context context, int[] images, String[] iconsDesc) {
        this.context = context;
        this.images = images;
        this.iconsDescription = iconsDesc;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_screen_row, null);
        MyHolder myHolder = new MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.imageView.setImageResource(images[position]);
        holder.description.setText(iconsDescription[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView description;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.settings_icon);
            description = itemView.findViewById(R.id.tv_icon_Desc);
        }
    }


}
