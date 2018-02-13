package com.example.product.restaurantmemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.RatingBar
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import java.text.SimpleDateFormat
import java.util.*

class ShopLogAdapter(realmResults: OrderedRealmCollection<ShopLog>)
    : RealmBaseAdapter<ShopLog>(realmResults), ListAdapter {

    private class ViewHolder(itemView: View) {
        var date    = itemView.findViewById<View>(R.id.log_date) as TextView
        var comment = itemView.findViewById<View>(R.id.log_comment) as TextView
        var stars   = itemView.findViewById<View>(R.id.log_star) as RatingBar
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var mView: View? = convertView
        val mHolder: ViewHolder

        if (mView == null) {
            mView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_shop_log, parent, false)

            mHolder = ViewHolder(mView)

            mView.tag = mHolder
        } else {
            mHolder = mView.tag as ViewHolder
        }

        val df = SimpleDateFormat("yyyy/MM/dd")
        df.timeZone = (TimeZone.getTimeZone("Asia/Tokyo"))

        val resultItem = getItem(position)

        // Viewにそれぞれ値を代入
        if (resultItem != null) {
            mHolder.date.text    = df.format(resultItem.logDate)
            mHolder.comment.text = resultItem.comment
            mHolder.stars.rating = resultItem.starRating
        }

        return mView
    }

    override fun isEnabled(position: Int): Boolean {
        return false
    }
}
