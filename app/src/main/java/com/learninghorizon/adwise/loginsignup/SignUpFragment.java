package com.learninghorizon.adwise.loginsignup;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.learninghorizon.adwise.R;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by ramnivasindani on 3/22/16.
 */
public class SignUpFragment extends Fragment implements LoginSignupInterface{

    @Bind(R.id.user_name)
    EditText userName;

    @Bind(R.id.password)
    EditText password;

    @Bind(R.id.login_error)
    TextView loginError;

    @Bind(R.id.signup_new_user)
    Button signup;
    private LoginSignUpPresenter loginSignUpPresenter;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        loginSignUpPresenter = new LoginSignUpPresenter(this,new LoginSignUpService());
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle saveInstanceState){
        super.onCreateView(layoutInflater, container, saveInstanceState);
        View view = layoutInflater.inflate(R.layout.signup_fragment,container, false);
        ButterKnife.bind(this,view);

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

    }

    @Override
    public void showLoginError(int resId) {

    }

    @Override
    public void showSignupError(int resId) {
        loginError.setVisibility(View.VISIBLE);
        loginError.setText(getString(resId));
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


    @OnClick (R.id.signup_new_user)
    public void signup(){
        try {
            loginSignUpPresenter.signup();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
