package com.example.todolist.ui.fragment.type

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.ix.ibrahim7.todolist.R
import com.example.todolist.model.Type
import com.example.todolist.util.taskTypeAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.task_fragment.view.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class TaskFragment : Fragment(), taskTypeAdapter.onClick {

    var array = ArrayList<Type>()


    private val adapter by lazy {
        taskTypeAdapter(requireActivity(), array, this)
    }

    lateinit var root:View
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
          //  requireActivity().bottom_nav.visibility=View.VISIBLE
            // Inflate the layout for this fragment
            val binding = DataBindingUtil.inflate<ViewDataBinding>(
                inflater, R.layout.task_fragment, container, false
            )
            root= binding.getRoot()

            getType()

            root.type_list.layoutManager = GridLayoutManager(requireContext(),2)
            root.type_list.adapter = adapter



            return root
        }


    private fun getType(){
        array.clear()
        array.add(Type("Personal",R.drawable.ic_personal))
        array.add(Type("Work",R.drawable.ic_work))
        array.add(Type("Meeting",R.drawable.ic_meeting))
        array.add(Type("Study",R.drawable.ic_study))
        array.add(Type("Shopping",R.drawable.ic_shopping))
        array.add(Type("Party",R.drawable.ic_party))
    }


    override fun onClickItem(position: Int, type: Int) {
      when(type){
          1->{
              val action=
                  TaskFragmentDirections.actionTaskFragmentToSelectedTypeFragment(
                      array[position].name
                  )
              Navigation.findNavController(root).navigate(action)
          }
      }
    }

}

