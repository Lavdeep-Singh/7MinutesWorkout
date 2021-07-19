package com.example.a7minutesworkout

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.a7minutesworkout.database.Database
import com.example.a7minutesworkout.database.EntityHistory
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_bmi.toolbar_bmi_activity
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    val layoutManager= LinearLayoutManager(this)
    lateinit var itemList:ArrayList<EntityHistory>
    lateinit var adapter:HistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_history_activity)
        if(supportActionBar!=null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title="History"
        }
        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        itemList=DbAsyncGetAll(this).execute().get() as ArrayList<EntityHistory>
        adapter=HistoryAdapter(this,itemList)
        rvHistory.adapter=adapter
        rvHistory.layoutManager=layoutManager
        if(itemList.size>0){
            rvHistory.visibility=View.VISIBLE
            tvHistory.visibility= View.VISIBLE
        }
    }
    class DbAsyncGetAll(val context: Context): AsyncTask<Void, Void, List<EntityHistory>>(){
        val db = Room.databaseBuilder(context, Database::class.java, "emp-db").build()
        override fun doInBackground(vararg params: Void?): List<EntityHistory> {
            return db.dao().getAllData()
        }

    }
}