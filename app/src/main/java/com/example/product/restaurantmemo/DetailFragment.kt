package com.example.product.restaurantmemo

import android.os.Bundle
import android.support.annotation.CheckResult
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private var mView: View? = null
    lateinit var mRealm: Realm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRealm = Realm.getDefaultInstance()
        mView = view
    }

    override fun onResume() {
        super.onResume()

        val id = if (arguments != null) { arguments.getLong(MainActivity.EXTRA_ID) } else { null }
        val placeId = mRealm.where(ShopLog::class.java).equalTo("id", id).findAll()[0].placeId
        val shopLogs = mRealm.where(ShopLog::class.java)
                .equalTo("placeId", placeId)
                .findAllSorted("id", Sort.DESCENDING)

        initLog(shopLogs)
    }

    override fun onDestroy() {
        mRealm.close()
        super.onDestroy()
    }

    @CheckResult
    fun createInstance(id: Long): DetailFragment {
        val mFragment = DetailFragment()
        val args = Bundle()

        args.putLong(MainActivity.EXTRA_ID, id)
        mFragment.arguments = args

        return mFragment
    }

    private fun initLog(shopLogs: OrderedRealmCollection<ShopLog>) {
        // TODO: 営業情報etc.の追加

        shop_star_text.text = "あなたの評価 ：" +
                String.format("%1$.1f", shopLogs.average("starRating"))
        shop_star_average.rating = shopLogs.average("starRating").toFloat()

        val logList = view?.findViewById<View>(R.id.log_list_view) as ListView
        logList.adapter = ShopLogAdapter(shopLogs)
    }
}
