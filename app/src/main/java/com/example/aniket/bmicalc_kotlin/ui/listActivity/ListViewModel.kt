package com.example.aniket.bmicalc_kotlin.ui.listActivity

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.aniket.bmicalc_kotlin.data.UserBmi
import com.example.aniket.bmicalc_kotlin.data.UserBmiDao
import com.example.aniket.bmicalc_kotlin.room.MyRoom

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: UserBmiDao by lazy {
        MyRoom.myDatabase.userBmiDao()
    }


    fun getUpdateList(): LiveData<MutableList<UserBmi>> = dao.getAll()


    fun deleteBmiData(userBmi: UserBmi) {
        val result: Int = dao.deleteUserBmiData(userBmi)
        if (result == 0)
            throw IllegalAccessException("Invalid data to delete")
    }
}
