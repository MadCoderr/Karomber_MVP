package com.example.eapple.tripdatacollection.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eapple.tripdatacollection.R;
import com.example.eapple.tripdatacollection.adapter.CategoryAdapter;
import com.example.eapple.tripdatacollection.data.Preferences;
import com.example.eapple.tripdatacollection.data.pojo.Data;
import com.example.eapple.tripdatacollection.data.pojo.SourceData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
/*This activity is only responsible for showing the data of each document being selected in SearchCategoryActivity
* it will retrieve list of data from selected module i.e Attraction points, hotels etc.*/
public class CategoryActivity extends AppCompatActivity {

    TextView itemTitle, itemCount;
    ImageView itemImage;
    ProgressBar proBar;
    RelativeLayout relLayout;

    RecyclerView recycler;
    CategoryAdapter adapter;

    FirebaseFirestore store;
    List<Data> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_category);

        itemTitle = findViewById(R.id.lbl_item_title_category);
        itemCount = findViewById(R.id.lbl_item_count_category);
        itemImage = findViewById(R.id.img_item_category);
        proBar    = findViewById(R.id.pro_bar_category);
        relLayout = findViewById(R.id.rel_layout_category);

        recycler = findViewById(R.id.rec_view_item_category);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        String documentName = getIntent().getStringExtra(Preferences.ITEM_NAME);
        itemTitle.setText(documentName); // set the module name (hotel, restuarant etc)

        final String collectionName = getIntent().getStringExtra(Preferences.QUERY_NAME);

        dataList = new ArrayList<>();
        store = FirebaseFirestore.getInstance();
        // retreive all the data associated with this document
        store.collection(collectionName).document(documentName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        proBar.setVisibility(View.GONE);
                        relLayout.setVisibility(View.VISIBLE);
                        // if document exit get the list of data and pass it to adapter
                        if (documentSnapshot.exists()) {
                            SourceData source =  documentSnapshot.toObject(SourceData.class);
                            // if image is not null show the image with the help of picasso lib.
                            if (source.getImage() != null) {
                                Picasso.with(CategoryActivity.this)
                                        .load(source.getImage())
                                        .placeholder(R.drawable.place_holder_image)
                                        .error(R.drawable.error_holder_image)
                                        .into(itemImage);
                            }

                            dataList.addAll(source.getData());
                            adapter = new CategoryAdapter(CategoryActivity.this, collectionName, dataList);
                            recycler.setAdapter(adapter);
                        } else {
                            Log.d("firebase_check", "no data found, no document exits");
                            Toast.makeText(CategoryActivity.this,
                                    "no data found, no document exits", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    // Method-> to hide status bar which contain(batter, wifi, network) icons.
    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void onBackButtonPressed(View view) {
        finish();
    }

    public void showPopUpMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.pop_up_menu, menu.getMenu());
        menu.show();
    }
}
