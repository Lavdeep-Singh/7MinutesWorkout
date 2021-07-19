package com.example.a7minutesworkout

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.a7minutesworkout.database.Database
import com.example.a7minutesworkout.database.EntityHistory
import kotlinx.android.synthetic.main.activity_finish.*
import java.util.*

class FinishActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
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
            val ent=EntityHistory(
                0,addDateToDatabase()
            )
            DbAsyncTask(this,ent,1).execute()
            finish()
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addDateToDatabase(): String {  //get current system time
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(dateTime).toString()
    }


    class DbAsyncTask(val context: Context, val entityHistory: EntityHistory, val mode:Int) : AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, Database::class.java, "emp-db").build()
        override fun doInBackground(vararg params: Void?):Boolean {

            when(mode){
                1->{
                    db.dao().insertData(entityHistory)
                    db.close()
                    return true
                }
                2->{
                    db.dao().deleteData(entityHistory)
                    db.close()
                    return true
                }
            }
            return false
        }
    }

}