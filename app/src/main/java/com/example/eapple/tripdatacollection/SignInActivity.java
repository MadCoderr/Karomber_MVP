package com.example.eapple.tripdatacollection;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText userEmailField;
    private EditText userPasswordField;
    private Button loginBtn;
    private TextView forgotPasswordView;
    private TextView createAccountView;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Initialize different view elements
        userEmailField = findViewById(R.id.user_name_field);
        userPasswordField = findViewById(R.id.email_field);
        loginBtn = findViewById(R.id.continue_btn);
        forgotPasswordView = findViewById(R.id.forgot_password_link);
        createAccountView = findViewById(R.id.create_account_link);
        mAuth = FirebaseAuth.getInstance();

        //Set On click Listener
        loginBtn.setOnClickListener(this);
        forgotPasswordView.setOnClickListener(this);
        createAccountView.setOnClickListener(this);
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Toast.makeText(SignInActivity.this, "User is already signed In", Toast.LENGTH_LONG).show();
            updateUI();
        }else{
            Toast.makeText(SignInActivity.this, "User needs to sign In", Toast.LENGTH_LONG).show();
        }
    }
    // [END on_start_check_user]


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.continue_btn:
                login();
                break;
            case R.id.forgot_password_link:
                forgotPass();
                break;
            case R.id.create_account_link:
                createAccount();
        }

    }

    private void login(){
        String email = userEmailField.getText().toString();
        String password = userPasswordField.getText().toString();
        //Log.d(TAG, "signIn:" + email);

        if (!validateForm(email, password)) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("debug", "signInWithEmail:success");
                            Toast.makeText(SignInActivity.this, "Logged In Successfully",Toast.LENGTH_LONG).show();
                            //FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthInvalidUserException){
                    String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();
                    if(errorCode.equals("ERROR_USER_DISABLED")){
                        Toast.makeText(SignInActivity.this, "This User has been disabled", Toast.LENGTH_LONG).show();
                        //changeUI();
                    } else if(errorCode.equals("ERROR_USER_NOT_FOUND")){
                        Toast.makeText(SignInActivity.this, "Email not found", Toast.LENGTH_LONG).show();
                    }
                } else if(e instanceof FirebaseAuthInvalidCredentialsException){
                    String errorCode = ((FirebaseAuthInvalidCredentialsException) e).getErrorCode();
                    //Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_LONG).show();
                    userPasswordField.setError("Invalid Password");
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
            userEmailField.setError("Required.");
            valid = false;
        } else if (!email.matches(emailFormat)) {
            userEmailField.setError("Please enter a valid email");
            valid = false;
        } else if (!email.matches(emailRegx)) {
            userEmailField.setError("Only characters a-z, A-Z, 0-9 and _ are allowed in user Name");
            valid = false;
        } else if (email.length() > emailLimit) {
            userEmailField.setError("User Email limit exceeded. Allowed Limit is 40 characters");
            valid = false;
        } else {
            userEmailField.setError(null);
        }
        //Log.d("debug", "Email validation done");

        //User Password/Confirm Password validation
        if (TextUtils.isEmpty(password)) {
            userPasswordField.setError("Required.");
            valid = false;
        } else if (password.length() > passwordLimit) {
            userEmailField.setError("Password limit exceeded. Allowed Limit is 40 characters");
            valid = false;
        }
        //Log.d("debug", "Password validation done");

        return valid;
    }

    private void forgotPass(){
        Toast.makeText(SignInActivity.this, "forgot password clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void createAccount(){
        //Toast.makeText(LoginActivity.this, "Create Account clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, fireBaseUpdates.class);
        startActivity(intent);
    }
    private void updateUI(){
        Intent intent = new Intent(this, UploadDataActivity.class);
        startActivity(intent);
    }

}
