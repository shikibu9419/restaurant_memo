package com.example.product.restaurantmemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this@MainActivity)

        button_post.setOnClickListener {
            val intent = Intent(this@MainActivity, PostActivity::class.java)
            startActivity(intent)
        }

        button_list.setOnClickListener {
            val intent = Intent(this@MainActivity, ListShopActivity::class.java)
            startActivity(intent)
        }
    }
}
