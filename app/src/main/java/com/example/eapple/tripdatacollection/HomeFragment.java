package com.example.eapple.tripdatacollection;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.eapple.tripdatacollection.activities.SearchCategoriesActivity;
import com.example.eapple.tripdatacollection.data.Preferences;
import com.example.eapple.tripdatacollection.fragments.SearchFilterFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static boolean IS_TOUCH = false;

    EditText userQuery;

    SearchFilterFragment searchFilter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.example_layout, container, false);

        searchFilter = new SearchFilterFragment();

        userQuery = view.findViewById(R.id.tv_search);

        userQuery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!IS_TOUCH) {
                    Log.i("check_edit", "focused enter in edit text");
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                                    manager
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
                                            .replace(R.id.layout_container_home, searchFilter)
                                            .addToBackStack("HomeFragment")
                                            .commit();
                    IS_TOUCH = true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        IS_TOUCH = false;
    }
}
