package com.example.aniket.bmicalc_kotlin.ui.insertEditActivity

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.aniket.bmicalc_kotlin.data.UserBmi
import com.example.aniket.bmicalc_kotlin.data.UserBmiDao
import com.example.aniket.bmicalc_kotlin.room.MyRoom

class InsertEditViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: UserBmiDao by lazy {
        MyRoom.myDatabase.userBmiDao()
    }


    fun calculateBmi(height: String, weight: String): String {
        val h: Float = height.toFloat()
        val w: Float = weight.toFloat()

        return (w / (h * h)).toString()
    }

    fun insertBmiData(userBmi: UserBmi) =
            dao.insertUserBmiData(userBmi)

    fun updateBmiData(userBmi: UserBmi) =
            dao.updateUserBmiData(userBmi)

    fun fetchBmiData(id: Int) = dao.getBmiUserData(id)
}
