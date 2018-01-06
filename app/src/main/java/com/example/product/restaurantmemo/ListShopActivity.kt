package com.example.product.restaurantmemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_list_shop.*

class ListShopActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Realm.init(this@ListShopActivity)

        setContentView(R.layout.activity_list_shop)
        setSupportActionBar(toolbar_list)
    }
}