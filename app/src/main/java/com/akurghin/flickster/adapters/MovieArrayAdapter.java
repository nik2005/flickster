package com.akurghin.flickster.adapters;


import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akurghin.flickster.R;
import com.akurghin.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static final int ROUNDED_CORNER_RADIUS = 20;
    private static final int ROUNDED_CORNER_MARGIN = 20;

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.ivMovieImage = convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvMovieTitle = convertView.findViewById(R.id.tvMovieTitle);
            viewHolder.tvMovieOverview = convertView.findViewById(R.id.tvMovieOverview);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext())
                    .load(movie.getPosterPath())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.image_placeholder)
                    .transform(new RoundedCornersTransformation(ROUNDED_CORNER_RADIUS, ROUNDED_CORNER_MARGIN))
                    .into(viewHolder.ivMovieImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext())
                    .load(movie.getBackdropPath())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.image_placeholder)
                    .transform(new RoundedCornersTransformation(ROUNDED_CORNER_RADIUS, ROUNDED_CORNER_MARGIN))
                    .into(viewHolder.ivMovieImage);
        }

        viewHolder.tvMovieTitle.setText(movie.getOriginalTitle());
        viewHolder.tvMovieOverview.setText(movie.getOverview());

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivMovieImage;
        TextView tvMovieTitle;
        TextView tvMovieOverview;
    }
}
