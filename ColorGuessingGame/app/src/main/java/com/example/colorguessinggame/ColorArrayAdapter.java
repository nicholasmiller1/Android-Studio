package com.example.colorguessinggame;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import java.util.List;

public class ColorArrayAdapter extends ArrayAdapter<GuessedColor> {

    public ColorArrayAdapter(@NonNull Context context, List<GuessedColor> resource) {
        super(context, 0, resource);
    }

    public View getView(int position, View view, ViewGroup parent) {
        if ( view == null ) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.color_layout, parent, false);
        }

        GuessedColor color = getItem(position);
        TextView distanceView = view.findViewById(R.id.textViewDistance);
        distanceView.setText(String.valueOf(color.getDistance()));
        CardView scoreBackground = view.findViewById(R.id.cardViewScore);
        scoreBackground.setBackgroundColor(Color.rgb(color.getRed(), color.getGreen(), color.getBlue()));
        return view;
    }
}
