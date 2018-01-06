package com.example.product.restaurantmemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.Sort
import kotlin.properties.Delegates

class ListShopFragment : Fragment() {

    private var realm: Realm by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_shop, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        realm = Realm.getDefaultInstance()

        val list = view?.findViewById<View>(R.id.shop_list_view) as ListView
        list.adapter = ListShopAdapter(extractShopList())

//        list.setOnItemClickListener {
//            // 店の詳細情報へ
//        }
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }

    // TODO: placeId -> 店名や日付 にする
    private fun extractShopList(): OrderedRealmCollection<ShopLog> {
        val uniqueData = realm.where(ShopLog::class.java).distinct("placeId")

        uniqueData.forEach {
            val logResult = realm.where(ShopLog::class.java)
                    .equalTo("placeId", it.placeId)
                    .findAllSorted("id", Sort.DESCENDING)

            it.aveNumStars = logResult.average("numStars").toFloat()
            it.numVisits = logResult.size
            it.latestLog = "${logResult[0].placeId} ー ${logResult[0].comment}\n${logResult[1].placeId} ー ${logResult[1].comment}"
        }

        return uniqueData
    }
}