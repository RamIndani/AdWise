package com.learninghorizon.adwise.home.offers.Adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learninghorizon.adwise.R;

import java.util.Random;

/**
 * Created by ramnivasindani on 4/18/16.
 */
public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private String[] titles;
    private String[] colors = {"#F44336","#9C27B0", "#673AB7", "#2196F3", "#03A9F4", "#009688"};
    public OffersAdapter(String[] titles){
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(titles[position]);
        Random colorNumber = new Random();
        int nextColor =colorNumber.nextInt(colors.length);
        String newTileColor = colors[nextColor];
        holder.cardView.setCardBackgroundColor(Color.parseColor(newTileColor));
    }


    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titleTextView;
        public CardView cardView;
        public ViewHolder(View titleView) {
            super(titleView);
            this.titleTextView = (TextView) titleView.findViewById(R.id.offer_title);
            this.cardView = (CardView) titleView.findViewById(R.id.card_view);
        }
    }
}
