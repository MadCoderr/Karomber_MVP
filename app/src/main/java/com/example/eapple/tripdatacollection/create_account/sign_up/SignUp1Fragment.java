package com.example.eapple.tripdatacollection.create_account.sign_up;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.example.eapple.tripdatacollection.ProfileFragment;
import com.example.eapple.tripdatacollection.R;
import com.example.eapple.tripdatacollection.create_account.validate.InputTextValidation;
import com.example.eapple.tripdatacollection.data.Preferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 *
 * This fragment is associated with fragment_Sing_up1 xml file
 * This is the first step to signUp for the user.
 * It has three cases:
 *
 * 1) enterUserName: The userName is forward to another fragment SignUp2 by attaching it with fragment transaction and then
 * at last to SignUp3 fragment where it is then updated on Firebase.
 *
 * 2) signInWithFacebook: The user is directly signed In through facebook account and it's implemeted
 * in this fragment
 *
 * 3) signInWithGoogle: The user is directly signed In through google account and it's implemeted
 * in this fragment
 */
public class SignUp1Fragment extends Fragment implements View.OnClickListener{

    private final String TAG = "SignUp1Fragment";
    private EditText userNameField;
    private Button continueBtn;
    private Button signInFacebookBtn;
    private Button signInGoogleBtn;
    private InputTextValidation validator;
    FirebaseAuth mAuth;
    FragmentTransaction transaction;

    /**
     * Default constructor Constructor
     */
    public SignUp1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up1, container, false);

        userNameField = view.findViewById(R.id.et_username_signup1);
        continueBtn = view.findViewById(R.id.btn_continue_signup1);
        signInFacebookBtn = view.findViewById(R.id.btn_fb_signup1);
        signInGoogleBtn = view.findViewById(R.id.btn_google_signup1);
        validator = new InputTextValidation();
        mAuth = FirebaseAuth.getInstance();
        transaction = getActivity().getSupportFragmentManager().beginTransaction();

        //Setting Onclick listener
        continueBtn.setOnClickListener(this);
        signInGoogleBtn.setOnClickListener(this);
        signInFacebookBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_continue_signup1:
                Log.d(TAG, "@onClicked: Continue btn clicked");
                continueToSignup2();
                break;

            case R.id.btn_google_signup1:
                Log.d(TAG, "@onClicked: google SignUp btn clicked");
                signInWithGoogle();
                break;

            case R.id.btn_fb_signup1:
                Log.d(TAG, "@onClicked: Fb SignUp btn clicked");
                signInWithFacebook();

        }
    }

    @Override
    public void onStart() {
        Log.d(TAG, "@onStart: Called");
        super.onStart();
        //Check if the user is already signed In
        FirebaseUser user = mAuth.getCurrentUser();

        //If user is already signed In then move to profile screen else let him sign In on
        // the current screen.

        if(user != null){
            Log.d(TAG, "@onStart: User is already signedIn");
            //Load profile fragment
            ProfileFragment fragment = new ProfileFragment();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    /**
     * This function loads SignUp2 fragment and pass the userName data to it.
     */
    private void continueToSignup2(){
        Log.d(TAG, "@continueToSignUp2: Called");
        hideKeyboard();
        String userName = userNameField.getText().toString();
        //validator.validateUserName(userNameField, userName);
        SignUp2Fragment fragment = new SignUp2Fragment();
        Bundle args = new Bundle();
        args.putString(Preferences.USER_NAME, userName);
        fragment.setArguments(args);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack("SignUp1Fragment");
        transaction.commit();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(userNameField.getWindowToken(), 0);
    }

    private void signInWithGoogle(){
        //To be implemented later
    }

    private void signInWithFacebook(){
        //To be implemented later
    }
}
