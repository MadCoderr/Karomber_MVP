package com.example.eapple.tripdatacollection;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private final String TAG = "ProfileFragment";
    private TextView profileDesc;
    private Button btnSignUP;
    private Button btnLogin;
    private View view;
    private CreateAccountFragment createAccountFragment;
    private SignInFragment signInFragment;
    private android.support.v7.widget.Toolbar toolbar;


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
        profileDesc = view.findViewById(R.id.tv_profile_desc);
        btnLogin = view.findViewById(R.id.btn_login);
        btnSignUP = view.findViewById(R.id.btn_signUp);

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
         createAccountFragment = new CreateAccountFragment();
         signInFragment = new SignInFragment();

         //OnclickListners for Buttons
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(signInFragment);
            }
        });

        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(createAccountFragment);
            }
        });


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

}
