package com.example.aniket.bmicalc_kotlin.ui.listActivity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.AsyncTaskLoader
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import com.example.aniket.bmicalc_kotlin.data.UserBmi
import com.example.aniket.bmicalc_kotlin.room.MyRoom

/**
 * Created by aniket on 26-09-2017.
 */
class ListActivityPres : IListActivityPres, LoaderManager.LoaderCallbacks<LiveData<MutableList<UserBmi>>> {

    private var mIListActivityView: IListActivityView? = null
    private lateinit var mContext: Context
    private val dao = MyRoom.myDatabase.userBmiDao()


    override fun attachView(iListActivityView: IListActivityView) {
        mIListActivityView = iListActivityView
        mContext = mIListActivityView!!.getContext()
    }

    override fun detachView() {
        mIListActivityView = null
    }

    override fun getUpdateList() {
        val loaderManager = (mContext as AppCompatActivity).supportLoaderManager

        val loader = loaderManager.getLoader<LiveData<MutableList<UserBmi>>>(1)

        if (loader != null && loader.isReset) {
            loaderManager.restartLoader(1, null, this).forceLoad()
        } else {
            loaderManager.initLoader(1, null, this).forceLoad()
        }

    }

    override fun onLoaderReset(loader: Loader<LiveData<MutableList<UserBmi>>>) {
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<LiveData<MutableList<UserBmi>>> {
        return object : AsyncTaskLoader<LiveData<MutableList<UserBmi>>>(mContext) {

            override fun loadInBackground(): LiveData<MutableList<UserBmi>> {
                return dao.getAll()
            }


        }

    }

    override fun onLoadFinished(loader: Loader<LiveData<MutableList<UserBmi>>>, data: LiveData<MutableList<UserBmi>>?) {
        mIListActivityView!!.bmiDataFetchedSuccefully(data!!)
    }



    override fun deleteBmiData(userBmi: UserBmi) {
        val result: Int = dao.deleteUserBmiData(userBmi)
        if (result == 0)
            throw IllegalAccessException("Invalid data to delete")
    }
}
