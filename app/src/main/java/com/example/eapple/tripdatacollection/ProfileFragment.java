package com.example.eapple.tripdatacollection;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.RequiresApi;

import com.example.eapple.tripdatacollection.adapter.ProfileAdapter;
import com.example.eapple.tripdatacollection.create_account.sign_in.SignInFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private final String TAG = "ProfileFragment";
    private ImageView profileImg;
    private TextView userName;
    private TextView address;
    private View view;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    FragmentTransaction transaction;
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
        mAuth = FirebaseAuth.getInstance();

        //Initializing objects
        recyclerView = view.findViewById(R.id.profile_recyler_view);
        profileImg = view.findViewById(R.id.profile_image);
        userName = view.findViewById(R.id.tv_name);
        address = view.findViewById(R.id.tv_address);
        descriptions = getResources().getStringArray(R.array.icons_description_list);
        transaction = getActivity().getSupportFragmentManager().beginTransaction();

        //Setting up recyclerView
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ProfileAdapter adpater = new ProfileAdapter(getActivity(), icons, descriptions);
        recyclerView.setAdapter(adpater);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        // Checks if the user is signedIn, otherwise redirects him to login
        if(user == null){
            //User is not signedIn, redirects him to signIn screen
            SignInFragment fragment = new SignInFragment();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        HomeFragment.IS_TOUCH = false;
    }
}
