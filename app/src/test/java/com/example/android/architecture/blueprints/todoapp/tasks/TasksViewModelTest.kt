package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.robolectric.annotation.Config

@Config(manifest=Config.NONE)
class TasksViewModelTest {

    // for testing live data
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var taskViewModel:TasksViewModel

    private lateinit var tasksRepository: FakeTestRepository

    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setTestDispatcher(){
        // swaps out standard DispatcherMain with TestCoroutineDispatcher
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownTestDispatcher(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Before
    fun setupViewModel(){
        tasksRepository = FakeTestRepository()
        // init repo with 2 completed and two active tasks
        val task1 = Task("Title 1", "description 1", true)
        val task2 = Task("Title 2", "description 2", true)
        val task3 = Task("Title 3", "description 3")
        val task4 = Task("Title 4", "description 4")
        tasksRepository.addTask(task1,task2,task3,task4)

        taskViewModel = TasksViewModel(tasksRepository)

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

    @Test
    fun completeTask_dataAndSnackbarUpdated(){
        // Given - repository with one active task
        val task = Task("Title", "description")
        tasksRepository.addTask(task)

        // When task completed
        taskViewModel.completeTask(task, completed = true)

        // Then - verify that the task is completed
        assertThat(tasksRepository.tasksServiceData[task.id]?.isCompleted, `is`(true))
        // and the snackbar is updated
        val snackbarText: Event<Int> = taskViewModel.snackbarText.getOrAwaitValue()
        assertThat(snackbarText.getContentIfNotHandled(), `is`(R.string.task_marked_complete))

    }
}