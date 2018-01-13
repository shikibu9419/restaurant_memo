package com.example.product.restaurantmemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Realm.init(this@MainActivity)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, PostActivity::class.java)
            startActivity(intent)
        }
    }
}
