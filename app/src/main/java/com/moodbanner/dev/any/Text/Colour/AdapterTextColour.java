package com.moodbanner.dev.any.Text.Colour;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moodbanner.dev.any.R;

import java.util.List;

/**
 * Created by dhen0003 on 28/03/16.
 */
public class AdapterTextColour extends RecyclerView.Adapter<AdapterTextColour.ViewHolder> {


    private Context mContext;
    private List<ValueFontColour> colourList;
    private LayoutInflater inflater;


    public AdapterTextColour(Context context, List<ValueFontColour> colourList) {
        this.colourList = colourList;
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvColourName;
        public ImageView ivColour;


        public ViewHolder(View itemView) {
            super(itemView);
            tvColourName = (TextView) itemView.findViewById(R.id.tvColourName);
            ivColour = (ImageView) itemView.findViewById(R.id.ivColour);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public AdapterTextColour.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_font_colours, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ValueFontColour currentPost = colourList.get(position);

        holder.tvColourName.setText(currentPost.getColourName());

        holder.ivColour.setBackgroundColor(Color.parseColor(currentPost.getColourHexCode()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return colourList == null ? 0 : colourList.size();
    }

}