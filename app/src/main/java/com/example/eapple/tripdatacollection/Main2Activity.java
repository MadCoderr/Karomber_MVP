package com.example.eapple.tripdatacollection;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class Main2Activity extends AppCompatActivity {

    private static AddLocationFragment addLocationFragment;
    private static ProfileFragment profileFragment;
    private static SavedLocationsFragment savedLocationsFragment;
    private gridViewFragment gridViewFragment;
    private locationsResult locationsResult;
    private View rootView;
    private final String TAG = "Main2Activity";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String tag;
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    tag = "profile_frag";
                    loadFragment(profileFragment, tag);
                    return true;
                case R.id.navigation_saved_locations:
                    tag = "saved_loc_frag";
                    loadFragment(savedLocationsFragment, tag);
                    return true;
                case R.id.navigation_add_location:
                    tag = "add_loc_frag";
                    //loadFragment(addLocationFragment, tag);
                    loadFragment(locationsResult, tag);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        addLocationFragment = new AddLocationFragment();
        savedLocationsFragment = new SavedLocationsFragment();
        profileFragment = new ProfileFragment();
        gridViewFragment = new gridViewFragment();
        locationsResult = new locationsResult();

        rootView = findViewById(R.id.root_view);

        final BottomNavigationView navigation = findViewById(R.id.navBar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_add_location);
        loadFragment(addLocationFragment, "add_loc_frag");

        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        Log.d(TAG,"onVisibilityChanged: Keyboard visibility changed");
                        if(isOpen){
                            Log.d(TAG, "onVisibilityChanged: Keyboard is open");
                            navigation.setVisibility(View.INVISIBLE);
                            Log.d(TAG, "onVisibilityChanged: NavBar got Invisible");
                        }else{
                            Log.d(TAG, "onVisibilityChanged: Keyboard is closed");
                            navigation.setVisibility(View.VISIBLE);
                            Log.d(TAG, "onVisibilityChanged: NavBar got Visible");
                        }
                    }
                });
    }

    private void loadFragment(android.support.v4.app.Fragment fragment, String tag) {
        int anim_left_id = R.anim.slide_in_right;
        int anim_right_id = R.anim.slide_out_left;
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if(f instanceof ProfileFragment){
                anim_left_id = R.anim.slide_in_right;
                anim_right_id = R.anim.slide_out_left;
                //Log.d(TAG, "Profile Fragment");
            }else if(f instanceof SavedLocationsFragment){
                anim_left_id = R.anim.slide_in_left;
                anim_right_id = R.anim.slide_out_right;
                Log.d(TAG, "SavedLocations Fragment");
            }else if(f instanceof locationsResult){
                if(tag == "saved_loc_frag"){
                    anim_left_id = R.anim.slide_in_right;
                    anim_right_id = R.anim.slide_out_left;
                } else if(tag == "profile_frag"){
                    anim_left_id = R.anim.slide_in_left;
                    anim_right_id = R.anim.slide_out_right;
                }
                Log.d(TAG, "Add Locations Fragment");
            }
        }
        //Log.d(TAG, getCurrentFragment());
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(anim_left_id, anim_right_id);
        //fragmentTransaction.setTransition(R.id.)
        fragmentTransaction.replace(R.id.fragment_container, fragment,tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
