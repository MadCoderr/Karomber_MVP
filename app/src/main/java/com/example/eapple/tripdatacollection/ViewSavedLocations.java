package com.example.eapple.tripdatacollection;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewSavedLocations extends AppCompatActivity {
    private TextView savedLocationsText;
    private AppDatabase db;
    ListView listView;
    private Toolbar toolbar;
    private final String TAG = "ViewSavedLocations";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_locations);
        //savedLocationsText = findViewById(R.id.saved_locations_text);
        listView = findViewById(R.id.list_view);
        db =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .build();
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        populateTextView();
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
                updateToFirebase();
                return true;
            case R.id.add_new_attraction:
                addNewAttraction();
                return true;
            case R.id.view_saved:
                viewSavedLocations();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    private void populateTextView(){
        List<Attraction> attrs = db.attractionsDAO().loadAllAttractions();
        //nt size = attrs.size();
        int aid;
        String attractionName;
        double longitude;
        double latitude;
        String attractionType;
        String attractionDetails;
        float rating;
        String info = " ";
        final ArrayList<String> list = new ArrayList<String>();
        for(Attraction attr: attrs){
            aid = attr.getAid();
            attractionName = attr.getName();
            longitude = attr.getLongitude();
            latitude = attr.getLatitude();
            attractionType = attr.getAccessibility();
            attractionDetails = attr.getAccessibility();
            rating = attr.getAid();

           info =   "id: "+ aid
                   + "\nName: " + attractionName
                   + "\nLongitude: " + longitude
                   + "\nLatitude: " + latitude
                   + "\nType: " + attractionType
                   + "\nDetails: " + attractionDetails
                   + "\nRatings " + rating;
           list.add(info);
           Log.d("debug", info);
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
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
}
