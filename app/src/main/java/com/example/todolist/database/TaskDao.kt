/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.model.Task

@Dao
interface TaskDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: Task)

    @Update
    fun update(task: Task)



    @Query("SELECT * FROM task_table where status =0")
    fun getAllTask(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table where status =1")
    fun getCompletedTask(): LiveData<List<Task>>



    @Query("SELECT * FROM task_table where title > :name")
    fun getTask(name:String): LiveData<List<Task>>?

    @Delete
    fun delete(task: Task)


    @Query("SELECT * from task_table WHERE type = :key")
    fun get(key: String): LiveData<List<Task>>?



}
