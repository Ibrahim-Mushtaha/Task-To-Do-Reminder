package com.example.todolist.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.todolist.R
import com.example.todolist.model.Task
import com.example.todolist.util.taskAdapter
import com.example.todolist.viewModel.HomeViewmodel
import kotlinx.android.synthetic.main.fragment_finsihed_task.view.*

class FinsihedTaskFragment : Fragment(), taskAdapter.onClick {


    var mViewModel: HomeViewmodel? = null
    var array = ArrayList<Task>()

    private val adapter by lazy {
        taskAdapter(requireActivity(), array, this)
    }

    lateinit var root:View
    override fun onCreate(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProvider(this).get(HomeViewmodel::class.java)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.fragment_finsihed_task, container, false
        )
        root = binding.root

        mViewModel?.taskCompletedLiveData!!.observe(requireActivity(), Observer {
            if (it.size == 0){
                root.container_empty.visibility = View.VISIBLE
                root.list_item_complete.visibility = View.GONE
            }else {
                root.container_empty.visibility = View.GONE
                root.list_item_complete.visibility = View.VISIBLE
                array = it as ArrayList<Task>
                adapter.setTask(it)
                adapter.notifyDataSetChanged()
            }
        })

        root.list_item_complete.layoutManager = LinearLayoutManager(requireContext())
        root.list_item_complete.adapter = adapter
        root.list_item_complete.layoutAnimation=
            AnimationUtils.loadLayoutAnimation(requireContext(),
                R.anim.recyclerview_layout_animation
            )



        return root
    }

    override fun onClickItem(position: Int, type: Int) {
        when(type){
            1->{
                val action =
                    FinsihedTaskFragmentDirections.actionFinsihedTaskFragmentToAddTask(
                        array[position]
                    )
                Navigation.findNavController(root).navigate(action)
            }
            2 -> {
                mViewModel!!.updateStatus(array,position)
                Snackbar.make(root,"remove from Completed List", Snackbar.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }
        }
    }
}