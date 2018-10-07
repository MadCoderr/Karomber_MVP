package com.example.eapple.tripdatacollection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eapple.tripdatacollection.adapter.LocationResultAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class locationsResult extends Fragment {
    View view;
    private int [] images = {R.drawable.tile_hospital, R.drawable.tile_hospital,
            R.drawable.tile_hospital,R.drawable.tile_hospital, R.drawable.tile_hospital };
    private String [] names = {"Naran", "Kaghan", "swat", "Peshawar", "Queta"};
    private String [] tags = {"Naran", "Kaghan", "swat", "Peshawar", "Queta"};
    private String [] distances = {"Naran", "Kaghan", "swat", "Peshawar", "Queta"};
    private String [] timeToReach = {"Naran", "Kaghan", "swat", "Peshawar", "Queta"};

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    public locationsResult() {
        // Required empty publicm constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_locations_result, container, false);
        LocationResultAdapter adapter = new LocationResultAdapter(getActivity(), images, names, tags, distances, timeToReach);
        recyclerView = view.findViewById(R.id.recycler_view_new);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
