package com.example.product.restaurantmemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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

            finish()
        }
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }

    // 投稿データの保存
    private fun savePostData(shopLog: ShopLog) {
        // idの生成(オートインクリメント)
        if(!realm.isEmpty) {
            shopLog.id = realm.where(ShopLog::class.java).max("id")!!.toLong() + 1
        }

        shopLog.comment = post_comment.text.toString()
        shopLog.numStars = post_star.rating

        realm.executeTransaction {
            realm.copyToRealmOrUpdate(shopLog)
        }

        realm.where(ShopLog::class.java).findAll().forEach {
            Log.d("REALM", "${it.id}, ${it.placeId}, ${it.comment}, ${it.numStars}")
        }
    }
}
