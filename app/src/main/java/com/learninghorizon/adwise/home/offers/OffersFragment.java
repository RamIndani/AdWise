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
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.GsonBuilder;
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.stream.JsonReader;
import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.offers.Adapter.OffersAdapter;
import com.learninghorizon.adwise.home.offers.service.OffersService;
import com.learninghorizon.adwise.home.offers.util.OffersPresenter;
import com.learninghorizon.adwise.home.spot.Spot;
import com.learninghorizon.adwise.loginsignup.AdWiseApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OffersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OffersFragment extends Fragment implements OffersPresenter,BeaconManager.ServiceReadyCallback, BeaconManager.MonitoringListener, BeaconManager.RangingListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BeaconManager beaconManager;
    // TODO: Rename and change types of parameters
    private Beacon currentBeacon;
    private Beacon previousBeacon;
    private UUID uuid;
    private int measuredPower;
    private Context activity;
    private OffersService offersService;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private OffersAdapter offersAdapter;
    private List<Spot> allSpots;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_offers, container, false);
        ButterKnife.bind(this,view);
        recyclerView.setHasFixedSize(true);
        offersService = new OffersService();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        allSpots = new ArrayList<Spot>();
        offersAdapter = new OffersAdapter(allSpots);
        recyclerView.setAdapter(offersAdapter);
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

    }


    @Override
    public void onEnteredRegion(Region region, List<Beacon> list) {
        //getLocationMapId and all spots

        beaconManager.setRangingListener(this);
        beaconManager.startRanging(region);
    }

    @Override
    public void onExitedRegion(Region region) {
        beaconManager.stopRanging(region);
        currentBeacon = null;
    }

    @Override
    public void onBeaconsDiscovered(Region region, List<Beacon> list) {
        if(null != list && !list.isEmpty() && null != activity) {
            list = sortBeacons(new ArrayList(list));
            currentBeacon = list.get(0);

            if(!currentBeacon.equals(previousBeacon) && measuredPower > list.get(0).getMeasuredPower()){
                Toast.makeText(activity.getApplicationContext(), "Found new beacon : " + list.get(0).getProximityUUID(), Toast.LENGTH_LONG).show();
                //check for old listeners, start new timer
                //request new coupon
                if(null != previousBeacon && null != uuid) {
                    if (!currentBeacon.getProximityUUID().equals(previousBeacon.getProximityUUID())) {
                        if(!uuid.equals(currentBeacon.getProximityUUID())) {
                            offersService.getLocationId(String.valueOf(currentBeacon.getProximityUUID()), this);
                            uuid = currentBeacon.getProximityUUID();
                        }
                    }
                }else{
                    offersService.getLocationId(String.valueOf(currentBeacon.getProximityUUID()).toUpperCase(), this);
                }
                previousBeacon = currentBeacon;
                uuid = currentBeacon.getProximityUUID();
            }

        }
    }

    private List<Beacon> sortBeacons(List<Beacon> list) {
        Collections.sort(list, new Comparator<Beacon>() {
            @Override
            public int compare(Beacon lhs, Beacon rhs) {
                return lhs.getMeasuredPower()>rhs.getMeasuredPower()?0:1;
            }
        });
        return list;
    }

    @Override
    public void updateLocationId(String locationId) {
        if(!locationId.isEmpty()) {
            offersService.getAllSpots(locationId, this);
        }
    }

    @Override
    public void updateSpots(JSONArray response, boolean b) {
        if(null != response) {
            Spot[] spots = new Gson().fromJson(response.toString(), Spot[].class);
            allSpots.clear();
            allSpots.addAll(Arrays.asList(spots));
            offersAdapter.notifyDataSetChanged();
        }
    }
}
