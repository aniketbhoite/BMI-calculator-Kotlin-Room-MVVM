package com.example.aniket.bmicalc_kotlin.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by aniket on 17-09-2017.
 */
@Entity(tableName = "UserBmi")
data class UserBmi(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int?,
                   @ColumnInfo(name = "name") var name: String,
                   @ColumnInfo(name = "height") var height: String,
                   @ColumnInfo(name = "weight") var weight: String,
                   @ColumnInfo(name = "bmi") var bmi: String,
                   @ColumnInfo(name = "gender") var gender: String,
                   @ColumnInfo(name = "age") var age: Int) {
    constructor() : this(null, "", "", "", "", "", 0)
}
