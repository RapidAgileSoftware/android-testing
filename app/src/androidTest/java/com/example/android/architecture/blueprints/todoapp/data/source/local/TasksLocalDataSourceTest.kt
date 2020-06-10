package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
// this is integration test since it tests real DAO code and TasksLocalDataSource
@MediumTest
class TasksLocalDataSourceTest(){
    //TasksLocalDataSource: class that takes the information returned by the DAO and converts it to a format that is expected by the repository class

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ToDoDatabase
    private lateinit var tasksLocalDataSource: TasksLocalDataSource

    @Before
    fun initDB(){
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                ToDoDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDB(){
        database.close()
    }

    @Before
    fun initTasksLocalDataSource(){
        tasksLocalDataSource= TasksLocalDataSource(
            database.taskDao(), Dispatchers.Main
        )
    }



}