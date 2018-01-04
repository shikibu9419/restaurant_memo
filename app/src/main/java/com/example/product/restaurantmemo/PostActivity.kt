package com.example.product.restaurantmemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_post.*
import kotlin.properties.Delegates

class PostActivity : AppCompatActivity() {

    var realm: Realm by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        realm = Realm.getDefaultInstance()

        // TODO: placeIdの初期化
        val placeId = "place id"

        val shopLog = ShopLog(placeId = placeId)

        post_save_button.setOnClickListener {
            savePostData(shopLog)
        }
    }

    // 投稿データの保存
    private fun savePostData(shopLog: ShopLog) {
        if(!realm.isEmpty) {
            shopLog.id = getShopLogId()
        }

        shopLog.comment = post_comment.toString()
        shopLog.numStars = post_star.numStars

        realm.executeTransaction {
            realm.copyToRealmOrUpdate(shopLog)
        }
    }

    // idの生成(オートインクリメント)
    private fun getShopLogId(): Long {
        val maxId = realm.where(ShopLog::class.java).max("id")!!.toLong()
        return maxId + 1
    }
}
