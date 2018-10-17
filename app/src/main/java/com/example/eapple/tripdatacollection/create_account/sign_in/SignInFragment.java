package com.example.eapple.tripdatacollection.create_account.sign_in;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eapple.tripdatacollection.ProfileFragment;
import com.example.eapple.tripdatacollection.R;
import com.example.eapple.tripdatacollection.create_account.sign_up.SignUp1Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment implements View.OnClickListener{

    private final String TAG = "SignInFragment";
    private TextView createAccountLink;
    private EditText emailField;
    private EditText passwordField;
    private Button continueBtn;
    private View view;
    FirebaseAuth mAuth;
    FragmentTransaction transaction;


    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        //Initializing objects
        passwordField = view.findViewById(R.id.et_password_signIn);
        emailField = view.findViewById(R.id.et_email_signIn);
        continueBtn = view.findViewById(R.id.btn_conitnue_signIn);
        createAccountLink = view.findViewById(R.id.tv_create_account_link_singIn);
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        mAuth = FirebaseAuth.getInstance();
        createAccountLink.setOnClickListener(this);
        continueBtn.setOnClickListener(this);

        //Onclick Listener for button and link
        return view;
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();

    }
    // [END on_start_check_user]


    private void signIn(){
        Log.d(TAG, "@signIn: Called");
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        //Validation needs to be implemented

        // [START sign_in_with_email]
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "@signIn: signInWithEmail:success");
                                updateUI();
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
                        passwordField.setError("Invalid Password");
                        //changeUI();
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), "Email or password is empty", Toast.LENGTH_SHORT).show();
        }
        // [END SignIn_user_with_email]
    }




    private void updateUI(){
        // load profile fragment
        ProfileFragment fragment = new ProfileFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        hideKeyboard();
        switch (id){
            case R.id.btn_conitnue_signIn:
                signIn();
                break;
            case R.id.tv_create_account_link_singIn:
                createAccount();
                break;
        }
    }

    private void createAccount(){
        SignUp1Fragment fragment = new SignUp1Fragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack("SignInFragment");
        transaction.commit();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailField.getWindowToken(), 0);
    }
}
