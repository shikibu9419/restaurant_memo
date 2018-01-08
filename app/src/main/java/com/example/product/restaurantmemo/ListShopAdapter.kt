package com.example.product.restaurantmemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.RatingBar
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmBaseAdapter
import io.realm.Sort

class ListShopAdapter(realmResults: OrderedRealmCollection<ShopLog>)
    : RealmBaseAdapter<ShopLog>(realmResults), ListAdapter {

    private var aveNumStars = 0f
    private var numVisits = 0
    private var latestLog = ""

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

        initItem(getItem(position))

        // Viewにそれぞれ値を代入
        holder.name.text = "${getItem(position)?.placeId} (Name)"
        holder.visits.text = "${numVisits}回このお店に来ています"
        holder.latestLog.text = latestLog
        holder.stars.rating = aveNumStars

        return view
    }

    // 星の平均, 訪れた回数, 最新ログの生成
    private fun initItem(resultItem: ShopLog?) {
        Realm.getDefaultInstance().use { realm ->
            val resultLog = realm.where(ShopLog::class.java)
                    .equalTo("placeId", resultItem?.placeId)
                    .findAllSorted("id", Sort.DESCENDING)

            aveNumStars = resultLog.average("numStars").toFloat()
            numVisits = resultLog.size
            latestLog = extractLatestLog(resultLog[0], resultLog[1])
        }
    }

    // 最新ログの抽出
    private fun extractLatestLog(log1: ShopLog, log2: ShopLog): String {
        return "${log1.placeId} (Date)" + if (log1.comment.isNotEmpty()) " - ${log1.comment}\n" else "\n" +
                "${log2.placeId} (Date)" + if (log2.comment.isNotEmpty()) " - ${log2.comment}" else ""
    }
}
