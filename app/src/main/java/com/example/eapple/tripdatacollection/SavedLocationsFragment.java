package com.example.eapple.tripdatacollection;

import android.arch.persistence.room.Room;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class SavedLocationsFragment extends Fragment {
    private final String TAG = "SavedLocationsFragment";
    private TextView savedLocationsText;
    private Button uploadBtn;
    private AppDatabase db;
    ListView listView;
    private Toolbar toolbar;
    View view;
    DatabaseReference firebaseDB;
    AppDatabase roomDB;
    String userId;
    private boolean uploadStatus;
    private String uploadExceptionMsg;


    public SavedLocationsFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        view = inflater.inflate(R.layout.fragment_saved_locations, container, false);
        listView = view.findViewById(R.id.list_view);
        uploadBtn = view.findViewById(R.id.upload_btn);
        db =  Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .build();
        toolbar = view.findViewById(R.id.app_bar);
        //Getting reference to actionbar and doing customization
        toolbar = view.findViewById(R.id.app_bar_new);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        firebaseDB  = FirebaseDatabase.getInstance().getReference();
        roomDB =  Room.databaseBuilder(getActivity(), AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .build();

        if(actionBar != null){
            Log.d(TAG,"onCreateView: actionBar is working");
            this.setHasOptionsMenu(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Saved Places");
        }else {
            Log.d(TAG, "onCreateView: actionBar object is null");
        }

        //Lists the saved items
        populateTextView();

        //onClickListener for uploadBtn
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: UploadBtn Clicked");
                if(checkLoginStatus()){
                    uploadDataToFirebase();
                }else{
                    redirectToSignIn();
                }
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Log.d(TAG, "onOptionItem Selected: Home button clicked");
            int count = getFragmentManager().getBackStackEntryCount();
            Log.d(TAG, "onOptionItemSelect: Stack size = " + count);
            getFragmentManager().popBackStack();
        }
        return true;
    }

    private void populateTextView(){
        Log.d(TAG, "populateTextView: Called");
        List<Attraction> attrs = db.attractionsDAO().loadAllAttractions();
        int id;
        String name;
        double longitude;
        double latitude;
        String classification;
        String description;
        String titleImgOnCam;
        String type;
        String info = " ";
        final ArrayList<String> list = new ArrayList<String>();
        for(Attraction attr: attrs){
            id = attr.getAid();
            name = attr.getName();
            longitude = attr.getLongitude();
            latitude = attr.getLatitude();
            classification = attr.getAccessibility();
            description = attr.getDescription();
            titleImgOnCam = attr.getImgTitleInCam();
            type = attr.getType();

            info =   "id: "+ id
                    + "\nName: " + name
                    + "\nLongitude: " + longitude
                    + "\nLatitude: " + latitude
                    + "\nClass: " + classification
                    + "\nDescription: " + description
                    + "\nImg Title: " + titleImgOnCam
                    + "\nType: " + type;

            list.add(info);
            Log.d(TAG, "Populate text: \n " + info);
        }

        final ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    private boolean checkLoginStatus(){
        boolean status = true;
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            //User is not logged in
            status = false;
        }
        return status;
    }

    private void uploadDataToFirebase(){
        Log.d(TAG, "Upload function called");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        List<Attraction> attrs = roomDB.attractionsDAO().loadAllAttractions();
        String name;
        double longitude;
        double latitude;
        String access;
        String classification;
        String description;
        String imgTitleOnCam;
        String key;
        String type;

        for(Attraction attr: attrs){
            uploadStatus = true;
            final int aid = attr.getAid();
            key = firebaseDB.child("Attractions").push().getKey();
            name = attr.getName();
            longitude = attr.getLongitude();
            latitude = attr.getLatitude();
            access = attr.getAccessibility();
            description = attr.getDescription();
            classification = attr.getClassification();
            imgTitleOnCam = attr.getImgTitleInCam();
            type = attr.getType();
            Log.d(TAG, key);

            AttractionWithoutID firebaseAttr = new AttractionWithoutID(userId, name, longitude, latitude, classification, description, imgTitleOnCam, access, type);

            Log.d(TAG, "User Id: " + firebaseAttr.getUserId());
            firebaseDB.child("Attractions").child(key).setValue(firebaseAttr, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference databaseReference) {
                    if(error == null){
                        Log.d(TAG, "UploadDataToFirebase: Data uplaoded successfully");
                        roomDB.attractionsDAO().deleteIfIdMatches(aid);
                    }else{
                        uploadExceptionMsg = error.getMessage();
                        Log.d(TAG, "UploadDataToFirebase: Exception: " + uploadExceptionMsg);
                        uploadStatus = false;
                    }
                }
            });
    }
        if(uploadStatus == false){
            Toast.makeText(getActivity(), "Error: " + uploadExceptionMsg, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Success fully uploaded successfully ", Toast.LENGTH_SHORT).show();
        }

    }

    private void redirectToSignIn(){
        try{
            ProfileFragment fragment = new ProfileFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,fragment);
            ft.commit();
        }catch (NullPointerException e){
            Log.d(TAG, "redirectToSignIn: Exception: " + e.getMessage());
        }

    }

}
