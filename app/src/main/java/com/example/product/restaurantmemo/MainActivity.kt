package com.example.product.restaurantmemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_list_shop.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Realm.init(this@MainActivity)
        setContentView(R.layout.activity_main)

        shop_list_view.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            startActivity(intent)
        }
    }

    companion object {
        val EXTRA_ID = "com.example.product.restaurantmemo.extra_id"
    }
}
