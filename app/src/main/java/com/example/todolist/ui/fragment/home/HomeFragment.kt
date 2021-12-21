package com.example.todolist.ui.fragment.home

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.todolist.R
import com.example.todolist.model.Task
import com.example.todolist.util.taskAdapter
import com.example.todolist.viewModel.HomeViewmodel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.item_header.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), taskAdapter.onClick {

    var Array = ArrayList<Task>()


    private val adapter by lazy {
        taskAdapter(requireActivity(), Array, this)
    }
    var mViewModel: HomeViewmodel? = null

    lateinit var root: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(HomeViewmodel::class.java)
    }


    override fun onStart() {
      //  getTime()
        mViewModel!!.getAllTaskList()!!.observe(requireActivity(), androidx.lifecycle.Observer { task ->
            if (task.isEmpty()) {
                root.list_item.visibility = View.GONE
                root.container_empty.visibility = View.VISIBLE
            } else {
                root.list_item.visibility = View.VISIBLE
                root.container_empty.visibility = View.GONE
                adapter.setTask(task)
                Array = task as ArrayList<Task>
                Log.e("eee", Array.size.toString())
                adapter.notifyDataSetChanged()
            }
        })
        super.onStart()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      /*  requireActivity().bottom_nav.visibility = View.VISIBLE*/
        requireActivity().toolbar.visibility = View.VISIBLE
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.home_fragment, container, false
        )
        root = binding.root
        setHasOptionsMenu(false)

        root.fab_add.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToAddTask(
                    Task("", "", "", "", 0)
                )
            Navigation.findNavController(root).navigate(action)
        }



        root.list_item.adapter = adapter
        root.list_item.layoutAnimation= AnimationUtils.loadLayoutAnimation(requireContext(),R.anim.recyclerview_layout_animation)

        swipe()

        return root
    }

    override fun onClickItem(position: Int, type: Int) {
        when (type) {
            1->{

            val action =
                HomeFragmentDirections.actionHomeFragmentToAddTask(
                    Array[position]
                )
            Navigation.findNavController(root).navigate(action)
            }
            2 -> {
                mViewModel!!.updateStatus(Array,position)
                Snackbar.make(root,"Added to Completed List",Snackbar.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }
        }
    }


    fun swipe() {
        val callback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) { // Take action for the swiped item
                val position = viewHolder.getAdapterPosition()

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        mViewModel!!.delete(adapter.getTaskPosition(position))
                        Snackbar.make(root,"Deleted",Snackbar.LENGTH_SHORT).show()
                    }
                }
            }


            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(root.list_item)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        val x=menu.findItem(R.id.search)
        requireActivity().searchView.setMenuItem(x)

        requireActivity().searchView.setOnQueryTextListener(object :SimpleSearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                mViewModel?.getSearchResult(query!!)?.observe(requireActivity(), androidx.lifecycle.Observer {
                    Array = it as ArrayList<Task>
                    adapter.setTask(it)
                    adapter.notifyDataSetChanged()
                })
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                    Log.e("eee", newText.toString())
                if (newText!!.length >0){
                }
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }


    fun getTime(){
        val time = SimpleDateFormat("HH : mm").format(Date())

        val timeNum = time.substring(0, 2).toInt()

        if (timeNum > 12) {
            requireActivity().txt_header_title.text = "dsad"
        } else {
            requireActivity().txt_header_title.text = "fdsfsd"
        }
    }
}

