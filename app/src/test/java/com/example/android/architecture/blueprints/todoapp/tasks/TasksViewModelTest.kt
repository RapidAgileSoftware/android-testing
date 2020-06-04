package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.CoreMatchers.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
class TasksViewModelTest {

    // for testing live data
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var taskViewModel:TasksViewModel

    @Before
    fun setupViewModel(){
        taskViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

    }

    @Test
    fun addNewTask_setsNewTaskEvent() {
        // Given a fresh TasksViewModel : now from @Before
        //val taskViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // When adding a new task
        taskViewModel.addNewTask()

        // Then a new task event is triggered
        val value = taskViewModel.newTaskEvent.getOrAwaitValue()
        // value is an Event, getContentIfNotHandled gets its content
        // we assert that it is not null
        assertThat(value.getContentIfNotHandled(),(not(nullValue())))
    }
    @Test
    fun setFilterAllTasks_tasksAddViewVisible(){
        //Given a fresh View model: : now from @Before
        //val taskViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // When filter mode is set to ALL_TASKS
        taskViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then "Add task" action is visible
        assertThat(taskViewModel.tasksAddViewVisible.getOrAwaitValue(), `is` (true))
       // or assertTrue(taskViewModel.tasksAddViewVisible.getOrAwaitValue())

    }
}