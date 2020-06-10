package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

        tasksLocalDataSource= TasksLocalDataSource(
                database.taskDao(), Dispatchers.Main
        )
    }

    @After
    fun closeDB(){
        database.close()
    }

    // runBlocking is used here because of https://github.com/Kotlin/kotlinx.coroutines/issues/1204
    // TODO: Replace with runBlockingTest once issue is resolved
    @Test
    fun saveTask_retrievesTask() = runBlocking {
        // GIVEN - A new task saved in the database.
        val newTask = Task("title", "description", false)
        tasksLocalDataSource.saveTask(newTask)

        // WHEN  - Task retrieved by ID.
        val result = tasksLocalDataSource.getTask(newTask.id)

        // THEN - Same task is returned.
        assertThat(result.succeeded, `is`(true))
        result as Result.Success
        assertThat(result.data.title, `is`("title"))
        assertThat(result.data.description, `is`("description"))
        assertThat(result.data.isCompleted, `is`(false))
    }

    @Test
    fun completeTask_retrievedTaskIsComplete()= runBlocking(){
        // save a new active task in the local data source.
        val task = Task("title", "description", false)
        tasksLocalDataSource.saveTask(task)

        // mark it as complete.
        tasksLocalDataSource.completeTask(task.id)

        // check that task can be retrieved from the local data source and is complete.
        val result = tasksLocalDataSource.getTask(task.id)
        assertThat(result.succeeded, `is`(true))
        result as Result.Success
        assertThat(result.data.title, `is`(task.title))
        assertThat(result.data.isCompleted, `is`(true))
    }


}