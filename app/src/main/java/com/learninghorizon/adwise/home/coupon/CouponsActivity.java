package com.learninghorizon.adwise.home.coupon;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learninghorizon.adwise.R;

import org.apache.maven.artifact.ant.shaded.cli.Commandline;

public class CouponsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);
        String couponCode = getIntent().getExtras().getString("coupon");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CouponsFragment couponsFragment = new CouponsFragment();
        couponsFragment.setArguments(getIntent().getExtras());
        fragmentTransaction.add(R.id.coupon, new CouponsFragment());
        fragmentTransaction.commit();
    }
}
