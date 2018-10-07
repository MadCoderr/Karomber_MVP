package com.example.eapple.tripdatacollection;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * @file addLocationFragment.java
 * @author Haroon khan
 * @date 9 Sept, 2018
 * @updated 10 Sept, 2018
 *
 * @section Description
 * This Fragment class navigates to different data
 * entery screens
 *
 */
public class AddLocationFragment extends Fragment implements View.OnClickListener{
    private ImageView attrPointImgView;
    private ImageView hotelImgView;
    private ImageView resturantImgView;
    private ImageView bankImgView;
    private ImageView gasStationImgView;
    private ImageView mobileWalletImgView;
    private ImageView rentalImgView;
    private ImageView toiletImgView;
    private ImageView shoppingMartImgView;
    private ImageView workshopImgView;
    private ImageView hospitalImg;
    private ImageView policeStationImgView;
    private static AddDataFragment addDataFrag;

    private static final String TAG = "addLocationFragment";

    public AddLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_location, container, false);
        Log.d(TAG, "onCreateView: Called");

        //Initializing Image views
        attrPointImgView = view.findViewById(R.id.attraction_img);
        hotelImgView = view.findViewById(R.id.hotel_img);
        resturantImgView = view.findViewById(R.id.resturant_img);
        bankImgView = view.findViewById(R.id.bank_img);
        gasStationImgView = view.findViewById(R.id.gas_station_img);
        mobileWalletImgView = view.findViewById(R.id.mobile_wallet_img);
        rentalImgView = view.findViewById(R.id.rental_img);
        toiletImgView = view.findViewById(R.id.tolit_img);
        shoppingMartImgView = view.findViewById(R.id.shoping_mart_img);
        workshopImgView = view.findViewById(R.id.workshop_img);
        hospitalImg = view.findViewById(R.id.hospital_img);
        policeStationImgView = view.findViewById(R.id.police_station_img);

        //Setting onclick listeners
        attrPointImgView.setOnClickListener(this);
        hotelImgView.setOnClickListener(this);
        resturantImgView.setOnClickListener(this);
        bankImgView.setOnClickListener(this);
        gasStationImgView.setOnClickListener(this);
        mobileWalletImgView.setOnClickListener(this);
        rentalImgView.setOnClickListener(this);
        toiletImgView.setOnClickListener(this);
        shoppingMartImgView.setOnClickListener(this);
        workshopImgView.setOnClickListener(this);
        hospitalImg.setOnClickListener(this);
        policeStationImgView.setOnClickListener(this);

        //Initializing addDataFrag
        addDataFrag = new AddDataFragment();

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.attraction_img:
                //Add attraction
                Log.d(TAG, "onClick: Attraction Image clicked");
                loadAddLocFrag("Attraction Point");
                break;

            case R.id.hotel_img:
                //Add hotel
                Log.d(TAG, "onClick: Hotel Image clicked");
                loadAddLocFrag("Hotel");
                break;

            case R.id.resturant_img:
                //Add hotel
                Log.d(TAG, "onClick: Restaurant Image clicked");
                loadAddLocFrag("Restaurant");
                break;

            case R.id.bank_img:
                //Add hotel
                Log.d(TAG, "onClick: bank Image clicked");
                loadAddLocFrag("Bank");
                break;

            case R.id.gas_station_img:
                //Add hotel
                Log.d(TAG, "onClick: Gas Station Image clicked");
                loadAddLocFrag("Gas Station");
                break;

            case R.id.mobile_wallet_img:
                //Add hotel
                Log.d(TAG, "onClick: Mobile wallet Image clicked");
                loadAddLocFrag("Mobile Wallet");
                break;

            case R.id.rental_img:
                //Add hotel
                Log.d(TAG, "onClick: Rental Image clicked");
                loadAddLocFrag("Rental Services");
                break;

            case R.id.tolit_img:
                //Add hotel
                Log.d(TAG, "onClick: Toilet Image clicked");
                loadAddLocFrag("Toilet");
                break;

            case R.id.shoping_mart_img:
                //Add hotel
                Log.d(TAG, "onClick: Shopping Image clicked");
                loadAddLocFrag("Shopping Mart");
                break;

            case R.id.workshop_img:
                //Add hotel
                Log.d(TAG, "onClick: workshop Image clicked");
                loadAddLocFrag("Workshop");
                break;

            case R.id.hospital_img:
                //Add hotel
                Log.d(TAG, "onClick: hospital Image clicked");
                loadAddLocFrag("Hospital");
                break;

            case R.id.police_station_img:
                //Add hotel
                Log.d(TAG, "onClick: Police Station Image clicked");
                loadAddLocFrag("Police Station");
                break;
        }
    }

    /**
     * @brief This function laods the addDataFrag
     * where we add the data about a location
     *
     * @param head
     */
    private void loadAddLocFrag(String head){
        try {
            Log.d(TAG, "loadAddLocFrag: Called");
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Bundle args = new Bundle();
            args.putString("head", head);
            addDataFrag.setArguments(args);
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            ft.replace(R.id.fragment_container, addDataFrag);
            ft.addToBackStack(null);
            ft.commit();

        }catch (NullPointerException e){
            Log.d(TAG, "loadAddLocFrag: Exception: " + e.getMessage());
        }

    }

}
