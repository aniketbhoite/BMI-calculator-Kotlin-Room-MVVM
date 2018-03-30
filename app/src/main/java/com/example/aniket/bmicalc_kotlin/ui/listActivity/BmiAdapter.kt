package com.example.aniket.bmicalc_kotlin.ui.listActivity

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.aniket.bmicalc_kotlin.R
import com.example.aniket.bmicalc_kotlin.data.UserBmi
import com.example.aniket.bmicalc_kotlin.ui.listActivity.BmiAdapter.ViewHolder
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * Created by aniket on 21-09-2017.
 */
class BmiAdapter(private var mContext: Context, private val mClickHandler: BmiAdapterOnClickHandler) : RecyclerView.Adapter<ViewHolder>() {

    private var mUserBmiList: MutableList<UserBmi>? = null

    interface BmiAdapterOnClickHandler {
        fun onClick(id: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            nameTextView.text = mUserBmiList!![position].name
            heightTextView.text = String.format(mContext.getString(R.string.list_age_text), mUserBmiList!![position].height)
            bmiTextView.text = String.format("%.1f", mUserBmiList!![position].bmi.toFloat())
            weightTextView.text = String.format(mContext.getString(R.string.list_gender_text), mUserBmiList!![position].weight)
            itemView.tag = position
            cardView.setOnClickListener {
                mClickHandler.onClick(mUserBmiList!![position].id!!)
            }
        }
        val bmiCircle = (holder.bmiTextView.background) as GradientDrawable
        val bmiColor = getBmiColor(mUserBmiList!![position].bmi.toFloat())
        bmiCircle.setColor(ContextCompat.getColor(mContext, bmiColor))
    }

    private fun getBmiColor(bmi: Float): Int {

        return when (bmi) {

            in 1f..15f -> R.color.severe_thinness

            in 15.1f..16f -> R.color.moderate_thinness

            in 17f..18.5f -> R.color.mild_thinness

            in 18.5f..25f -> R.color.normal

            in 25.1f..30f -> R.color.overweight

            in 30f..35f -> R.color.Obese_class_1

            in 35.1f..40f -> R.color.Obese_class_2

            in 40.1f..40f -> R.color.Obese_class_3

            else -> R.color.colorAccent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        return ViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        if (mUserBmiList != null) {
            return mUserBmiList!!.size
        }
        return 0
    }


    fun swapList(userBmiList: MutableList<UserBmi>) {
        mUserBmiList = userBmiList
        notifyDataSetChanged()
    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){



        var nameTextView: TextView = itemView.nameTextView
        var heightTextView: TextView = itemView.heightTextView
        var bmiTextView: TextView = itemView.bmiTextView
        var weightTextView: TextView = itemView.weightTextView
        var cardView: CardView = itemView.cardView
         



    }
}
