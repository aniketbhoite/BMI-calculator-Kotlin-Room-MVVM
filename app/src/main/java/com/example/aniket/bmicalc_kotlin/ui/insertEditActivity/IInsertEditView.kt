package com.example.aniket.bmicalc_kotlin.ui.insertEditActivity

import android.content.Context
import com.example.aniket.bmicalc_kotlin.data.UserBmi

/**
 * Created by aniket on 17-09-2017.
 */
interface IInsertEditView {
    fun dataSubmitted()

    fun getContext(): Context

    fun attachBmiDataToUi(userBmi: UserBmi)

    fun dataSubmitFailed()
}
