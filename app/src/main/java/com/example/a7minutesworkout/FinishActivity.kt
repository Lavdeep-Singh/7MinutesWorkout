package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_finish.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setSupportActionBar(toolbar_finish_activity)//enabling toolbar to work as action bar
        if(supportActionBar!=null ){
            supportActionBar?.setDisplayHomeAsUpEnabled(true) //set back button
        }
        //setting click listener on toolbar back button
        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        btnFinish.setOnClickListener {
                 finish()
        }

    }
}