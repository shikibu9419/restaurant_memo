package com.example.product.restaurantmemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this@MainActivity)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent: Intent = Intent(this@MainActivity, PostActivity::class.java)
                startActivity(intent)
            }
        })
    }
}
