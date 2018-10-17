package com.example.eapple.tripdatacollection.create_account.sign_up;


import android.app.Activity;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.eapple.tripdatacollection.ProfileFragment;
import com.example.eapple.tripdatacollection.R;
import com.example.eapple.tripdatacollection.data.Preferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp3Fragment extends Fragment {

    private final String TAG = "SignUp3Fragment";

    private EditText passwordField;
    private ProgressBar proBar;

    private Button btnContinue;
    private FragmentTransaction transaction;


    private View view;
    FirebaseAuth mAuth;

    public SignUp3Fragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up3, container, false);
        mAuth = FirebaseAuth.getInstance();

        //Initializing objects
        passwordField = view.findViewById(R.id.et_password_signup3);
        btnContinue = view.findViewById(R.id.btn_continue_singup3);
        proBar = view.findViewById(R.id.pro_bar_sign_up_3);

        transaction = getActivity().getSupportFragmentManager().beginTransaction();

        //Onclick Listeners
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Called");
                //Get data and Create Account
                hideKeyboard();
                getDataAndCreateAccount();
            }
        });

        return view;
    }

    /**
     * @param email
     * @param password
     * @brief This function creates account for user
     * on firebase database using email and password
     */
    private void createAccount(String userName, String email, String password) {
        Log.d(TAG, "createAccount: called with " + email);

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        proBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getActivity(), "sign up succesully", Toast.LENGTH_LONG).show();
                            updateUI();
                        } else {
                            Log.d(TAG, "fialed: " + task.getException().getMessage());
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthUserCollisionException) {
                    proBar.setVisibility(View.GONE);
                    String errorCode = ((FirebaseAuthUserCollisionException) e).getErrorCode();
                    if (errorCode.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                        Log.d(TAG, "onFailure: Email Already exists");
                        Toast.makeText(getActivity(), "This Email already Exists", Toast.LENGTH_LONG).show();
                        //changeUI();
                    }
                }
            }
        });
        ;
        // [END create_user_with_email]
    }

        /*
        private void signOut() {
            mAuth.signOut();
            updateUI(null);
        }
        */

    private void getDataAndCreateAccount() {
        Log.d(TAG, "getDataAndCreateAccount: Called");
        String userName = getArguments().getString(Preferences.USER_NAME);
        String email = getArguments().getString(Preferences.USER_EMAIL);
        String password = passwordField.getText().toString();

        Log.i("credential", "user: " + userName + " " + email + " " + password);

        //Validate Form
        if (password.isEmpty()) {
            Toast.makeText(getContext(), "password field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6){
            Toast.makeText(getContext(), "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
        } else {
            //Create Account
            proBar.setVisibility(View.VISIBLE);
            createAccount(userName, email, password);
        }
    }

    /**
     * Update the UI and load profile Fragment
     */
    private void updateUI() {
        ProfileFragment fragment = new ProfileFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);
    }

}
