package com.example.eapple.tripdatacollection;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.RequiresApi;

import com.example.eapple.tripdatacollection.adapter.ProfileAdpater;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private final String TAG = "ProfileFragment";
    private ImageView profileImg;
    private TextView userName;
    private TextView address;
    private View view;
    RecyclerView recyclerView;
    private SignUp3Fragment signUp3Fragment;
    private SignInFragment signInFragment;
    private android.support.v7.widget.Toolbar toolbar;
    private String[] descriptions;
    private int[] icons = {
            R.drawable.edit_profile_icon,
            R.drawable.settings_icon,
            R.drawable.feedback_icon
    };


    public ProfileFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Initializing objects
        recyclerView = view.findViewById(R.id.profile_recyler_view);
        profileImg = view.findViewById(R.id.profile_image);
        userName = view.findViewById(R.id.tv_name);
        address = view.findViewById(R.id.tv_address);
        descriptions = getResources().getStringArray(R.array.icons_description_list);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        ProfileAdpater adpater = new ProfileAdpater(getActivity(), icons, descriptions);
        recyclerView.setAdapter(adpater);


        //Getting reference to actionbar and doing customization
        toolbar = view.findViewById(R.id.app_bar_new);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        this.setHasOptionsMenu(true);

        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Profile");
        }else {
            Log.d(TAG, "onCreateView: actionBar object is null");
        }



        //Initializing fragments and managers
         signUp3Fragment = new SignUp3Fragment();
         signInFragment = new SignInFragment();

        return view;

    }

    /**
     * @brief Adds the current fragment to back stack and Loads the new fragment passed to it.
     * @param fragment
     */
    private void loadFragment(android.support.v4.app.Fragment fragment){
        try {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }catch (NullPointerException e){
            Log.d(TAG, "loadFragment: Exception" + e.getMessage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        HomeFragment.IS_TOUCH = false;
    }
}
