package com.example.eapple.tripdatacollection;


import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import static android.content.Context.LOCATION_SERVICE;


/**
 * @author Haroon khan
 * @file addDataFragment.java
 * @data 10 Sept, 2018
 * @section Description
 * A {@link Fragment} subclass, derived from Fragment class
 * where we get data from the user and store it the local sqlite
 * database of the device.
 */
public class AddDataFragment extends Fragment {

    //Tag for debugging logs
    private static final String TAG = "AddDataFragment";

    private Toolbar toolbar;
    private Button btnGetGpsLoc;
    private TextView tvLong;
    private TextView tvLat;
    private EditText etLocName;
    private EditText etDescription;
    private EditText etImageTitleOnCam;
    private ImageView ivAddImgBtn;
    private Spinner spnClass;
    private RadioGroup rgAccess;
    private RadioButton rbCar;
    private RadioButton rbJeep;
    private RadioButton rbTrack;
    private Button btnSave;
    private double latitude;
    private double longitude;
    View view;
    String head;

    //Database object
    private AppDatabase db;

    //GPS objects
    private LocationManager locationManager;
    private LocationListener locationListener;

    public AddDataFragment() {
        // Required empty public constructor
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionItemSelected: Called");
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Log.d(TAG, "onOptionItem Selected: Home button clicked");
            getFragmentManager().popBackStack();
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Called");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_data, container, false);
        head = getArguments().getString("head");

        //Initializing objects
        toolbar = view.findViewById(R.id.app_bar_new);
        btnGetGpsLoc = view.findViewById(R.id.btn_get_location);
        tvLong = view.findViewById(R.id.tv_long);
        tvLat = view.findViewById(R.id.tv_lat);
        etLocName = view.findViewById(R.id.et_name_of_attr);
        etDescription = view.findViewById(R.id.et_description);
        etImageTitleOnCam = view.findViewById(R.id.et_cam_img_title);
        ivAddImgBtn = view.findViewById(R.id.btn_add_imgs);
        spnClass = view.findViewById(R.id.spin_classification);
        rgAccess = view.findViewById(R.id.rg_access);
        rbCar = view.findViewById(R.id.rb_car);
        rbJeep = view.findViewById(R.id.rb_jeep);
        rbTrack = view.findViewById(R.id.rb_track);
        btnSave = view.findViewById(R.id.btn_save);

        //Getting reference to actionbar and doing customization
        toolbar = view.findViewById(R.id.app_bar_new);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        ActionBar actionBar = ((Main2Activity) getActivity()).getSupportActionBar();
        actionBar.setTitle(head);
        this.setHasOptionsMenu(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Populate the spinner accordingly
        populateSpinner(head);

        //Get reference to sqlite local database
        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .build();

        //Get Location management ready
        getLocationManagementReady();

        //Get Required Permissions
        getRequiredPermissions();

        //Adding on click listener to save and get gps credential buttons
        btnGetGpsLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGPSCredentials();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setEnabled(false);
                Log.d(TAG, "Save Button Disabled");
                saveDataToLocalDB();
            }
        });

        return view;
    }

    /**
     * @param head
     * @breif This function checks the type of data we want to enter
     * like hotel or resturants etc and then sets the spinner dropdown
     * according from the array-lists in the string resource file
     */
    private void populateSpinner(String head) {
        ArrayAdapter<CharSequence> adapter;

        Log.d(TAG, "populateSpinner: Called");

        if (this.getActivity() != null) {
            switch (head) {
                case "Hotel":
                    //DropDown list for hotel
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.hotel_classes_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClass.setAdapter(adapter);
                    break;

                case "Attraction Point":
                    //DropDown list for attraction points
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.attraction_classes_list, android.R.layout.simple_spinner_item);
                    spnClass.setAdapter(adapter);
                    break;

                case "Restaurant":
                    //DropDown list for attraction points
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.resturant_classes_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClass.setAdapter(adapter);
                    break;

                case "Bank":
                    //DropDown list for attraction points
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.bank_classes_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClass.setAdapter(adapter);
                    break;

                case "Mobile Wallet":
                    //DropDown list for attraction points
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.mobile_wallet_classes_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClass.setAdapter(adapter);
                    break;

                case "Gas Station":
                    //DropDown list for attraction points
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.gas_staion_classes_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClass.setAdapter(adapter);
                    break;

                case "Rental Services":
                    //DropDown list for attraction points
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.rentals_classes_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClass.setAdapter(adapter);
                    break;

                case "Shopping Mart":
                    //DropDown list for attraction points
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.shopping_mart_classes_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClass.setAdapter(adapter);
                    break;

                case "Toilet":
                    //DropDown list for attraction points
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.toilet_classes_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClass.setAdapter(adapter);
                    break;

                case "Workshop":
                    //DropDown list for attraction points
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.toilet_classes_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClass.setAdapter(adapter);
                    break;
                case "Hospital":
                    //DropDown list for attraction points
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.toilet_classes_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClass.setAdapter(adapter);
                    break;
                case "Police Station":
                    //DropDown list for attraction points
                    adapter = ArrayAdapter.createFromResource(this.getActivity(),
                            R.array.toilet_classes_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClass.setAdapter(adapter);
                    break;

            }


        } else {
            Log.d(TAG, "populateSpinner: this.getActivity is null");
        }
    }

    /**
     * @brief This function do the initialization of locationManager and
     * gets the device gps location and sets the required fields
     * longitude and latitude
     */
    private void getLocationManagementReady() {
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("debug", "Location Changed");
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                tvLong.setText(Double.toString(longitude));
                tvLat.setText(Double.toString(latitude));
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
    }


    /**
     * This function first checks the required permissions and if
     * they are not provided, it request for the required permissions e.g
     * ACCESS_FINE LOCATION
     * ACCESS_COARSE_LOCATION
     * INTERNET
     */
    private void getRequiredPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
            //Toast.makeText(MainActivity.this, "Plz enable your GPS services", Toast.LENGTH_LONG).show();
        } else {
            configurationButton();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                configurationButton();
            }
        }
    }

    private void configurationButton() {
        //getLocationBtn.setOnClickListener(this);
        //saveLocationBtn.setOnClickListener(this);
    }

    /**
     * This functions gets the form data and stores in the
     * local sqlire (Room) database
     */
    private void saveDataToLocalDB() {
        String name = etLocName.getText().toString();
        String classification = spnClass.getSelectedItem().toString();
        String description = etDescription.getText().toString();
        String imgTitleInCam = etImageTitleOnCam.getText().toString();
        String accessiblity = getAccessibility();
        String type = head;

        if (!validateForm()) {
            return;
        }
        Attraction attr = new Attraction(name, longitude, latitude, classification, description, imgTitleInCam, accessiblity, head);
        Attraction availAttr = db.attractionsDAO().selectIfmatches(name, longitude, latitude);
        if (availAttr == null) {
            db.attractionsDAO().insertAttraction(attr);
            Toast.makeText(getActivity(), "Attraction inserted", Toast.LENGTH_SHORT).show();
            clearForm();
        } else {
            Toast.makeText(getActivity(), "Redundant Entry", Toast.LENGTH_SHORT).show();
        }
        btnSave.setEnabled(true);
    }

    private void getGPSCredentials() {
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1, 1, locationListener);
    }

    /**
     * This form validates the form for any
     * invalid entry and warns the user for any error
     *
     * @return valid ( True/False)
     */
    private boolean validateForm() {
        boolean valid = true;
        String name = etLocName.getText().toString();
        String description = etDescription.getText().toString();
        String imgTitleOnCam = etImageTitleOnCam.getText().toString();
        String classification = spnClass.getSelectedItem().toString();

        if (TextUtils.isEmpty(name)) {
            etLocName.setError("Required");
            valid = false;
            return valid;
        } else if (TextUtils.isEmpty(description)) {
            etDescription.setError("Required");
            valid = false;
            return valid;
        } else if (TextUtils.isEmpty(imgTitleOnCam)) {
            etImageTitleOnCam.setError("Required");
            valid = false;
            return valid;
        } else if (classification == "Classification") {
            Toast.makeText(getActivity(), "Select Classificaiton", Toast.LENGTH_LONG).show();
            valid = false;
            return valid;
        } /*else if(longitude == 0 && latitude == 0){
            Toast.makeText(getActivity(), "Invalid location, Longitude = 0, Latitude =0", Toast.LENGTH_SHORT).show();
            valid = false;
            return valid;
        }*/
        return valid;
    }

    /**
     * This function gets the selected field from
     * the form and return it for saving
     *
     * @return accessibility
     */
    private String getAccessibility() {
        String access = "";
        int id = rgAccess.getCheckedRadioButtonId();
        switch (id) {
            case R.id.rb_car:
                access = "car";
                break;
            case R.id.rb_jeep:
                access = "jeep";
                break;
            case R.id.rb_track:
                access = "hike";
                break;
        }
        return access;
    }

    /**
     * This form clears the form for new entery
     */
    private void clearForm() {
        etImageTitleOnCam.setText("");
        etDescription.setText("");
        etLocName.setText("");
        tvLong.setText("");
        tvLat.setText("");
    }
}
