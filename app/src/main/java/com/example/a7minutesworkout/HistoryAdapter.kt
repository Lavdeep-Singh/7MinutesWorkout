package com.example.a7minutesworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.database.EntityHistory

class HistoryAdapter(val context: Context, val itemList:ArrayList<EntityHistory>): RecyclerView.Adapter<HistoryAdapter.MainViewHolder>() {

    class MainViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvPosition=view.findViewById<TextView>(R.id.tvPosition)
        val tvItem=view.findViewById<TextView>(R.id.tvItem)
        val llHistoryItemMain=view.findViewById<LinearLayout>(R.id.ll_history_item_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_history_row,parent,false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val single=itemList[position]
        if(position%2==0){
            holder.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#ebebeb")
            )
        }else{
            holder.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#ffffff")
            )
        }
        holder.tvPosition.text= single.id.toString()
        holder.tvItem.text=single.time

    }
    override fun getItemCount(): Int {
        return itemList.size
    }

}