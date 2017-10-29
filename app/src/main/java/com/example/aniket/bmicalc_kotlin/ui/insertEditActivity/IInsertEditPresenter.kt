package com.example.aniket.bmicalc_kotlin.ui.insertEditActivity

import com.example.aniket.bmicalc_kotlin.data.UserBmi

/**
 * Created by aniket on 18-09-2017.
 */
interface IInsertEditPresenter {
    fun attachView(iInsertEditView: IInsertEditView)

    fun detachView()

    fun calculateBmi(height: String, weight: String): String

    fun insertBmiData(userBmi: UserBmi)

    fun fetchBmiData(id: Int)

    fun updateBmiData(userBmi: UserBmi)
}
