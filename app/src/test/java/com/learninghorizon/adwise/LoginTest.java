package com.learninghorizon.adwise;

import android.content.Context;

import com.learninghorizon.adwise.loginsignup.LoginSignUpPresenter;
import com.learninghorizon.adwise.loginsignup.LoginSignUpService;
import com.learninghorizon.adwise.loginsignup.SignInFragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by ramnivasindani on 4/4/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginTest {


    LoginSignUpPresenter loginSignUpPresenter;
    @Mock
    private SignInFragment signInFragment;
    @Mock
    private LoginSignUpService loginService;
    @Mock
    private Context mContext;
    @Before
    public void LoginViewTest(){
        loginSignUpPresenter = new LoginSignUpPresenter(signInFragment, loginService);
    }

    @Test
    public void testUserNameEmptyError() throws Exception {
        when(signInFragment.getUserName()).thenReturn("");
        loginSignUpPresenter.login();

        verify(signInFragment).setUserNameEmptyError(R.string.user_name_empty);
    }

    @Test
    public void testPasswordEmptyError() throws Exception {
        when(signInFragment.getUserName()).thenReturn("ramnivas.indani@sjsu.edu");
        when(signInFragment.getPassword()).thenReturn("");
        loginSignUpPresenter.login();
        verify(signInFragment).setPasswordEmptyError(R.string.password_empty);
    }

    @Test
    public void testUserNameANdPasswordIsEmpty() throws Exception {
        when(signInFragment.getUserName()).thenReturn("");
        when(signInFragment.getPassword()).thenReturn("");
        loginSignUpPresenter.login();
        verify(signInFragment).setUserNameEmptyError(R.string.user_name_empty);
    }

    @Test
    public void testWrongCredentials() throws Exception {
        when(signInFragment.getUserName()).thenReturn("ramnivas@sjsu.edu");
        when(signInFragment.getPassword()).thenReturn("welcome");
        //loginSignUpPresenter.login();
        //verify(signInFragment).showLoginError(R.string.login_failed);
    }

    @Test
    public void testLoginSuccess() throws Exception {
        when(signInFragment.getUserName()).thenReturn("ramnivas.indani@sjsu.edu");
        when(signInFragment.getPassword()).thenReturn("welcome123");
        //loginSignUpPresenter.login();
       // verify(loginService).login();
    }


}


