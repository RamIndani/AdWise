package com.learninghorizon.adwise.loginsignup;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.learninghorizon.adwise.R;

import butterknife.Bind;

public class LoginSignup extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup_fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.signup_signin_container, new SignInFragment());
        fragmentTransaction.commit();

    }

    public void signUp(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.signup_signin_container, new SignUpFragment());
        fragmentTransaction.addToBackStack("signup");
        fragmentTransaction.commit();
    }
}
