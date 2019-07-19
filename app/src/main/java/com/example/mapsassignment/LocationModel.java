package com.example.mapsassignment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.materialize.holder.StringHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationModel extends AbstractItem<LocationModel, LocationModel.ViewHolder> {

    String name;
    String icon;
    String lat;
    String lng;

    public LocationModel(String name, String icon, String lat, String lng) {
        this.name = name;
        this.icon = icon;
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.framelayoutparent;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.card;
    }

    protected static class ViewHolder extends FastAdapter.ViewHolder<LocationModel> {
        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.icon)
        ImageView icon;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void bindView(LocationModel item, List<Object> payloads) {
            StringHolder.applyTo(new StringHolder(item.name), name);
            if (item.icon.equals("fuel"))
                icon.setImageResource(R.drawable.ic_fuel_white);
            else
                icon.setImageResource(R.drawable.ic_restaurant_white);
        }

        @Override
        public void unbindView(LocationModel item) {
            name.setText("");
        }
    }
}
