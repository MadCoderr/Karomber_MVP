package com.example.eapple.tripdatacollection;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class UploadDataActivity extends AppCompatActivity implements View.OnClickListener{


    private String userId;
    private Toolbar toolbar;
    Button uploadBtn;
    private int aid;
    private DatabaseReference firebaseDB;
    private AppDatabase roomDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);
        uploadBtn = findViewById(R.id.upload_btn);
        firebaseDB  = FirebaseDatabase.getInstance().getReference();
        roomDB =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .build();
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        uploadBtn.setOnClickListener(this);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
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


    /**
     *
     * upload function fetches the object from room database,
     * Upload that object to Firebase database and
     * then deletes it from the room database
     * The process continues till all the objects are upload to firebase.
     */
    /*
    private void upload(){

        Log.d("debug", "Upload function called");
        //Log.d("debug", "userId: " + userId);
        List<Attraction> attrs = roomDB.attractionsDAO().loadAllAttractions();
        //nt size = attrs.size();
        String attractionName;
        double longitude;
        double latitude;
        String attractionType;
        String attractionDetails;
        float rating;
        String key;


        for(Attraction attr: attrs){
            aid = attr.getAid();
            key = firebaseDB.child("Attractions").push().getKey();
            attractionName = attr.getName();
            longitude = attr.getLongitude();
            latitude = attr.getLatitude();
            attractionType = attr.getAccessibility();
            attractionDetails = attr.getAccessibility();
            rating = attr.getAid();
            Log.d("debug", key);

            AttractionWithoutID firebaseAttr = new AttractionWithoutID( userId, attractionName, longitude, latitude, attractionType, attractionDetails, rating);

            //Log.d("debug", "User Id: " + firebaseAttr.getUser_Id());
            Task<Void> task = firebaseDB.child("Attractions").child(key).setValue(firebaseAttr)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           Log.d("debug", "Exception: " + e);
                        }
                    });

            Log.d("debug", "Loop ran");
            roomDB.attractionsDAO().deleteIfIdMatches(aid);
        }



    }
    */

    /**
     * @desctcription
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.upload_btn) {
            Log.d("debug", "Upload btn clicked");
            //upload();
        }
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
