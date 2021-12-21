package com.example.todolist.util

import android.app.Activity
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.ix.ibrahim7.todolist.R
import com.ix.ibrahim7.todolist.databinding.TaskItemBinding
import com.example.todolist.model.Task


class taskAdapter(
    var activity: Activity, var data: MutableList<Task>, val itemclick: onClick
) :
    RecyclerView.Adapter<taskAdapter.MyViewHolder>() , Filterable {

    var mdata: MutableList<Task> = data


class MyViewHolder(val item: TaskItemBinding) : RecyclerView.ViewHolder(item.root) {


        fun bind(n: Task) {
        item.tasktodo = n
        }

    val line =item.lineColor
    val tvtitle= item.txtTitle
    val tvCheck=item.checkBox
    val tvCard = item.taskCard

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout:TaskItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.task_item,parent,false)
        return MyViewHolder(itemView_layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

     fun setTask(lsit: List<Task>){
            this.data=lsit as MutableList<Task>
    }


    fun getTaskPosition(position: Int): Task {
        return data.get(position)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    val currentItem = data[position]
        holder.bind(currentItem)
        holder.item.executePendingBindings()

        if (data[position].status == 1){
            val ss = SpannableString(holder.tvtitle.text)
            val i = StrikethroughSpan()
            ss.setSpan(i,0,holder.tvtitle.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            holder.tvtitle.setText(ss)
        }else{
            holder.tvtitle.setText(data[position].title)
        }

        if (data[position].type == "Personal") {
            holder.line.setBackgroundColor(Color.parseColor("#FFD506"))
        }else if (data[position].type == "Work"){
            holder.line.setBackgroundColor(Color.parseColor("#5DE61A"))
        }else if (data[position].type == "Meeting"){
            holder.line.setBackgroundColor(Color.parseColor("#D10263"))
        }else if (data[position].type == "Study"){
            holder.line.setBackgroundColor(Color.parseColor("#3044F2"))
        }else if (data[position].type == "Shopping"){
            holder.line.setBackgroundColor(Color.parseColor("#F29130"))
        }

        if (data[position].status == 1){
            holder.tvCheck.isChecked = true
        }else{
            holder.tvCheck.isChecked = false
        }

        holder.tvCheck.setOnClickListener {
            itemclick.onClickItem(holder.adapterPosition,2)
        }

        holder.tvCard.setOnClickListener {
            itemclick.onClickItem(holder.adapterPosition,1)
        }


    }

    interface onClick {
        fun onClickItem(position: Int,type:Int)
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                data = results!!.values as ArrayList<Task>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charString: String = constraint.toString()
                if (charString.isEmpty()) {
                    data = mdata
                }
                else {
                    val filteredList = ArrayList<Task>()
                    for (i in 0 until data.size) {
                        if (data[i].title.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(data[i])
                        }
                    }
                    data = filteredList
                }
                val filteredResult = FilterResults()
                filteredResult.values = data
                return filteredResult
            }


        }
    }


}
