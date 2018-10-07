package com.example.eapple.tripdatacollection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eapple.tripdatacollection.adapter.MyAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class gridViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private View view;
    GridLayoutManager layoutManager;

    public gridViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_grid_view, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        int imgId[] = {R.drawable.tile_attraction, R.drawable.tile_hotel, R.drawable.tile_restaurant,
                R.drawable.tile_bank, R.drawable.tile_gasstation, R.drawable.tile_mobilewallet,
                R.drawable.tile_rental, R.drawable.tile_toilet, R.drawable.tile_shoppingmart,
                R.drawable.tile_workshop, R.drawable.tile_hospital, R.drawable.tile_policestation,
                R.drawable.tile_workshop, R.drawable.tile_hospital, R.drawable.tile_policestation};

        MyAdapter myAdapter = new MyAdapter(getActivity(), imgId);
        recyclerView.setAdapter(myAdapter);

        return view;

    }



}
