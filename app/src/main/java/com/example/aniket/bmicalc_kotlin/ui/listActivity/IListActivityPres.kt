package com.example.aniket.bmicalc_kotlin.ui.listActivity

import com.example.aniket.bmicalc_kotlin.data.UserBmi

/**
 * Created by aniket on 26-09-2017.
 */
interface IListActivityPres {
    fun attachView(iListActivityView: IListActivityView)

    fun detachView()

    fun getUpdateList()

    fun deleteBmiData(userBmi: UserBmi)
}
