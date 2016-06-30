package com.learninghorizon.adwise.home.offers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.coupon.Coupon;
import com.learninghorizon.adwise.home.coupon.CouponsActivity;
import com.learninghorizon.adwise.home.favorites.FavoritesFragment;
import com.learninghorizon.adwise.home.favorites.adapter.FavoritesAdapter;
import com.learninghorizon.adwise.home.favorites.service.FavoriteService;
import com.learninghorizon.adwise.home.favorites.util.FavoritesDataUtil;
import com.learninghorizon.adwise.home.favorites.util.FavoritesPresenter;
import com.learninghorizon.adwise.home.offers.service.OffersService;
import com.learninghorizon.adwise.home.offers.util.OffersPresenter;
import com.learninghorizon.adwise.home.spot.Spot;
import com.learninghorizon.adwise.home.spot.util.SpotDataUtil;
import com.learninghorizon.adwise.home.user.UserDataUtil;
import com.learninghorizon.adwise.loginsignup.AdWiseApplication;
import com.oym.indoor.Building;
import com.oym.indoor.ConnectCallback;
import com.oym.indoor.Floor;
import com.oym.indoor.GoIndoor;
import com.oym.indoor.LocationBroadcast;
import com.oym.indoor.LocationResult;
import com.oym.indoor.NotificationResult;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;



/**
 * Created by ramnivasindani on 4/28/16.
 */
public class OffersMapFragment extends Fragment implements FavoritesPresenter,ConnectCallback,OffersPresenter,BeaconManager.ServiceReadyCallback, BeaconManager.MonitoringListener, BeaconManager.RangingListener, GoogleMap.OnMarkerClickListener, View.OnDragListener, View.OnClickListener {
    private MapView mMapView;;
    private GoogleMap googleMap;
    private MarkerOptions marker;
    private GoIndoor go;
    private FavoritesAdapter favoritesAdapter;
    private List<Spot> allSpots;
    Handler offersHandler;
    Handler timerHandler;
    private Runnable timerRunnable;
    private Beacon currentBeacon;
    private Spot currentSpot;
    private Beacon previousBeacon;
    private UUID uuid;
    private int measuredPower;
    private Context activity;
    private OffersService offersService;
    private BeaconManager beaconManager;
    private FloatingActionButton favoriteFloatingButton;
    private FloatingActionButton offerFlaotingButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        offersService = new OffersService();
        allSpots = new ArrayList<Spot>();
        favoritesAdapter = new FavoritesAdapter(allSpots);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.map, container, false);


        try {
            mMapView = (MapView) view.findViewById(R.id.mapView);
            favoriteFloatingButton = (FloatingActionButton) view.findViewById(R.id.favorite);
            offerFlaotingButton = (FloatingActionButton) view.findViewById(R.id.offer);
            offerFlaotingButton.setOnClickListener(this);
            favoriteFloatingButton.setOnClickListener(this);
            mMapView.onCreate(savedInstanceState);
            mMapView.setOnDragListener(this);
            mMapView.onResume();// needed to get the map to display immediately


            googleMap = mMapView.getMap();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private boolean isAlreadyZoomedIn = false;
    private Marker userMark;
    private LocationBroadcast broadcast = new LocationBroadcast() {
        @Override
        public void onLocation(LocationResult location) {
            if(null == marker) {
                marker = new MarkerOptions().position(
                        new LatLng(location.latitude, location.longitude)).title(getActivity().getString(R.string.you_are_here));
            }else{

            }

            // Changing marker icon
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            Building building = go.getBuildings(location.building);
            googleMap.setOnMarkerClickListener(OffersMapFragment.this);
            ArrayList<Floor> floors = building.getFloorsList();
            UrlTileProvider tiles = floors.get(0).getTileProvider();
            googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(tiles));
            // adding marker
            if(null == userMark) {
                userMark = googleMap.addMarker(marker);
            }else{
                userMark.setPosition(new LatLng(location.latitude, location.longitude));
            }
            if(!isAlreadyZoomedIn) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.latitude, location.longitude)).zoom(20).build();
                googleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
                isAlreadyZoomedIn = true;
            }

        }
        @Override
        public void onNotification(NotificationResult notification) {
            // TODO Auto-generated method stub
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


       // initilizeMap();
    }
    private void initilizeMap() {
        if (googleMap == null) {
            //googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        go = new GoIndoor.Builder()
                .setContext(getActivity())
                .setAccount("sjsu")
                .setPassword("4a86rd6")
                .setConnectCallback(this)
                .build();
        beaconManager = new BeaconManager(AdWiseApplication.getIntance().getApplicationContext());
        beaconManager.connect(this);
        beaconManager.setMonitoringListener(this);
        offersHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.getData().getBoolean("history")) {
                }else if(msg.getData().getBoolean("offerDetails")){
                    offersService.getOfferDetails(currentBeacon);
                }else if(msg.getData().getBoolean("offer")){
                    offersService.getOffer(currentBeacon);
                }
                return true;
            }
        });

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onStop(){
        super.onStop();
        if(null != timerHandler && null !=timerRunnable){
            timerHandler.removeCallbacks(timerRunnable);
        }
        if(null != go){
            go.stopLocate();
        }
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
    public void onConnected() {
        go.startLocate(broadcast);
    }

    @Override
    public void onConnectFailure(Exception e) {

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
                //Toast.makeText(activity.getApplicationContext(), "Found new beacon : " + list.get(0).getProximityUUID(), Toast.LENGTH_LONG).show();
                //check for old listeners, start new timer
                //request new coupon
                if(null != previousBeacon && null != uuid) {
                    if (!currentBeacon.getProximityUUID().equals(previousBeacon.getProximityUUID())) {
                        if(!String.valueOf(uuid).toLowerCase().trim().equals(String.valueOf(currentBeacon.getProximityUUID()).toLowerCase().trim())) {
                            offersService.getLocationId(String.valueOf(currentBeacon.getProximityUUID()), this);
                            uuid = currentBeacon.getProximityUUID();
                            startTimer(currentBeacon);
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

    private void startTimer(final Beacon offersForBeacon) {
        try {
            timerHandler = new Handler();
            timerRunnable = new Runnable() {
                @Override
                public void run() {
                    if (currentBeacon.equals(offersForBeacon)) {
                        try {
                            offersService.recordUserHistory(findCurrentSpotId(String.valueOf(currentBeacon.getMajor()),
                                    String.valueOf(currentBeacon.getMinor()), String.valueOf(currentBeacon.getProximityUUID())),
                                    UserDataUtil.getInstance().getUserEmailId(),
                                    OffersMapFragment.this, currentBeacon);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            timerHandler.postDelayed(timerRunnable, Integer.valueOf(getActivity().getResources().getString(R.string.history_timer)));
        }catch(Exception e){
            e.printStackTrace();
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
            if(allSpots.isEmpty()) {
                allSpots.clear();
                allSpots.addAll(Arrays.asList(spots));
            }
                SpotDataUtil.getInstance().setSpots(allSpots);
                for (Spot beacon : allSpots) {
                    for (com.oym.indoor.Beacon location : go.getBeacons()) {
                        if (location.getUuid().equalsIgnoreCase(beacon.getUuid().toString()) &&
                                location.getMajor() == Integer.valueOf(beacon.getMajorId()) &&
                                location.getMinor() == Integer.valueOf(beacon.getMinorId())) {
                            marker = new MarkerOptions().position(
                                    new LatLng(location.getLatitude(), location.getLongitude())).title(beacon.getSpotName());
                            marker.icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            googleMap.addMarker(marker);
                            FavoritesFragment.LoadFavorites();
                            FavoritesFragment.notifyAdapter();
                            break;
                        }
                    }
                }
                //favoritesAdapter.notifyDataSetChanged();
                startTimer(currentBeacon);

        }
    }

    @Override
    public void updateOffersForSpot(String response, boolean b, Beacon beacon){
        Coupon[] coupons = new Gson().fromJson(response, Coupon[].class);
        if(currentBeacon.equals(beacon)) {

            startTimerForCoupon(beacon,coupons);
        }

    }

    private void startTimerForCoupon(final Beacon beacon, final Coupon[] coupons) {
        if(null != coupons && coupons.length >0) {
            final List<Coupon> couponsList = new ArrayList(Arrays.asList(coupons));
            Collections.sort(couponsList);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayCoupon(couponsList.get(0));
                    addCouponToSpot(couponsList.get(0), beacon);
                    couponsList.remove(0);
                    if (couponsList.size() != 0) {
                        handler.postDelayed(this, 0);
                    }
                }
            }, couponsList.get(0).getCouponStartTime());
        }
    }

    private void addCouponToSpot(Coupon coupon, Beacon beacon) {
        for(Spot spot:allSpots){
            if(spot.getUuid().trim().equalsIgnoreCase(String.valueOf(beacon.getProximityUUID()).trim())
                    && Integer.valueOf(spot.getMajorId()) == beacon.getMajor()
                    && Integer.valueOf(spot.getMinorId()) == beacon.getMinor()){
                if(!spot.contains(coupon)) {
                    spot.addActiveCoupons(coupon);
                }
                break;
            }
        }
    }

    private void displayCoupon(Coupon coupon) {
        Toast.makeText(getActivity().getApplicationContext(), coupon.getCode(), Toast.LENGTH_LONG).show();
        coupon.setCouponActiveTime(new Date().getTime());
    }

    private void saveCoupons(Coupon[] coupons, Beacon beacon) {
        for(Spot spot : allSpots){
            if(spot.getMajorId().equals(beacon.getMajor()) &&
                    spot.getMinorId().equals(beacon.getMinor()) &&
                    spot.getUuid().equals(beacon.getProximityUUID())){
                spot.setCoupons(Arrays.asList(coupons));
                break;
            }
        }
    }

    private String findCurrentSpotId(final String majorId, final String minorId, final String uuid){
        String beaconId = null;
        for(Spot spot : allSpots){
            if(spot.getMajorId().trim().equals(majorId.trim())) {
                if (spot.getMinorId().trim().equals(minorId.trim())) {
                    if (spot.getUuid().trim().equalsIgnoreCase(uuid.toLowerCase().trim())) {
                        beaconId = spot.getBeaconId();
                        break;
                    }
                }
            }
        }
        return beaconId;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        OffersDataUtil.getInstance().clear();
        offerFlaotingButton.setVisibility(View.INVISIBLE);
        favoriteFloatingButton.setVisibility(View.INVISIBLE);
        for(Spot beacon : allSpots) {
            if(beacon.getSpotName().trim().equalsIgnoreCase(marker.getTitle().trim())){
                currentSpot = beacon;
                OffersDataUtil.getInstance().setCurrentSpot(beacon);
                offerFlaotingButton.setVisibility(View.INVISIBLE);
                if(!beacon.getCoupons().isEmpty()){
                    offerFlaotingButton.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
                            offerFlaotingButton.getLayoutParams();
                    offerFlaotingButton.setLayoutParams(layoutParams);
                    offerFlaotingButton.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab));
                    offerFlaotingButton.setClickable(true);
                }
                if(isFavorite(beacon)){
                    favoriteFloatingButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                }else{
                    favoriteFloatingButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
                        favoriteFloatingButton.getLayoutParams();
                favoriteFloatingButton.setLayoutParams(layoutParams);
                favoriteFloatingButton.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab));
                favoriteFloatingButton.setClickable(true);

                break;
            }

            if(marker.getTitle().contains(getActivity().getString(R.string.you_are_here))){
                offerFlaotingButton.setVisibility(View.INVISIBLE);
                favoriteFloatingButton.setVisibility(View.INVISIBLE);
            }
        }
        return false;
    }

    private boolean isFavorite(Spot beacon) {
        boolean isFavorite = false;
        for(String beaconsId : UserDataUtil.getInstance().getFavorites()){
            if(beaconsId.trim().equalsIgnoreCase(beacon.getBeaconId().trim())){
                isFavorite = true;
                break;
            }
        }
        return isFavorite;
    }

    private void removeFloatingActionButtons(){
        offerFlaotingButton.setVisibility(View.INVISIBLE);
        favoriteFloatingButton.setVisibility(View.INVISIBLE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) offerFlaotingButton.getLayoutParams();
        layoutParams.rightMargin += (int) (offerFlaotingButton.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (offerFlaotingButton.getHeight() * 0.25);
        offerFlaotingButton.setLayoutParams(layoutParams);
        offerFlaotingButton.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab_hide));
        offerFlaotingButton.setClickable(false);

        layoutParams = (LinearLayout.LayoutParams) favoriteFloatingButton.getLayoutParams();
        layoutParams.rightMargin += (int) (favoriteFloatingButton.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (favoriteFloatingButton.getHeight() * 0.25);
        favoriteFloatingButton.setLayoutParams(layoutParams);
        favoriteFloatingButton.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab_hide));
        favoriteFloatingButton.setClickable(false);

    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        removeFloatingActionButtons();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.offer:{
                Intent offersIntent = new Intent(getContext(), CouponsActivity.class);
                startActivity(offersIntent);
                break;
            }
            case R.id.favorite:{

                try {
                    if(!isFavorite(currentSpot)) {
                        new FavoriteService().saveFavorite(OffersMapFragment.this, currentSpot, UserDataUtil.getInstance().getUserEmailId());
                    }
                    FavoritesFragment.notifyAdapter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    @Override
    public void updateFavorites(Spot spot, boolean b) {
       FavoritesDataUtil.getInstance().addFavorite(spot);
        FavoritesFragment.LoadFavorites();
        FavoritesFragment.notifyAdapter();
        if(null != favoriteFloatingButton){
            favoriteFloatingButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
        }
    }
}
