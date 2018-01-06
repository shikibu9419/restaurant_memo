package com.example.product.restaurantmemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.RatingBar
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class ListShopAdapter(realmResults: OrderedRealmCollection<ShopLog>)
    : RealmBaseAdapter<ShopLog>(realmResults), ListAdapter {

    private class ViewHolder(itemView: View) {
        var name = itemView.findViewById<View>(R.id.shop_name) as TextView
        var visits = itemView.findViewById<View>(R.id.shop_visits) as TextView
        var latestLog = itemView.findViewById<View>(R.id.shop_latest_log) as TextView
        var stars = itemView.findViewById<View>(R.id.shop_star) as RatingBar
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view: View? = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_shop, parent, false)

            holder = ViewHolder(view)

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        // Viewにそれぞれ値を代入
        holder.name.text = getItem(position)?.placeId
        holder.visits.text = "${getItem(position)?.numVisits}回このお店に来ています"
        holder.latestLog.text = getItem(position)?.latestLog

        val starRating = getItem(position)?.aveNumStars
        if(starRating != null) {
            holder.stars.rating = starRating
        }

        return view
    }
}
