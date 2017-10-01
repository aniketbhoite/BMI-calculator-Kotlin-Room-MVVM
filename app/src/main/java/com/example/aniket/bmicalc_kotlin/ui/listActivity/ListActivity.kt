package com.example.aniket.bmicalc_kotlin.ui.listActivity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.aniket.bmicalc_kotlin.Common
import com.example.aniket.bmicalc_kotlin.R
import com.example.aniket.bmicalc_kotlin.data.UserBmi
import com.example.aniket.bmicalc_kotlin.ui.insertEditActivity.InsertEditActivity

class ListActivity : AppCompatActivity(),
        BmiAdapter.BmiAdapterOnClickHandler,
        IListActivityView {

    private lateinit var recyclerView:RecyclerView

    lateinit var mUserBmiList:MutableList<UserBmi>

    lateinit var bmiAdapter:BmiAdapter

    lateinit var tempUserBmi:UserBmi

    private lateinit var emptyViewText: TextView

    private lateinit var listActivityPres:ListActivityPres
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        bindAllView()

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        fab.setOnClickListener { _ -> startActivity(Intent(this,InsertEditActivity::class.java)) }

        listActivityPres = ListActivityPres()
        listActivityPres.attachView(this)

        val snackbarCallback = object: Snackbar.Callback(){
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                listActivityPres.deleteBmiData(tempUserBmi)
                bmiAdapter.notifyDataSetChanged()
                checkDataList()
            }

            override fun onShown(sb: Snackbar?) {}
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
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
                val snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout),"Deleted 1",Snackbar.LENGTH_LONG)
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.addCallback(snackbarCallback)
                snackbar.setAction("Undo") {
                    mUserBmiList.add(position,tempUserBmi)
                    bmiAdapter.notifyDataSetChanged()
                    snackbar.removeCallback(snackbarCallback)
                    checkDataList()
                }
                snackbar.show()
            }
        }).attachToRecyclerView(recyclerView)


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if ((dy > 0) and fab.isShown)
                    fab.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    fab.show()
            }
        })

    }

    private fun bindAllView() {

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        val layoutManager =  LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)
        recyclerView.layoutManager = layoutManager

        bmiAdapter = BmiAdapter(this,this)

        recyclerView.adapter = bmiAdapter

        emptyViewText = findViewById(R.id.emptyDataText)
    }

    override fun bmiDataFetchedSuccefully(userBmiList: MutableList<UserBmi>) {

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.GONE
        mUserBmiList = userBmiList
        checkDataList()
        bmiAdapter.swapList(userBmiList)

    }



    override fun onResume() {
        super.onResume()
        listActivityPres.getUpdateList()
    }

    override fun onDestroy() {
        super.onDestroy()
        listActivityPres.detachView()
    }

    override fun onClick(id: Int) {
        val intent = Intent(this,InsertEditActivity::class.java)
        intent.putExtra(Common.LIST_TO_EDIT_ACTIVITY_INTENT_ID,id)
        startActivity(intent)
    }

    fun checkDataList(){
        if (mUserBmiList.size == 0)
            emptyViewText.visibility = View.VISIBLE
        else
            emptyViewText.visibility = View.GONE
    }

    override fun getContext() = this
}
