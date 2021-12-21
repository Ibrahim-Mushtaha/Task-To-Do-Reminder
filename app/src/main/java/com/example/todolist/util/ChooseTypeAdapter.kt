package com.example.todolist.util

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.ix.ibrahim7.todolist.R
import com.ix.ibrahim7.todolist.databinding.ChooseTypeBinding
import com.example.todolist.model.Type


class ChooseTypeAdapter(
    var activity: Activity, var data: MutableList<Type>, val itemclick: onClick
) :
    RecyclerView.Adapter<ChooseTypeAdapter.MyViewHolder>() {


    var last_position =0

class MyViewHolder(val item: ChooseTypeBinding) : RecyclerView.ViewHolder(item.root) {


        fun bind(n: Type) {
        item.chooseType = n
        }

    val radio = item.radio

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout:ChooseTypeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.choose_type,parent,false)
        return MyViewHolder(itemView_layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

     /*fun setNotes(lsit: List<Type>){
            this.data=lsit as MutableList<Type>
    }*/





    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    val currentItem = data[position]
        holder.bind(currentItem)
        holder.item.executePendingBindings()

        if (position != last_position) {
            holder.radio.isChecked = false
        }

        holder.radio.setOnClickListener {
            last_position = holder.adapterPosition
            itemclick.onClickItem(holder.adapterPosition,1)
        }

    }

    interface onClick {
        fun onClickItem(position: Int,type:Int)
    }

}
