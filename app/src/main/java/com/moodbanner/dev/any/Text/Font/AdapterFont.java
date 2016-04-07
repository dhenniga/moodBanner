package com.moodbanner.dev.any.Text.Font;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moodbanner.dev.any.R;
import com.moodbanner.dev.any.Text.ValueFont;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dhen0003 on 16/03/16.
 */
public class AdapterFont extends RecyclerView.Adapter<AdapterFont.ViewHolder> {


    private Context mContext;
    private List<ValueFont> postList;
    private LayoutInflater inflater;


    public AdapterFont(Context context, List<ValueFont> postList) {
        this.postList = postList;
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFontName;
        public ImageView ivFontThumb;


        public ViewHolder(View itemView) {
            super(itemView);
            tvFontName = (TextView) itemView.findViewById(R.id.tvFontName);
            ivFontThumb = (ImageView) itemView.findViewById(R.id.ivFontThumb);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public AdapterFont.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_font_selection, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ValueFont currentPost = postList.get(position);
        holder.tvFontName.setText(currentPost.getName());

        String fontThumb = currentPost.getFontThumb();
        Picasso.with(mContext).load(fontThumb).resize(420, 200).centerCrop().into(holder.ivFontThumb);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

}