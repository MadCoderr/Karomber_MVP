package com.example.eapple.tripdatacollection;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private Button getLocationBtn;
    private Button saveLocationBtn;
    private TextView longText;
    private TextView latText;
    private EditText attractionNameText;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private RatingBar ratingBar;
    private EditText detailsText;
    private EditText attractionTypeText;
    private AppDatabase db;
    private double longitude;
    private double latitude;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getLocationBtn = findViewById(R.id.btn_get_location);
        saveLocationBtn = findViewById(R.id.btn_save);
        longText = findViewById(R.id.long_text);
        latText = findViewById(R.id.lat_text);
        attractionNameText = findViewById(R.id.location_name_text);
        ratingBar = findViewById(R.id.ratingBar);
        detailsText = findViewById(R.id.et_cam_img_title);
        attractionTypeText = findViewById(R.id.attractionTypeText);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .build();


        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                    Log.d("debug", "Location Changed");
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    longText.setText(Double.toString(longitude));
                    latText.setText(Double.toString(latitude));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String []{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
            //Toast.makeText(MainActivity.this, "Plz enable your GPS services", Toast.LENGTH_LONG).show();
            return;
        }else {
            configurationButton();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.upload_to_firebase:
                Log.d("debug", "Upload to Firebase clicked");
                updateToFirebase();
                return true;
            case R.id.add_new_attraction:
                Log.d("debug", "Add clicked");
                addNewAttraction();
                return true;
            case R.id.view_saved:
                viewSavedLocations();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    private void configurationButton(){
        //getLocationBtn.setOnClickListener(this);
        //saveLocationBtn.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 10){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                configurationButton();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_get_location){
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1, 1, locationListener);
            Log.d("debug", "Location Requested");
        }else if(id == R.id.btn_save){
            saveLocationBtn.setEnabled(false);
            saveData();

        }else {
            Toast.makeText(MainActivity.this, "Button Not found", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean validateForm(){
        boolean valid = true;
        String attractionName = attractionNameText.getText().toString();
        String attractionType = attractionTypeText.getText().toString();
        String details = detailsText.getText().toString();
        float rating = ratingBar.getRating();

        if(TextUtils.isEmpty(attractionName)){
            attractionNameText.setError("Required");
            valid = false;
            return valid;
        } else if(TextUtils.isEmpty(attractionType)){
            attractionTypeText.setError("Required");
            valid = false;
            return valid;
        }else if(TextUtils.isEmpty(attractionName)){
            attractionNameText.setError("Required");
            valid = false;
            return valid;
        }else if(TextUtils.isEmpty(details)){
            detailsText.setError("Required");
            valid = false;
            return valid;
        } else if(longitude == 0 && latitude == 0){
            Toast.makeText(this, "Invalid location, Longitude = 0, Latitude =0", Toast.LENGTH_SHORT).show();
            valid = false;
            return valid;
        }
        return valid;
    }

    private void saveData(){
        /*
        String attractionName = attractionNameText.getText().toString();
        String attractionType = attractionTypeText.getText().toString();
        String details = detailsText.getText().toString();
        float rating = ratingBar.getRating();
        if(!validateForm()){
            return;
        }
        Attraction attr = new Attraction(attractionName,longitude, latitude, attractionType, details, rating);
        Attraction availAttr = db.attractionsDAO().selectIfmatches(attractionName, longitude, latitude);
        if(availAttr == null){
            db.attractionsDAO().insertAttraction(attr);
            Toast.makeText(MainActivity.this, "Attraction inserted" , Toast.LENGTH_SHORT).show();
            clearForm();
        }else{
            Toast.makeText(MainActivity.this, "Redundant Entry" , Toast.LENGTH_SHORT).show();
        }
        saveLocationBtn.setEnabled(true);
        */
    }


    private void updateToFirebase(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private void viewSavedLocations(){
        Intent intent = new Intent(this, ViewSavedLocations.class);
        startActivity(intent);
    }

    private void addNewAttraction(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void clearForm(){
        attractionTypeText.setText("");
        attractionNameText.setText("");
        detailsText.setText("");
        ratingBar.setRating(0);
    }
}
