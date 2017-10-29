package com.example.aniket.bmicalc_kotlin.room

import android.app.Application

/**
 * Created by aniket on 17-09-2017.
 */
class MyRoom : Application() {
    companion object {
        lateinit var myDatabase: com.example.aniket.bmicalc_kotlin.data.MyDatabase
    }

    override fun onCreate() {
        super.onCreate()
        myDatabase = android.arch.persistence.room.Room.databaseBuilder(this,
                com.example.aniket.bmicalc_kotlin.data.MyDatabase::class.java,
                "Bmi-MyDatabase").allowMainThreadQueries()
                .build()
    }
}
