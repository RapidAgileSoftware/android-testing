package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.Result.Error
import com.example.android.architecture.blueprints.todoapp.data.Result.Success
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultTasksRepositoryTest {
    private val task1 = Task("Task1", "description 1")
    private val task2 = Task("Task2", "description 2")
    private val task3 = Task("Task3", "description 3")

    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var taskRemoteDataSource: FakeDataSource
    private lateinit var taskLocalDataSource: FakeDataSource
    // class to test
    private lateinit var tasksRepository: DefaultTasksRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository(){
        taskRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        taskLocalDataSource = FakeDataSource(localTasks.toMutableList())
        // here we init our class to test
        tasksRepository= DefaultTasksRepository(
                taskRemoteDataSource,
                taskLocalDataSource,
                // since we are using MainCoroutineRule Dispatcher.Main is swaped with TestCoroutineDispatcher
                Dispatchers.Main
        )
    }
    // we use CoroutinesApi to run suspended functions with runBlockingTest, we add Annotation to avoid linting errors
    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource()= mainCoroutineRule.runBlockingTest{
        // When tasks are requested from the tasks repository
        val tasks = tasksRepository.getTasks(true) as Success

        // Then tasks are loaded from the remote data source
        assertThat(tasks.data, IsEqual(remoteTasks))

    }




}