package com.example.aniket.bmicalc_kotlin.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by aniket on 17-09-2017.
 */
@Database(entities = arrayOf(UserBmi :: class),version = 1,exportSchema = false)
abstract class MyDatabase : RoomDatabase()
{
    abstract fun userBmiDao(): UserBmiDao

}
