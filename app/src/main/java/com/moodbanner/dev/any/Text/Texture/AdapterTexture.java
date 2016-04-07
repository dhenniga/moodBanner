package com.moodbanner.dev.any.Text.Texture;

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
public class AdapterTexture extends RecyclerView.Adapter<AdapterTexture.ViewHolder> {


    private Context mContext;
    private List<ValueTexture> postList;
    private LayoutInflater inflater;


    public AdapterTexture(Context context, List<ValueTexture> postList) {
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
    public AdapterTexture.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_textures, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ValueTexture currentPost = postList.get(position);
        holder.tvName.setText(currentPost.getName());

        String fontThumb = currentPost.getTextureFile();
        Picasso.with(mContext).load(fontThumb).resize(245, 200).centerCrop().into(holder.imgBackground);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

}