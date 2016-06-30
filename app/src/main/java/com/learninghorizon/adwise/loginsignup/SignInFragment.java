package com.learninghorizon.adwise.loginsignup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.HomeActivity;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ramnivasindani on 3/23/16.
 */
public class SignInFragment extends Fragment implements LoginSignupInterface, View.OnClickListener{

    @Bind(R.id.user_name)
    EditText userName;

    @Bind(R.id.password)
    EditText password;

    @Bind(R.id.login_error)
    TextView loginError;

    @Bind(R.id.login)
    Button login;
    private LoginSignUpPresenter loginSignUpPresenter;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        loginSignUpPresenter = new LoginSignUpPresenter(this,new LoginSignUpService());
        LoginSignUpService loginSignUpService = new LoginSignUpService();
        if(loginSignUpService.isAlreadyLogedIn()) {
            //startHomeAcitivity();
           /* try {
                loginSignUpPresenter.login();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle saveInstanceState){
        super.onCreateView(layoutInflater, container, saveInstanceState);
        View view =  layoutInflater.inflate(R.layout.signin_fragment,container, false);
        ButterKnife.bind(this,view);
        login.setOnClickListener(this);
        return view;
    }

    @Override
    public void setUserNameEmptyError(int resId) {
        userName.setError(getString(resId));
    }

    @Override
    public void setPasswordEmptyError(int resId) {
        password.setError(getString(resId));
    }

    @Override
    public void startHomeAcitivity() {
        Intent homeActivityIntent = new Intent(getContext(), HomeActivity.class);
        getActivity().startActivity(homeActivityIntent);
        getActivity().finish();
    }

    @Override
    public void showLoginError(int resId) {
        loginError.setVisibility(View.VISIBLE);
        loginError.setText(getString(resId));
    }

    @Override
    public void showSignupError(int resId) {

    }

    @Override
    public String getUserName() {
        return userName.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public Context getContext(){
        return getActivity().getApplicationContext();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:{
                try {
                    loginSignUpPresenter.login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        try {
            loginSignUpPresenter.login();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}