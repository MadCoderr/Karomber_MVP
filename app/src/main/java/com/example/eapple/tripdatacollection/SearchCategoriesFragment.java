package com.example.eapple.tripdatacollection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.eapple.tripdatacollection.adapter.MyAdapter;
import com.example.eapple.tripdatacollection.adapter.SearchCategoryAdapter;
import com.example.eapple.tripdatacollection.data.Preferences;
import com.example.eapple.tripdatacollection.data.SearchCategoryModel;


public class SearchCategoriesFragment extends Fragment {

    TextView displayTitle;
    ImageButton backButton;

    RecyclerView recycler;
    SearchCategoryAdapter adapter;

    public SearchCategoriesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_search_categories, container, false);

        recycler = view.findViewById(R.id.rv_search_cat);
        displayTitle = view.findViewById(R.id.tv_loc_search_cat);
        backButton = view.findViewById(R.id.btn_back_search_cat);

        Bundle bundle = this.getArguments();
        String queryName = bundle.getString(Preferences.QUERY_NAME);
        displayTitle.setText(queryName);

        recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recycler.setHasFixedSize(true);
        adapter = new SearchCategoryAdapter(getContext(), queryName, SearchCategoryModel.getCategoryModelList());
        recycler.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().popBackStack();
            }
        });

        return view;
    }

}
