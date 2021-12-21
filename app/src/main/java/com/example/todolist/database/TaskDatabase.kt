package com.example.todolist.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.model.Task

@Database(entities = [Task::class], version = 2,exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao : TaskDao

    companion object{

        private var INSTANCE: TaskDatabase? = null

        fun getInstance(activity: Application): TaskDatabase {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        activity,
                        TaskDatabase::class.java,
                        "task_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        fun CreateBuilder(context: Context) = Room.databaseBuilder(context.applicationContext,
            TaskDatabase::class.java,"task.db").build()
    }







}