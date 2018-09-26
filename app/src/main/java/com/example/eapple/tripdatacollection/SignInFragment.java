package com.example.eapple.tripdatacollection;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private TextView forgotPasswordLink;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnContinue;
    private View view;
    FirebaseAuth mAuth;


    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        //Initializing objects
        forgotPasswordLink = view.findViewById(R.id.forgot_password_link);
        etEmail = view.findViewById(R.id.user_name_field);
        etPassword = view.findViewById(R.id.email_field);
        btnContinue = view.findViewById(R.id.continue_btn);
        mAuth = FirebaseAuth.getInstance();

        //Onclick Listener for button and link
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

        return view;
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Toast.makeText(getActivity(), "User is already signed In", Toast.LENGTH_LONG).show();
            //updateUI();
        }else{
            Toast.makeText(getActivity(), "User needs to sign In", Toast.LENGTH_LONG).show();
        }
    }
    // [END on_start_check_user]


    private void login(){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        //Log.d(TAG, "signIn:" + email);

        if (!validateForm(email, password)) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("debug", "signInWithEmail:success");
                            Toast.makeText(getActivity(), "Logged In Successfully",Toast.LENGTH_LONG).show();
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthInvalidUserException){
                    String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();
                    if(errorCode.equals("ERROR_USER_DISABLED")){
                        Toast.makeText(getActivity(), "This User has been disabled", Toast.LENGTH_LONG).show();
                        //changeUI();
                    } else if(errorCode.equals("ERROR_USER_NOT_FOUND")){
                        Toast.makeText(getActivity(), "Email not found", Toast.LENGTH_LONG).show();
                    }
                } else if(e instanceof FirebaseAuthInvalidCredentialsException){
                    String errorCode = ((FirebaseAuthInvalidCredentialsException) e).getErrorCode();
                    //Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_LONG).show();
                    etPassword.setError("Invalid Password");
                    //changeUI();
                }
            }
        });
        // [END SignIn_user_with_email]
    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;

        //Regular expressions
        String emailRegx =    "[a-zA-Z0-9_@.]+";         //Regular expression for email
        String emailFormat = "[a-zA-Z0-9_@.]+";       //Regular expression for email format

        //Limit of the input fields
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
            etEmail.setError("Only characters a-z, A-Z, 0-9, @ and _ are allowed in Email");
            valid = false;
        } else if (email.length() > emailLimit) {
            etEmail.setError("User Email limit exceeded. Allowed Limit is 40 characters");
            valid = false;
        } else {
            etEmail.setError(null);
        }
        //Log.d("debug", "Email validation done");

        //User Password/Confirm Password validation
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Required.");
            valid = false;
        } else if (password.length() > passwordLimit) {
            etPassword.setError("Password limit exceeded. Allowed Limit is 40 characters");
            valid = false;
        }
        //Log.d("debug", "Password validation done");

        return valid;
    }


    private void forgotPassword(){
        //Toast.makeText(getActivity(), "forgot password clicked", Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(this, ForgotPasswordActivity.class);
        //startActivity(intent);
    }


    private void updateUI(){

        //Intent intent = new Intent(this, UploadDataActivity.class);
        //startActivity(intent);
    }

}
