package com.example.product.restaurantmemo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ShopLog(
        @PrimaryKey
        open var id: Long = 0,
        open var placeId: String = "",
        open var comment: String? = null,
        open var numStars: Float = 0f
): RealmObject(){}