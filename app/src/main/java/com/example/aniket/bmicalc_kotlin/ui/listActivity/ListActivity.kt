package com.example.aniket.bmicalc_kotlin.ui.listActivity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.AbsListView
import com.example.aniket.bmicalc_kotlin.Common
import com.example.aniket.bmicalc_kotlin.R
import com.example.aniket.bmicalc_kotlin.data.UserBmi
import com.example.aniket.bmicalc_kotlin.ui.insertEditActivity.InsertEditActivity
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity(),
        BmiAdapter.BmiAdapterOnClickHandler {


    lateinit var mUserBmiList: MutableList<UserBmi>

    lateinit var bmiAdapter: BmiAdapter

    lateinit var tempUserBmi: UserBmi

    private lateinit var listViewModel: ListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        bindAllView()

        mFab.setFabBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))

        mFab.setOnClickListener { startActivity(Intent(this, InsertEditActivity::class.java)) }


        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)


        val snackbarCallback = object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                listViewModel.deleteBmiData(tempUserBmi)
                bmiAdapter.notifyDataSetChanged()
                checkDataList()
            }

            override fun onShown(sb: Snackbar?) {}
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?,
                                viewHolder: RecyclerView.ViewHolder?,
                                target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val position = viewHolder!!.itemView.tag
                tempUserBmi = mUserBmiList[position as Int]
                mUserBmiList.removeAt(position)
                bmiAdapter.notifyDataSetChanged()
                checkDataList()
                val snackbar = Snackbar.make(coordinatorLayout, "Deleted 1", Snackbar.LENGTH_LONG)
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.addCallback(snackbarCallback)
                snackbar.setAction("Undo") {
                    mUserBmiList.add(position, tempUserBmi)
                    bmiAdapter.notifyDataSetChanged()
                    snackbar.removeCallback(snackbarCallback)
                    checkDataList()
                }
                snackbar.show()
            }
        }).attachToRecyclerView(recyclerView)


/*        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if ((dy > 0) and fab.isShown)
                    fab.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    fab.show()
            }
        })*/
//        listActivityPres.getUpdateList()
        listViewModel.getUpdateList().observe(this, Observer {
            if (it != null) {
                progressBar.visibility = View.GONE
                mUserBmiList = it
                checkDataList()
                bmiAdapter.swapList(it)
            }
        })
    }

    private fun bindAllView() {

        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)
        recyclerView.layoutManager = layoutManager

        bmiAdapter = BmiAdapter(this, this)

        recyclerView.adapter = bmiAdapter


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && mUserBmiList.size != 0)
                    mFab.fabTextVisibility = View.GONE
                else
                    mFab.fabTextVisibility = View.VISIBLE
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING && mUserBmiList.size != 0) {
                    mFab.fabTextVisibility = View.GONE
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mFab.fabTextVisibility = View.VISIBLE
                }
            }
        })


    }


    override fun onClick(id: Int) {
        val intent = Intent(this, InsertEditActivity::class.java)
        intent.putExtra(Common.LIST_TO_EDIT_ACTIVITY_INTENT_ID, id)
        startActivity(intent)
    }

    fun checkDataList() {
        if (mUserBmiList.size == 0)
            emptyViewText.visibility = View.VISIBLE
        else
            emptyViewText.visibility = View.GONE
    }

}
