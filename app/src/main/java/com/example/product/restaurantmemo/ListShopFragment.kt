package com.example.product.restaurantmemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import io.realm.Realm

class ListShopFragment : Fragment() {

    lateinit var mRealm: Realm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_shop, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRealm = Realm.getDefaultInstance()
    }

    override fun onResume() {
        super.onResume()

        val list = view?.findViewById<View>(R.id.shop_list_view) as ListView
        val uniqueData = mRealm.where(ShopLog::class.java).distinct("placeId")

        list.adapter = ListShopAdapter(uniqueData)
    }

    override fun onDestroy() {
        mRealm.close()
        super.onDestroy()
    }
}