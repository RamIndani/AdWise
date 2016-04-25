package com.learninghorizon.adwise.home;

import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.coupon.CouponsActivity;
import com.learninghorizon.adwise.home.coupon.CouponsFragment;
import com.learninghorizon.adwise.loginsignup.AdWiseApplication;
import com.learninghorizon.adwise.widget.view.SlidingTabLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements ActionBar.TabListener{


    @Bind(R.id.pager)
    ViewPager mViewPager;
   /* @Bind(R.id.toolbar)
    Toolbar topToolBar;*/
    private SlidingTabLayout mSlidingTabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        //create beaconManager and start monitoring for any beacon.


        //setSupportActionBar(topToolBar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),getApplicationContext());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        //mSlidingTabLayout.setCustomTabView(R.layout.custom_tab, R.id.item_title);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.getItem(0);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                loadOffers("batmap");
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public void loadOffers(String coupon){
        Toast.makeText(getApplicationContext(), "Load currently valid coupons", Toast.LENGTH_LONG).show();
        Intent showCouponIntent = new Intent(this, CouponsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("coupon",coupon);
        showCouponIntent.putExtras(bundle);
        startActivity(showCouponIntent);
    }

}
