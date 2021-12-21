package com.example.todolist.util

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.ix.ibrahim7.todolist.R
import com.ix.ibrahim7.todolist.databinding.TaskTypeBinding
import com.example.todolist.model.Type


class taskTypeAdapter(
    var activity: Activity, var data: MutableList<Type>, val itemclick: onClick
) :
    RecyclerView.Adapter<taskTypeAdapter.MyViewHolder>() {



class MyViewHolder(val item: TaskTypeBinding) : RecyclerView.ViewHolder(item.root) {


    val image = item.typeImage
    val tvcard = item.CardItem
        fun bind(n: Type) {
        item.taskType = n
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout:TaskTypeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.task_type,parent,false)
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

        holder.image.setImageResource(data[position].image)

        holder.tvcard.setOnClickListener {
            itemclick.onClickItem(holder.adapterPosition,1)
        }
    }

    interface onClick {
        fun onClickItem(position: Int,type:Int)
    }

}
