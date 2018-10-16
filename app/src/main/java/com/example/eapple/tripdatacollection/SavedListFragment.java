package com.example.eapple.tripdatacollection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eapple.tripdatacollection.adapter.SavedListAdapter;
import com.example.eapple.tripdatacollection.adapter.SearchResultAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedListFragment extends Fragment {

    // This is just a test model to test the adapter.
    public class Model {
        public String Title;
        public String SubTitle;

        public Model(String title, String subTitle) {
            Title = title;
            SubTitle = subTitle;
        }
    }

    private final String TAG = "searchResultListFrag";
    RecyclerView recyclerView;
    View view;

    List<Model> modelList;

    public SavedListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_result_list, container, false);

        recyclerView = view.findViewById(R.id.search_result_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        modelList = new ArrayList<>();
        dummyData();

        SavedListAdapter adapter = new SavedListAdapter(getContext(), modelList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void dummyData() {
        modelList.add(new Model("Naran", "Khyber Pakhtunkhawa"));
        modelList.add(new Model("Kumrat", "Khyber Pakhtunkhawa"));
        modelList.add(new Model("Wagha Border", "Punjab"));
        modelList.add(new Model("Kashmir", "Punjab"));
    }

    @Override
    public void onPause() {
        super.onPause();
        HomeFragment.IS_TOUCH = false;
    }
}
