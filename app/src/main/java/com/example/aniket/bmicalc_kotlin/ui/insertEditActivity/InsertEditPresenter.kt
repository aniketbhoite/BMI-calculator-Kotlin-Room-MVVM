package com.example.aniket.bmicalc_kotlin.ui.insertEditActivity

import android.content.Context
import com.example.aniket.bmicalc_kotlin.data.UserBmi
import com.example.aniket.bmicalc_kotlin.room.MyRoom

/**
 * Created by aniket on 18-09-2017.
 */
class InsertEditPresenter : IInsertEditPresenter {

    private val dao = MyRoom.myDatabase.userBmiDao()

    private var mIInsertEditView: IInsertEditView? = null
    private lateinit var mContext: Context

    override fun attachView(iInsertEditView: IInsertEditView) {
        mIInsertEditView = iInsertEditView
        mContext = iInsertEditView.getContext()
    }

    override fun detachView() {
        mIInsertEditView = null
    }

    override fun calculateBmi(height: String, weight: String): String {
        val h: Float = height.toFloat()
        val w: Float = weight.toFloat()

        return (w / (h * h)).toString()
    }

    override fun insertBmiData(userBmi: UserBmi) {
        val result: Long = dao.insertUserBmiData(userBmi)
        if (result > 0)
            mIInsertEditView!!.dataSubmitted()
        else
            mIInsertEditView!!.dataSubmitFailed()
    }

    override fun fetchBmiData(id: Int) {
        val userBmi: UserBmi = dao.getBmiUserData(id)
        mIInsertEditView!!.attachBmiDataToUi(userBmi)
    }

    override fun updateBmiData(userBmi: UserBmi) {
        val result: Int = dao.updateUserBmiData(userBmi)
        if (result > 0)
            mIInsertEditView!!.dataSubmitted()
        else
            mIInsertEditView!!.dataSubmitFailed()
    }
}
