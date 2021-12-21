package com.example.todolist.database

import android.app.Application
import com.example.todolist.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(application: Application)  {


    var data: TaskDatabase?=null
    var mDao: TaskDao

    init {
        data = TaskDatabase.getInstance(application)
        mDao =data!!.taskDao
    }


    fun getAlltask() = mDao.getAllTask()
    fun getCompletedTask() = mDao.getCompletedTask()
    fun getSearchTask(name:String) = mDao.getTask(name)
    fun getType(type:String) = mDao.get(type)


     suspend fun inserttask(task: Task) {
        withContext(Dispatchers.IO){
            mDao.insert(task)
        }
    }

    suspend fun updatetask(task: Task){
        withContext(Dispatchers.IO){
            mDao.update(task)
        }
    }

    suspend fun deletetask(task: Task){
        withContext(Dispatchers.IO){
            mDao.delete(task)
        }
    }
}