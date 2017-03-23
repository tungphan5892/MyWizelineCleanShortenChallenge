package com.example.tungphan.wizelinecleanshortenchallenge.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Datum;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.ImageDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstant.IMAGE_DESCRIPTION;
import static com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstant.IMAGE_ID;
import static com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstant.IMAGE_URL;

/**
 * Created by tungphan on 3/23/17.
 */

public class ImagesFromServiceAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private Context context;
    private List<Datum> data = new ArrayList<>();
    private final Object scrollTag = new Object();
    private int galleryImageSize;

    public AbsListView.OnScrollListener getOnScrollListener() {
        return this;
    }

    public ImagesFromServiceAdapter(Context context, List<Datum> data) {
        this.context = context;
        this.data = data;
        galleryImageSize = (int) context.getResources().getDimension(R.dimen.image_grid_view_size);

    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImagesFromServiceAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_image_grid_view, parent, false);
            viewHolder = new ImagesFromServiceAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ImagesFromServiceAdapter.ViewHolder) convertView.getTag();
        }
        String itemId = data.get(position).getId();
        String itemDescription = data.get(position).getDescription();
        String itemLink = data.get(position).getLink();
        Picasso.with(context)
                .load(itemLink)
                .placeholder(R.drawable.default_placeholder)
                .resize(galleryImageSize, galleryImageSize)
                .centerCrop()
                .into(viewHolder.image);
        viewHolder.image.setOnClickListener(v -> {
            Intent intent = new Intent(context, ImageDetailActivity.class);
            intent.putExtra(IMAGE_ID, itemId);
            intent.putExtra(IMAGE_DESCRIPTION, itemDescription);
            intent.putExtra(IMAGE_URL, itemLink);
            context.startActivity(intent);
        });
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Picasso picasso = Picasso.with(context);
        if (scrollState == SCROLL_STATE_IDLE) {
            picasso.resumeTag(scrollTag);
        } else {
            picasso.pauseTag(scrollTag);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public static class ViewHolder {
        ImageView image;

        public ViewHolder(View itemView) {
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
