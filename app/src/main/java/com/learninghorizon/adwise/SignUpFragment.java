package com.learninghorizon.adwise;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by ramnivasindani on 3/22/16.
 */
public class SignUpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle saveInstanceState){
        super.onCreateView(layoutInflater, container, saveInstanceState);
        return layoutInflater.inflate(R.layout.signup_fragment,container, false);
    }
}
