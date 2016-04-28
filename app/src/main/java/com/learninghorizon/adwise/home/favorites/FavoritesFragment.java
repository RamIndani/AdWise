package com.learninghorizon.adwise.home.favorites;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.favorites.adapter.FavoritesAdapter;
import com.learninghorizon.adwise.home.favorites.util.FavoritesPresenter;
import com.learninghorizon.adwise.home.spot.Spot;
import com.learninghorizon.adwise.home.spot.util.SpotDataUtil;
import com.learninghorizon.adwise.home.user.UserDataUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment implements FavoritesPresenter {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
   
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    public static FavoritesAdapter favoritesAdapter;
    private static List<Spot> allFavorites;
    Handler offersHandler;
    Handler timerHandler;
    private Runnable timerRunnable;
    private Context activity;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this,view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allFavorites = new ArrayList<Spot>();
        favoritesAdapter = new FavoritesAdapter(allFavorites);
        recyclerView.setAdapter(favoritesAdapter);
        LoadFavorites();
        notifyAdapter();
        return view;
    }

    public static void LoadFavorites() {
        allFavorites.clear();
        if(null != UserDataUtil.getInstance().getFavorites()) {
           if(!UserDataUtil.getInstance().getFavorites().isEmpty()) {
               for (String favoriteSpotId : UserDataUtil.getInstance().getFavorites()) {
                   for (Spot spot : SpotDataUtil.getInstance().getSpots()) {
                       if (spot.getBeaconId().trim().equalsIgnoreCase(favoriteSpotId.trim()) &&
                               !allFavorites.contains(spot)) {
                           allFavorites.add(spot);
                       }
                   }
               }
           }
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        activity = context;
    }


    @Override
    public void onStop(){
        super.onStop();
        if(null != timerHandler && null !=timerRunnable){
            timerHandler.removeCallbacks(timerRunnable);
        }
    }
    

    @Override
    public void updateFavorites(Spot spot, boolean b) {
        if(null != spot) {
            allFavorites.add(spot);
            notifyAdapter();
        }
    }

    public static void notifyAdapter(){
        favoritesAdapter.notifyDataSetChanged();
    }
    
}
