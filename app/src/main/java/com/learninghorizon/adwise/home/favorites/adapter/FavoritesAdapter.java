package com.learninghorizon.adwise.home.favorites.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.favorites.util.RestUtil;
import com.learninghorizon.adwise.home.spot.Spot;
import com.learninghorizon.adwise.home.user.UserDataUtil;

import java.util.List;
import java.util.Random;

/**
 * Created by ramnivasindani on 4/18/16.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<Spot> titles;
    private String[] colors = {"#F44336","#9C27B0", "#673AB7", "#2196F3", "#03A9F4", "#009688"};
    public FavoritesAdapter(List<Spot> titles){
        this.titles = titles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.titleTextView.setText(titles.get(position).getSpotName());
        Random colorNumber = new Random();
        int nextColor =colorNumber.nextInt(colors.length);
        String newTileColor = colors[nextColor];
        holder.cardView.setCardBackgroundColor(Color.parseColor(newTileColor));
        holder.isFavoriteImage.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                Spot spot = titles.get(position);
                try {
                    removeFavorite(spot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void removeFavorite(Spot spot) throws Exception {
        RestUtil.removeFavorites(spot,  UserDataUtil.getInstance().getUserEmailId());
    }


    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titleTextView;
        public CardView cardView;
        public ImageView isFavoriteImage;
        public ViewHolder(View titleView) {
            super(titleView);
            this.titleTextView = (TextView) titleView.findViewById(R.id.offer_title);
            this.cardView = (CardView) titleView.findViewById(R.id.card_view);
            this.isFavoriteImage = (ImageView) titleView.findViewById(R.id.favorite_image);
        }
    }
}
