package com.example.mapsassignment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.materialize.holder.StringHolder

class LocationModel(var name: String, var icon: String, var lat: String, var lng: String) : AbstractItem<LocationModel?, LocationModel.ViewHolder>() {

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    override fun getType(): Int {
        return R.id.framelayoutparent
    }

    override fun getLayoutRes(): Int {
        return R.layout.card
    }

    class ViewHolder(view: View?) : FastAdapter.ViewHolder<LocationModel>(view) {
        @JvmField
        @BindView(R.id.name)
        var name: TextView? = null

        @JvmField
        @BindView(R.id.icon)
        var icon: ImageView? = null
        override fun bindView(item: LocationModel, payloads: List<Any>) {
            StringHolder.applyTo(StringHolder(item.name), name)
            if (item.icon == "fuel") icon!!.setImageResource(R.drawable.ic_fuel_white) else icon!!.setImageResource(R.drawable.ic_restaurant_white)
        }

        override fun unbindView(item: LocationModel) {
            name!!.text = ""
        }

        init {
            ButterKnife.bind(this, view!!)
        }
    }

}