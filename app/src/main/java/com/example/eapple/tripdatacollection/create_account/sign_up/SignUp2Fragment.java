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
import android.widget.Toast;

import com.example.eapple.tripdatacollection.R;
import com.example.eapple.tripdatacollection.data.Preferences;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp2Fragment extends Fragment implements View.OnClickListener{

    private final String TAG = "SignUP2Fragment";
    private View view;
    EditText emailField;
    Button continueBtn;
    FragmentTransaction transaction;

    public SignUp2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up2, container, false);
        emailField = view.findViewById(R.id.et_username_signup2);
        continueBtn = view.findViewById(R.id.btn_continue_signup2);
        transaction = getActivity().getSupportFragmentManager().beginTransaction();

        continueBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_continue_signup2){
            //Do first validation over here.. Which is to be done
            Log.d(TAG, "@onClick: Continue btn clicked");
            gotoSignUp3();
        }
    }

    void gotoSignUp3(){
        hideKeyboard();
        String userName = getArguments().getString(Preferences.USER_NAME);
        String email = emailField.getText().toString();

        if (!email.isEmpty()) {

            Bundle args = new Bundle();
            args.putString(Preferences.USER_NAME, userName);
            args.putString(Preferences.USER_EMAIL, email);
            SignUp3Fragment fragment = new SignUp3Fragment();
            fragment.setArguments(args);
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack("SignUp2Fragment");
            transaction.commit();
        } else {
            Toast.makeText(getContext(), "email cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailField.getWindowToken(), 0);
    }

}
