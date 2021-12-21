package com.example.todolist.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.todolist.database.TaskRepository
import com.example.todolist.model.Task
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class HomeViewmodel(application: Application) :
    AndroidViewModel(application) {

        var taskLiveData: LiveData<List<Task>>? = null
        var taskCompletedLiveData: LiveData<List<Task>>? = null
        var SearchTask: LiveData<List<Task>>? = null
    var mRepository: TaskRepository? = null



    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    init {
        Log.e("eee", "ViewModel Created")
        mRepository = TaskRepository(application)
        taskLiveData = mRepository?.getAlltask()
        taskCompletedLiveData = mRepository?.getCompletedTask()
    }

    override fun onCleared() {
        super.onCleared()
            viewModelJob.cancel()
    }

    fun insert(task: Task) {
        uiScope.launch {
                mRepository!!.inserttask(task).also {
                    Log.e("eee","Added")
                }
        }
    }


    fun getSearchResult(name:String):LiveData<List<Task>>{
        uiScope.launch {
            SearchTask =mRepository?.getSearchTask(name)
        }
        return SearchTask!!
    }


    fun update(task: Task) {
        uiScope.launch {
                mRepository!!.updatetask(task).also {
                    Log.e("eee","Updated")
                }
        }
    }


    fun delete(task: Task) {
        uiScope.launch {
            mRepository!!.deletetask(task).also {
                Log.e("eee","deleted")
            }
        }
    }




    fun updateStatus(Array: ArrayList<Task>, position:Int){
        if (Array[position].status == 0) {
            update(
                Task(
                    Array[position].id,
                    Array[position].title,
                    Array[position].type,
                    Array[position].timeToDo,
                    1
                )
            )
        } else {
            update(
                Task(
                    Array[position].id,
                    Array[position].title,
                    Array[position].type,
                    Array[position].timeToDo,
                    0
                )
            )
        }
    }

    fun check(title:String,time:String,task: Task):Boolean{
        if (title.isNotEmpty() && time != ""){
            insert(task)
            return true
        }else{
            Log.e("eee","input is emoty")
            return false
        }
    }



    internal fun getAllTaskList(): LiveData<List<Task>>? {
        return taskLiveData
    }




    fun addToDatabase(title: String,time: String,selected_type:String):Boolean{
        val x=   check(title,time,
            Task(
                UUID.randomUUID().toString(),
               title,
                selected_type,
                time,
                0
            )
        )
        if (x){
            return true
        }else{
            return false
        }
    }



    fun updateToDatabase(title: String,time: String,selected_type:String){
        update(
            Task(
            UUID.randomUUID().toString(),
            title,
            selected_type,
            time,
            0
        )
        )
    }


}