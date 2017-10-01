package com.example.aniket.bmicalc_kotlin.ui.listActivity

import android.content.Context
import com.example.aniket.bmicalc_kotlin.data.UserBmi

/**
 * Created by aniket on 26-09-2017.
 */
interface IListActivityView {

    fun getContext():Context

    fun bmiDataFetchedSuccefully(userBmiList:MutableList<UserBmi>)
}
