package com.example.product.restaurantmemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.RatingBar
import android.widget.TextView
import io.realm.*
import java.text.SimpleDateFormat
import java.util.*

class ListShopAdapter(realmResults: OrderedRealmCollection<ShopLog>)
    : RealmBaseAdapter<ShopLog>(realmResults), ListAdapter {

    private var aveStarRating = 0f
    private var numVisits = 0
    private var latestLog = ""

    private class ViewHolder(itemView: View) {
        var name      = itemView.findViewById<View>(R.id.shop_name) as TextView
        var visits    = itemView.findViewById<View>(R.id.shop_visits) as TextView
        var latestLog = itemView.findViewById<View>(R.id.shop_latest_log) as TextView
        var stars     = itemView.findViewById<View>(R.id.shop_star) as RatingBar
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var mView: View? = convertView
        val mHolder: ViewHolder

        if (mView == null) {
            mView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_shop, parent, false)

            mHolder = ViewHolder(mView)

            mView.tag = mHolder
        } else {
            mHolder = mView.tag as ViewHolder
        }

        initItem(getItem(position))

        // Viewにそれぞれ値を代入
        mHolder.name.text      = getItem(position)?.placeId
        mHolder.visits.text    = "${numVisits}回このお店に来ています"
        mHolder.latestLog.text = latestLog
        mHolder.stars.rating   = aveStarRating

        return mView
    }

    // 星の平均, 訪れた回数, 最新ログの生成
    private fun initItem(resultItem: ShopLog?) {
        Realm.getDefaultInstance().use { realm ->
            val resultLog = realm.where(ShopLog::class.java)
                    .equalTo("placeId", resultItem?.placeId)
                    .findAllSorted("id", Sort.DESCENDING)

            aveStarRating = resultLog.average("starRating").toFloat()
            numVisits     = resultLog.size
            latestLog     = extractLatestLog(resultLog)
        }
    }

    // 最新ログの抽出
    private fun extractLatestLog(logs: RealmResults<ShopLog>): String {
        val df = SimpleDateFormat("yyyy/MM/dd")
        df.timeZone = (TimeZone.getTimeZone("Asia/Tokyo"))

        var logComment = df.format(logs[0].logDate) +
                if (logs[0].comment.isNotEmpty()) " - ${logs[0].comment}\n" else "\n"
        if (logs.size > 1) {
            logComment += df.format(logs[0].logDate) +
                if (logs[1].comment.isNotEmpty()) " - ${logs[1].comment}" else ""
        }

        return logComment
    }
}
