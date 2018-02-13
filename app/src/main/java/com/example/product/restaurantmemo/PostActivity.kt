package com.example.product.restaurantmemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_post.*
import java.util.*
import kotlin.properties.Delegates

class PostActivity : AppCompatActivity() {

    var mRealm: Realm by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        mRealm = Realm.getDefaultInstance()

        // TODO: placeIdの生成
        val placeId = "place id"

        val shopLog = ShopLog(placeId = placeId)

        post_save_button.setOnClickListener {
            savePostData(shopLog)
            finish()
        }
    }

    override fun onDestroy() {
        mRealm.close()
        super.onDestroy()
    }

    // 投稿データの保存
    private fun savePostData(shopLog: ShopLog) {
        // idの生成(オートインクリメント)
        if(!mRealm.isEmpty) {
            shopLog.id = mRealm.where(ShopLog::class.java).max("id")!!.toLong() + 1
        }

        shopLog.logDate    = Date(System.currentTimeMillis())
        shopLog.comment    = post_comment.text.toString()
        shopLog.starRating = post_star.rating

        mRealm.executeTransaction {
            mRealm.copyToRealmOrUpdate(shopLog)
        }

//        mRealm.where(ShopLog::class.java).findAll().forEach {
//            Log.d("REALM", "${it.id}, ${it.placeId}, ${it.logDate}, ${it.comment}, ${it.starRating}")
//        }
    }
}
