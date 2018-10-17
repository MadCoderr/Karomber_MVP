package com.example.eapple.tripdatacollection.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eapple.tripdatacollection.HomeFragment;
import com.example.eapple.tripdatacollection.R;
import com.example.eapple.tripdatacollection.activities.SearchCategoriesActivity;
import com.example.eapple.tripdatacollection.adapter.SearchableAdapter;
import com.example.eapple.tripdatacollection.data.Preferences;
import com.example.eapple.tripdatacollection.data.pojo.CollectionNames;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterFragment extends Fragment {


    ImageButton btnCancelSearch;
    Button btnFilterSearch;
    EditText searchQuery;


    RecyclerView recycler;
    SearchableAdapter adapter;

    FirebaseFirestore store;
    List<String> listOfNames;

    public SearchFilterFragment() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_search_filter, container, false);

        store = FirebaseFirestore.getInstance();
        listOfNames = new ArrayList<>();

        btnCancelSearch = v.findViewById(R.id.btn_cancel_search);
        btnFilterSearch = v.findViewById(R.id.btn_filter_search);
        searchQuery = v.findViewById(R.id.edt_filter_search);
        recycler = v.findViewById(R.id.rec_view_filter_search);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        store.collection("collection_names")
                .document("names")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            CollectionNames names = documentSnapshot.toObject(CollectionNames.class);
                            listOfNames.addAll(names.getNames());
                            adapter = new SearchableAdapter(getContext(), listOfNames, searchQuery);
                            recycler.setAdapter(adapter);

                        } else {
                            Log.i("firebase_check", "no document found");
                            Toast.makeText(getContext(), "no record found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        searchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });

        // it will check the text being enter by user in edit text
        searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!listOfNames.isEmpty())
                {
                    if (editable.length() > 0) {
                        recycler.setVisibility(View.VISIBLE);
                        String text = searchQuery.getText().toString();
                        adapter.filter(text);
                    } else {
                        recycler.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        // if user click on X cancel button exit the current fragement and go back to previous one
        btnCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment.IS_TOUCH = false;
                searchQuery.getText().clear();
                getActivity().onBackPressed();
            }
        });


        // if user click on search image take the user input and pass it to SearchCategoriesActivity
        btnFilterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchQuery.getText().toString();
                query = query.toLowerCase().trim();

                if (query.length() <= 0) {
                    return;
                } else {

                    // check is the user query in listOfNames (mean in database) if not then show some kind of message
                    if (containTextInList(query)) {
                        Intent intent = new Intent(getActivity(), SearchCategoriesActivity.class);
                        intent.putExtra(Preferences.QUERY_NAME, query);
                        getActivity().startActivity(intent);
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getActivity(), "No record found for : " + query,
                                Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        return v;
    }

    private boolean containTextInList(String str) {
        for (String s : listOfNames) {
            if (s.toLowerCase().contains(str))
                return true;
        }
        return false;
    }



    @Override
    public void onPause() {
        super.onPause();
        hideKeyboard();
        HomeFragment.IS_TOUCH = false;
        searchQuery.getText().clear();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchQuery.getWindowToken(), 0);
    }
}
