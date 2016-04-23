package com.learninghorizon.adwise.home.offers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.offers.Adapter.OffersAdapter;
import com.learninghorizon.adwise.loginsignup.AdWiseApplication;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OffersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OffersFragment extends Fragment implements BeaconManager.ServiceReadyCallback, BeaconManager.MonitoringListener, BeaconManager.RangingListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BeaconManager beaconManager;
    // TODO: Rename and change types of parameters
    private String currentBeacon;
    private String previousBeacon;
    private Context activity;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    public OffersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OffersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OffersFragment newInstance(String param1, String param2) {
        OffersFragment fragment = new OffersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beaconManager = new BeaconManager(AdWiseApplication.getIntance().getApplicationContext());
        beaconManager.connect(this);
        beaconManager.setMonitoringListener(this);
        beaconManager.setRangingListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_offers, container, false);
        ButterKnife.bind(this,view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(new OffersAdapter(new String[] {"Air glide", "Air flow", "Bunjee", "fire dragon"}));
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        activity = context;
    }
    @Override
    public void onServiceReady() {
        Region region = new Region(getResources().getString(R.string.adwise_monitoring_identifier),null,null,null);
        beaconManager.startMonitoring(region);
        beaconManager.startRanging(region);
    }

    @Override
    public void onEnteredRegion(Region region, List<Beacon> list) {

    }

    @Override
    public void onExitedRegion(Region region) {

    }

    @Override
    public void onBeaconsDiscovered(Region region, List<Beacon> list) {
        if(null != list && !list.isEmpty() && null != activity) {
            Toast.makeText(activity.getApplicationContext(), "Found beacon : " + list.get(0).getMajor(), Toast.LENGTH_LONG).show();
            currentBeacon = String.valueOf(list.get(0).getMinor());
            if(currentBeacon != previousBeacon){
                //check for old listeners, start new timer
                //request new coupon
                currentBeacon = previousBeacon;
            }
        }
    }
}
