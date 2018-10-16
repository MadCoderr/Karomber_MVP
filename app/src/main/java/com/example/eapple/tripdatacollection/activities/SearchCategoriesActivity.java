package com.example.eapple.tripdatacollection.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.eapple.tripdatacollection.R;
import com.example.eapple.tripdatacollection.adapter.SearchCategoryAdapter;
import com.example.eapple.tripdatacollection.data.Preferences;
import com.example.eapple.tripdatacollection.data.SearchCategoryModel;
/*This activity only show the list of modules for user query
* By modules i mean (attraction points, hotels, resturants, gasstation etc).*/
public class SearchCategoriesActivity extends AppCompatActivity {

    TextView displayTitle;

    RecyclerView recycler;
    SearchCategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_categories);

        recycler = findViewById(R.id.rv_search_cat);
        displayTitle = findViewById(R.id.tv_loc_search_cat);

        String queryName = getIntent().getStringExtra(Preferences.QUERY_NAME);
        displayTitle.setText(queryName);

        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.setHasFixedSize(true);
        adapter = new SearchCategoryAdapter(this, queryName,SearchCategoryModel.getCategoryModelList());
        recycler.setAdapter(adapter);
    }
}
