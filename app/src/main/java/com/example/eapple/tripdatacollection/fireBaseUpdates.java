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
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

/**
 * @file FirebaseUpdates.java
 * @author Haroon khan
 * @date 08 Sept, 2018
 *
 * @section Description
 * This file has functions to update data from local
 * database to main firebase database
 *
 */

public class fireBaseUpdates extends AppCompatActivity implements View.OnClickListener {

    private EditText userEmailField;
    private EditText userPasswordField;
    private EditText confirmPasswordField;
    private Button signUpBtn;
    private Button loginBtn;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_updates);

        // Views
        userEmailField = findViewById(R.id.user_name_field);
        userPasswordField = findViewById(R.id.email_field);
        confirmPasswordField = findViewById(R.id.password_field);
        signUpBtn = findViewById(R.id.signUp_btn);
        loginBtn = findViewById(R.id.continue_btn);

        //Set Click listener
        signUpBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }


    /**
     * @brief This function creates account for user
     * on firebase database using email and password
     * @param email
     * @param password
     * @param confirmPassword
     */
    private void createAccount(final String email, String password, String confirmPassword) {
        //Log.d(TAG, "createAccount:" + email);

        if (!validateForm(email, password, confirmPassword)) {
            return;
        }

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(fireBaseUpdates.this, "CreateUserWithEmail: Success", Toast.LENGTH_LONG).show();
                            //Log.d("debug", "createUserWithEmail:success");
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
                        //Toast.makeText(MainActivity.this, "This Email already Exists", Toast.LENGTH_LONG).show();
                        changeUI();
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
        Log.d("debug", "Email validation done");

        //User Password/Confirm Password validation
        if (TextUtils.isEmpty(password)) {
            userPasswordField.setError("Required.");
            valid = false;
        } else if (password.length() > passwordLimit) {
            userEmailField.setError("Password limit exceeded. Allowed Limit is 40 characters");
            valid = false;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordField.setError("Password Miss-Match");
            valid = false;
        }
        Log.d("debug", "Password validation done");

        return valid;
    }

        /*
        private void updateUI(FirebaseUser user) {
            hideProgressDialog();
            if (user != null) {
                mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                        user.getEmail(), user.isEmailVerified()));
                mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

                findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
                findViewById(R.id.email_password_fields).setVisibility(View.GONE);
                findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

                findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
            } else {
                mStatusTextView.setText(R.string.signed_out);
                mDetailTextView.setText(null);

                findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
                findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
                findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
            }
        }
        */

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signUp_btn) {
            String email = userEmailField.getText().toString();
            String password = userPasswordField.getText().toString();
            String confirmPassword = confirmPasswordField.getText().toString();

            if (validateForm(email, password, confirmPassword)) {
                createAccount(email, password, confirmPassword);
            }
        } else if (i == R.id.continue_btn) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }
    }

    public void changeUI() {
        loginBtn.setVisibility(View.VISIBLE);
    }

}
