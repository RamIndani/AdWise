package com.learninghorizon.adwise;

import android.app.Activity;
import android.content.Context;

import com.learninghorizon.adwise.loginsignup.LoginSignUpPresenter;
import com.learninghorizon.adwise.loginsignup.LoginSignUpService;
import com.learninghorizon.adwise.loginsignup.LoginSignup;
import com.learninghorizon.adwise.loginsignup.SignInFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ramnivasindani on 4/12/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml")
public class LoginRobolectic {

    LoginSignUpPresenter loginSignUpPresenter;
    @Mock
    private SignInFragment signInFragment;
    @Mock
    private LoginSignUpService loginService;
    @Mock
    private Context mContext;
    private Activity activity;
    @Before
    public void LoginViewTest(){
        loginSignUpPresenter = new LoginSignUpPresenter(signInFragment, loginService);
    }

    @Before
    public void setup()  {
        activity = Robolectric.buildActivity(LoginSignup.class)
                .create().get();

    }

}
