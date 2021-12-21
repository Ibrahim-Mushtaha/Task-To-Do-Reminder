package com.example.todolist.ui.fragment

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.todolist.R
import com.example.todolist.Service.AlertReceiver
import com.example.todolist.model.Task
import com.example.todolist.model.Type
import com.example.todolist.util.ChooseTypeAdapter
import com.example.todolist.viewModel.HomeViewmodel
import kotlinx.android.synthetic.main.add_task_fragment.view.*
import java.lang.Exception
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class AddTask : Fragment(), ChooseTypeAdapter.onClick {

    val args: AddTaskArgs by navArgs()


    var array = ArrayList<Type>()

    var selected_type = ""
    var time = ""

    var instance : Calendar?=null
    private val adapter by lazy {
        ChooseTypeAdapter(requireActivity(), array, this)
    }

    var mViewModel: HomeViewmodel? = null


    lateinit var root:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(HomeViewmodel::class.java)
    }


    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //requireActivity().bottom_nav.visibility=View.GONE
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.add_task_fragment, container, false
        )
        root= binding.getRoot()
        getType()



        if (args.task.id != "") {
            root.btn_add.text = "Update"
            Log.e("eee",args.task.type)
            Log.e("eee title",args.task.title)
            time = args.task.timeToDo
            val timeMin = args.task.timeToDo.substring(3).toInt()
            val timeNum =args.task.timeToDo.substring(0, 2).toInt()
            Log.e("ttt",args.task.timeToDo +"|| $timeNum || $timeMin")
            val now = Calendar.getInstance()
            now.set(Calendar.HOUR_OF_DAY,timeNum)
            now.set(Calendar.MINUTE,timeMin)
            instance = now
            root.editText.setText(args.task.title)
            root.select_date.setText(args.task.timeToDo)
            root.btn_add.setOnClickListener {
                    mViewModel?.update(Task(args.task.id,root.editText.text.toString(),selected_type,time,0))
                    Navigation.findNavController(root).navigate(R.id.action_addTask_to_homeFragment)
                    startAlarm(instance!!, root.editText.text.toString())
            }

        }else{
            try {
                val x = SimpleDateFormat("hh : mm").format(Date().time)
                root.select_date.setText(x)
            }catch (e:Exception){
                Log.e("ttt",e.message.toString())
            }

            root.btn_add.setOnClickListener {
                if (selected_type == ""){
                    selected_type = "Personal"
                }
                val x =
                    mViewModel?.addToDatabase(root.editText.text.toString(), time, selected_type)
                if (x == true) {
                    Navigation.findNavController(root).navigate(R.id.action_addTask_to_homeFragment)
                    startAlarm(instance!!, root.editText.text.toString())
                }
            }
        }


        root.type_list_add.layoutManager =LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        root.type_list_add.adapter= adapter

        root.select_date.setOnClickListener {
            val now = Calendar.getInstance()
            instance = now
            val timePicker = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
               // val selectedTime = Calendar.getInstance()
                now.set(Calendar.HOUR_OF_DAY,hourOfDay)
                now.set(Calendar.MINUTE,minute)
                root.select_date.text = SimpleDateFormat("HH:mm").format(now.time)
                time = SimpleDateFormat("HH:mm").format(now.time)
            },
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false).show()
        }

        return root

    }

    private fun startAlarm(c:Calendar,Message:String){
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlertReceiver::class.java).putExtra("message",Message)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 1, intent, 0)

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1)
        }
        /*1599652800000*/
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    override fun onClickItem(position: Int, type: Int) {
        when(type){
            1->{
                selected_type = array[position].name
                adapter.notifyDataSetChanged()
            }
        }
    }



    private fun getType(){
        array.add(Type("Personal", R.drawable.ic_personal))
        array.add(Type("Work", R.drawable.ic_work))
        array.add(Type("Shopping", R.drawable.ic_shopping))
        array.add(Type("Meeting", R.drawable.ic_meeting))
        array.add(Type("Party", R.drawable.ic_party))
        array.add(Type("Study", R.drawable.ic_study))
    }
}
