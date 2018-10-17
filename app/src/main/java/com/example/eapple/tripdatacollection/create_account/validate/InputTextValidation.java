package com.example.eapple.tripdatacollection.create_account.validate;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * This file validates the based based on regex provided in here.
 * It works for different form of inputs including:
 *
 * 1) Email
 * 2) User Name
 * 3) Password
 *
 *
 */
public class  InputTextValidation {

    private final String TAG = "InputTextValidation";
    //Regular expressions
    private final String USERNANME_REGX = "[a-zA-Z0-9_]+" ;        //Regular expression for userName
    private final String EMAIL_REGX = "[a-zA-Z0-9_@.]+" ;         //Regular expression for email
    //private final String PASSWORD_REGX;

    //Limit of the input fields
    private final int USERNAME_LIMIT = 40;
    private final int PASSWORD_LIMIT = 40;
    private final int EMAIL_LIMIT = 40;


    //Default constructor
    public InputTextValidation(){

    }

    public void validateUserName(EditText view, String userName){

    }

    public boolean validateEmail(EditText view, String email){
        boolean valid = true;

        //User Email validation'
        if (TextUtils.isEmpty(email)) {
            view.setError("Required.");
            valid = false;
        } else if (!email.matches(EMAIL_REGX)) {
            view.setError("Please enter a valid email");
            valid = false;
        } else if (!email.matches(EMAIL_REGX)) {
            view.setError("Only characters a-z, A-Z, 0-9 and _ are allowed in user Name");
            valid = false;
        } else if (email.length() > EMAIL_LIMIT) {
            view.setError("User Email limit exceeded. Allowed Limit is 40 characters");
            valid = false;
        } else {
            view.setError(null);
        }
        Log.d(TAG, "@validateEmail: Email validation done");

        return valid;
    }

    public boolean validatePassword(EditText view, String password){

        boolean valid = true;
        //User Password/Confirm Password validation
        if (TextUtils.isEmpty(password)) {
            view.setError("Required.");
            valid = false;
        } else if (password.length() > PASSWORD_LIMIT) {
            view.setError("Password limit exceeded. Allowed Limit is 40 characters");
            valid = false;
        }

        Log.d(TAG, "@validatePassword: Password validation done");

        return valid;
    }
}