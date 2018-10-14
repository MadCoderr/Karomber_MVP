package com.example.eapple.tripdatacollection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eapple.tripdatacollection.adapter.SearchResultAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class searchResultListFragment extends Fragment {

    private final String TAG = "searchResultListFrag";
    RecyclerView recyclerView;
    View view;
    private int [] images = {
            R.drawable.search_result_thumbnail,
            R.drawable.search_result_thumbnail,
            R.drawable.search_result_thumbnail

    };
    private String [] names = {
            "Naran",
            "Naran",
            "Naran",
    };

    private String [] tags = {
            "Attraction Point",
            "Attraction Point",
            "Attraction Point"
    };
    private String [] distances = {
            "30km",
            "30km",
            "30km"

    };
    private String [] timeToReach = {
            "3hrs 20mnts",
            "3hrs 20mnts",
            "3hrs 20mnts"
    };

    public searchResultListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_result_list, container, false);
        recyclerView = view.findViewById(R.id.search_result_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        Log.d(TAG, "onCreateView: Calls searchResultAdapter");
        SearchResultAdapter adapter = new SearchResultAdapter(getActivity(), images, names, tags, distances, timeToReach);

        recyclerView.setAdapter(adapter);
        return view;
    }

}
