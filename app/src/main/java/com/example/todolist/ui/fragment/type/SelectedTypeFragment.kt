package com.example.todolist.ui.fragment.type

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ix.ibrahim7.todolist.R
import com.example.todolist.model.Task
import com.example.todolist.util.taskAdapter
import com.example.todolist.viewModel.HomeViewmodel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_empty.view.*
import kotlinx.android.synthetic.main.selected_type_fragment.view.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class SelectedTypeFragment : Fragment(), taskAdapter.onClick {

    val args: SelectedTypeFragmentArgs by navArgs()

    var array = ArrayList<Task>()


    private val adapter by lazy {
        taskAdapter(requireActivity(), array, this)
    }
    var mViewModel: HomeViewmodel? = null


    lateinit var root:View




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(HomeViewmodel::class.java)
    }




    override fun onStart() {
        mViewModel?.getAllTaskList()?.observe(requireActivity(), androidx.lifecycle.Observer {
            it.forEach {task->
                Log.e("eee",args.name)
                Log.e("eee type",task.type)
                if (task.type.equals(args.name)) {
                    array.add(task)
                    Log.e("eee type",task.toString())
                    root.container_selected_empty.visibility = View.GONE
                }else{
                    root.txt2.text = "You have no task to do in this field."
                    root.container_selected_empty.visibility = View.VISIBLE
                }
            }
            adapter.notifyDataSetChanged()
        })
        super.onStart()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      //  requireActivity().bottom_nav.visibility=View.GONE
        requireActivity().toolbar.title = args.name
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.selected_type_fragment, container, false
        )
        root= binding.getRoot()


        root.list_selected_item.layoutManager = LinearLayoutManager(requireContext())
        root.list_selected_item.adapter = adapter

        return root
    }

    override fun onClickItem(position: Int, type: Int) {
        when(type){
            1->{
                Log.e("eee",array[position].title)
            }
            2 -> {
                mViewModel!!.updateStatus(array,position)
            }
        }
    }

}
