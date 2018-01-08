package com.example.product.restaurantmemo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ShopLog(
        @PrimaryKey var id: Long = 0,
        var placeId: String = "",
        var comment: String = "",
        var numStars: Float = 0f
): RealmObject(){}