package com.app.listviewdemowithrest.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.listviewdemowithrest.R;
import com.app.listviewdemowithrest.models.Row;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Row> {

    private List<Row> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        ImageView imgToLoad;
        ImageView imgArrow;
        RelativeLayout relMain;
    }

    public CustomAdapter(List<Row> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Row dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
            viewHolder.imgToLoad = (ImageView) convertView.findViewById(R.id.imgToLoad);
            viewHolder.imgArrow = (ImageView) convertView.findViewById(R.id.imgArrow);
            viewHolder.relMain = (RelativeLayout) convertView.findViewById(R.id.relMain);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (dataModel.getTitle() != null && !dataModel.getTitle().trim().isEmpty()) {
            viewHolder.txtTitle.setVisibility(View.VISIBLE);
            viewHolder.txtTitle.setText(dataModel.getTitle());
        } else {
            viewHolder.txtTitle.setVisibility(View.GONE);
            viewHolder.txtTitle.setText("");
        }

        if (dataModel.getDescription() != null && !dataModel.getDescription().trim().isEmpty()) {
            viewHolder.txtDescription.setVisibility(View.VISIBLE);
            viewHolder.txtDescription.setText(dataModel.getDescription());
        } else {
            viewHolder.txtDescription.setVisibility(View.GONE);
            viewHolder.txtDescription.setText("");
        }

        if (dataModel.getImageHref() != null && !dataModel.getImageHref().trim().isEmpty()) {
            viewHolder.imgToLoad.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(dataModel.getImageHref())
                    .apply(new RequestOptions().override((int) mContext.getResources().getDimension(R.dimen._100dp), Target.SIZE_ORIGINAL))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            viewHolder.imgToLoad.setVisibility(View.GONE);
                            viewHolder.imgToLoad.setImageDrawable(null);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .dontAnimate()
                    .into(viewHolder.imgToLoad);
        } else {
            viewHolder.imgToLoad.setVisibility(View.GONE);
            viewHolder.imgToLoad.setImageDrawable(null);
        }

        if (viewHolder.txtTitle.getVisibility() == View.GONE
                && viewHolder.txtDescription.getVisibility() == View.GONE
                && viewHolder.imgToLoad.getVisibility() == View.GONE) {
            remove(dataModel);
            notifyDataSetChanged();
        }

        // Return the completed view to render on screen
        return convertView;
    }
}