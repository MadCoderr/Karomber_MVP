package com.example.eapple.tripdatacollection.activities;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.example.eapple.tripdatacollection.R;
import com.example.eapple.tripdatacollection.adapter.SliderAdapter;
import com.example.eapple.tripdatacollection.data.Preferences;
import com.example.eapple.tripdatacollection.data.pojo.Data;
/*This activity will only show the detail of each selected item from Category Activity.
* Suppose from module Attraction point user select saif ul malok, then all the data associated
* with saif ul malok will be shown by this activity*/
public class DetailActivity extends AppCompatActivity {

    SliderLayout sliderLayout;
    SliderAdapter sliderAdapter;

    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Data data = getIntent().getParcelableExtra(Preferences.DATA_LIST);

        sliderLayout = findViewById(R.id.slider_layout_detail);
        description = findViewById(R.id.lbl_description_detail);

        description.setText(data.getDescription());
        sliderAdapter = new SliderAdapter(this, data.getGallery(), data.getName());
        sliderAdapter.setSliderLayout(sliderLayout);

        Log.d("detail", "location: " + data.getLatitude() + " " + data.getLongitude());
    }

    // Method-> this method will be fire when show on map button is pressed
    // write code to show the map
    public void showMap(View view) {
    }

    public void onBackButtonPressed(View view) {
        finish();
    }

    public void showPopUpMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.pop_up_menu, menu.getMenu());
        menu.show();
    }

}
