package com.example.aniket.bmicalc_kotlin.ui.insertEditActivity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.example.aniket.bmicalc_kotlin.Common
import com.example.aniket.bmicalc_kotlin.R
import com.example.aniket.bmicalc_kotlin.data.UserBmi
import kotlinx.android.synthetic.main.activity_insert_edit.*


class InsertEditActivity : AppCompatActivity(), IInsertEditView {


    private var gender: String = "male"
    private var actionUpdate: Boolean = false
    private lateinit var insertEditPresenter: InsertEditPresenter
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_edit)

        bindAllViews()
        var title = "Insert new"
        val intent: Intent? = intent
        if (intent != null && intent.hasExtra(Common.LIST_TO_EDIT_ACTIVITY_INTENT_ID)) {
            id = intent.getIntExtra("id", 0)
            actionUpdate = true
            if (id != 0) {
                title = "Edit"
                insertEditPresenter.fetchBmiData(id)
            }
        }
        setTitle(title)


        insertUpdateBtn.setOnClickListener {
            addBmiData()
        }

        genderSpinner.onItemSelectedListener = object : OnItemSelectedListener {

            override fun onItemSelected(arg0: AdapterView<*>, arg1: View?, arg2: Int, arg3: Long) {
                val selectedItem = genderSpinner.selectedItem.toString()
                gender = selectedItem
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {
            }
        }


    }

    private fun bindAllViews() {
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
                R.array.gender_array,
                android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        nameEditText
        heightEditText
        weightEditText
        ageEditText
        insertUpdateBtn

        insertEditPresenter = InsertEditPresenter()
        insertEditPresenter.attachView(this)

    }

    override fun getContext(): Context = baseContext

    private fun addBmiData() {
        val nameText = nameEditText.text.toString()
        val heightText = heightEditText.text.toString()
        val weightText = weightEditText.text.toString()
        val ageText = ageEditText.text.toString()
        if (TextUtils.isEmpty(nameText) or TextUtils.isEmpty(heightText) or TextUtils.isEmpty(weightText) or TextUtils.isEmpty(ageText)) {
            AlertDialog.Builder(this)
                    .setMessage("Please fill all fileds")
                    .setPositiveButton("ok", (DialogInterface.OnClickListener { _, _ -> }))
                    .show()
            return
        }


        val userBmi = UserBmi(null,
                nameEditText.text.toString(),
                heightText,
                weightText,
                insertEditPresenter.calculateBmi(heightText, weightText),
                gender,
                ageEditText.text.toString().toInt())
        if (!actionUpdate)
            insertEditPresenter.insertBmiData(userBmi)
        else {
            userBmi.id = id
            insertEditPresenter.updateBmiData(userBmi)
        }
    }


    /**
     * This function called after data is submitted
     * Shows data submitted toast and destroys this activity
     */
    override fun dataSubmitted() {
        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_LONG).show()
        finish()
    }


    override fun attachBmiDataToUi(userBmi: UserBmi) {
        nameEditText.setText(userBmi.name)
        heightEditText.setText(userBmi.height)
        weightEditText.setText(userBmi.weight)
        ageEditText.setText(userBmi.age.toString())

        if (userBmi.gender == "Male")
            genderSpinner.setSelection(0, true)
        else if (userBmi.gender == "Female")
            genderSpinner.setSelection(1, true)

        insertUpdateBtn.text = getString(R.string.update_btn_text)
    }


    override fun dataSubmitFailed() {
        Toast.makeText(this, getString(R.string.error_toast_msg), Toast.LENGTH_LONG).show()
    }
}
