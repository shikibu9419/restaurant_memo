package com.example.product.restaurantmemo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class ShopLog(
    @PrimaryKey var id: Long = 0,
    var placeId: String = "",
    var comment: String = "",
    var starRating: Float = 0f,
    var logDate: Date = Date()
): RealmObject(){}