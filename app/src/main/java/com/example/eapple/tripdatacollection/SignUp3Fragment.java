package com.example.eapple.tripdatacollection;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp3Fragment extends Fragment {

    private final String TAG = "SignUp3Fragment";
    private EditText etUserName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnContinue;

    private View view;
    Toolbar toolbar;
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

        //Initializing objects
        etUserName = view.findViewById(R.id.user_name_field);
        etEmail = view.findViewById(R.id.email_field);
        etPassword = view.findViewById(R.id.password_field);
        btnContinue = view.findViewById(R.id.continue_btn);
        etConfirmPassword = view.findViewById(R.id.confirm_password_field);
        toolbar = view.findViewById(R.id.app_bar_new);
        mAuth = FirebaseAuth.getInstance();

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        if(actionBar != null){
            actionBar.setTitle("Profile");
            this.setHasOptionsMenu(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }else {
            Log.d(TAG, "onCreateView: actionBar object is null");
        }

        //Onclick Listeners
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Called");
                //Create Account
                getDataAndCreateAccount();
            }
        });

        return view;
    }

    /**
     * @brief This function creates account for user
     * on firebase database using email and password
     * @param email
     * @param password
     */
    private void createAccount(String userName, String email, String password) {
        Log.d(TAG, "createAccount: called with " + email );

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getActivity(), "CreateUserWithEmail: Success", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthUserCollisionException) {
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


    /**
     * @param email
     * @param password
     * @param confirmPassword
     * @return boolean valid status
     * @author Haroon khan
     *
     * @brief ValidateForm checks the complete form for valid inputs.
     * It checks empty, allowed characters range (regx) , input field limit
     * and generates error messages accordingly to the fields where error exists
     *
     */
    private boolean validateForm(String email, String password, String confirmPassword) {
        Log.d(TAG, "ValidateForm: Called");
        boolean valid = true;

        //Regular expressions
        String userNameRegx = "[a-zA-Z0-9_]+" ;        //Regular expression for userName
        String emailRegx = "[a-zA-Z0-9_@.]+" ;         //Regular expression for email
        String emailFormat = "[a-zA-Z0-9_@.]+" ;       //Regular expression for email format

        //Limit of the input fields
        int userNameLimit = 40;
        int passwordLimit = 40;
        int emailLimit = 40;

        //User Email validation'
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Required.");
            valid = false;
        } else if (!email.matches(emailFormat)) {
            etEmail.setError("Please enter a valid email");
            valid = false;
        } else if (!email.matches(emailRegx)) {
            etEmail.setError("Only characters a-z, A-Z, 0-9 and _ are allowed in user Name");
            valid = false;
        } else if (email.length() > emailLimit) {
            etEmail.setError("User Email limit exceeded. Allowed Limit is 40 characters");
            valid = false;
        } else {
            etEmail.setError(null);
        }
        Log.d("debug", "Email validation done");

        //User Password/Confirm Password validation
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Required.");
            valid = false;
        } else if (password.length() > passwordLimit) {
            etPassword.setError("Password limit exceeded. Allowed Limit is 40 characters");
            valid = false;
        } else if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password Miss-Match");
            valid = false;
        }
        Log.d("debug", "Password validation done");

        return valid;
    }

    private void getDataAndCreateAccount(){
        Log.d(TAG, "getDataAndCreateAccount: Called");
        String userName = etUserName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        //Validate Form
        if (!validateForm(email, password, confirmPassword)) {
            Log.d(TAG, "getDataAndCreateAccount: Form validation Error");
            return;
        }

        //Create Account
        createAccount(userName, email, password);

    }

}
