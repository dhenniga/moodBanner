package com.moodbanner.dev.any.backgrounds;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moodbanner.dev.any.R;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by dhen0003 on 16/03/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private Context mContext;
    private List<PostValueBackground> postList;
    private LayoutInflater inflater;


    public MyAdapter(Context context, List<PostValueBackground> postList) {
        this.postList = postList;
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView imgBackground;


        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            imgBackground = (ImageView) itemView.findViewById(R.id.imgBackground);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_background_fragment, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostValueBackground currentPost = postList.get(position);
        holder.tvName.setText(currentPost.getName());

        String imageURL = currentPost.getImageURL();
        Picasso.with(mContext).load(imageURL).resize(200, 200).centerCrop().into(holder.imgBackground);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

}